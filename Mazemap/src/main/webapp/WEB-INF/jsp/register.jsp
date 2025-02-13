<!DOCTYPE html>
<html lang="en">
<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.models.User"%>
<%@page import="com.helpers.ServerUrl"%>
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="./css/main.css">
	<link rel="stylesheet" type="text/css" href="./css/custom.css">
	<link rel="stylesheet" href="./kendo-ui-core/styles/kendo.common.min.css">
	<link rel="stylesheet" href="./kendo-ui-core/styles/kendo.default.min.css">
	<!-- Scripts -->
	<script src="./js/jquery.min.js"></script>
	<script src="./js/jquery.poptrox.min.js"></script>
	<script src="./js/jquery.scrolly.min.js"></script>
	<script src="./js/skel.min.js"></script>
	<script src="./js/util.js"></script>
	<script src="./js/main.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.1/require.js" crossorigin="anonymous"></script>
	<script src="./kendo-ui-core/js/kendo.core.min.js"></script>
	<script src="./kendo-ui-core/js/kendo.data.min.js"></script>
	<script src="./kendo-ui-core/js/kendo.dropdownlist.min.js"></script>
	<script src="./kendo-ui-core/js/kendo.multiselect.min.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<script src="https://kit.fontawesome.com/7510661d31.js" crossorigin="anonymous"></script>
	<title>DTU Hub</title>
</head>

<body>
	<!-- @Author s192671, s154666 -->
	<input type="hidden" id="baseUrl" name="baseUrl" value = '<%= ServerUrl.baseUrl %>' > 
	<div class="page-wrap">

		<!-- Nav -->
		<nav id="nav">
			<ul>
				<li><a href="index" class="active"><span class="icon fa-home"></span></a></li>
				<% if(session.getAttribute("user")!=null) {%>
				<li><a href="friendlist"><span class="icon fas fa-users"></span></a></li>
				<li><a href="survey_main"><i class="fas fa-poll-h"></i></a></li>
				<li><a href="events"><i class="fa fa-calendar"></i></a></li>
				<% } else { %>

				<li><a href="login"><span class="icon fas fa-sign-in"></span></a></li>
				<%}%>
				<% if(session.getAttribute("user")!=null) {%>
				<li><a href="logout"><span class="icon fas fa-sign-out"></span></a></li>
				<% } %>
			</ul>
		</nav>

		<!-- Main -->
		<section id="main">
			<!-- Banner -->
			<section id="banner" style=" min-height: 25vh; height: 40vh">
				<div class="column inner">
					<img alt="DTU logo" src="images/dtu_logo_white.png" class="logo">
				</div>
			</section>

			<div class="column">
				<h3>Create an account</h3>
								<%
                if(session.getAttribute("user")!=null)
                {
					User user =  (User) session.getAttribute("user");
                %>
				<h3>You've just been signed in <%= user.getStudentnr() %> </h3>
                 <% } else { %> 
					<p> Session attribute is null </p>
					<% } %>
				<form id="signup">
					<label for="name"> Enter your name: </label>
					<input name="name" id="name" type="text"
						placeholder="Enter your name here...">
					</input>
				</br>
					<input value="signup" class="button" type="submit">
				</form>
				<div id="signupResult">
				</div>
			</div>

			<!-- Footer -->
			<footer id="footer">
				<div class="copyright">
					&copy; Group E: <a href="https://se2-webapp05.compute.dtu.dk/">DTU Hub</a>. Images: <a
						href="http://inside.dtu.dk">DTU Inside</a>.
				</div>
			</footer>
		</section>
	</div>


	<style>
		#cap {
			width: 242px;
			height: 225px;
			margin: 20px auto;
			background-image: url('../content/web/dropdownlist/cap.png');
			background-repeat: no-repeat;
			background-color: transparent;
		}

		.black-cap {
			background-position: 0 0;
		}

		.grey-cap {
			background-position: 0 -225px;
		}

		.orange-cap {
			background-position: 0 -450px;
		}
	</style>
	<script>
		
		$(document).ready(function () {
			var baseUrl = $("baseUrl").val();
			console.log(baseUrl);
			require.config({
				baseUrl: "/kendo-ui-core/js/", // the path where the kendo scripts are present
				paths: {
					"jquery": "/js/jquery.min",//jquery path
				}
			});

			

			const handleFormSubmit = event => {

				// Stop the form from submitting since we’re handling that with AJAX.
				event.preventDefault();
				submitForm();
			}
			function submitForm() {
				var user = {};
				$("#signup").find("input, textarea, select").each(function () {
					var inputType = this.tagName.toUpperCase() === "INPUT" && this.type.toUpperCase();
					if (inputType !== "BUTTON" && inputType !== "SUBMIT") {
						
							user[this.name] = $(this).val();
							console.log(this.name);
						
					}
				});

				console.log(user);
				$.ajax({
					contentType: 'application/json',
					data: JSON.stringify(user),
					dataType: 'json',
					success: function (data, status) {
						$("#result").text("<p>"+data+"</p>");
					},
					error: function () {
						console.log("Stuff happened");
					},
					processData: false,
					type: 'POST',
					url: baseUrl + '/signup'
				});
				
			};
			const form = document.getElementById('signup');
			form.addEventListener('submit', handleFormSubmit);
		});

	</script>
</body>

</html>
