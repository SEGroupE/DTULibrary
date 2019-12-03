package com.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.validation.Errors;
import javax.validation.Valid;
import java.util.stream.Collectors;
import com.helpers.*;
import com.helpers.ReturnMessageHelper;
import com.models.*;
import java.util.Set;
//import java.util.concurrent.atomic.AtomicLong;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.sql.Date;

@RestController
@RequestMapping(path = "/events")
public class EventController 
{
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;	
	
	
	@RequestMapping(value = "/eventdata", method = RequestMethod.GET)
	  public Set<Event> getEventData() 
	  {
	    // ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
		HashSet<Event> tempEvents = new HashSet<Event>();
		eventRepository.findAll().forEach(tempEvents::add);
	    Set<Event> events = tempEvents;
	    
	    	    return events;
	  }
	
	@RequestMapping(value = "/createevent", method = RequestMethod.POST)
	public ResponseEntity<?> createEvent(
            @Valid @RequestBody Event event, Errors errors) {
		
		Optional<User> user = userRepository.findById(13);
		if(user.isPresent()) {
			User saveUser = user.get();
			event.setCreator(saveUser);
		}
		eventRepository.save(event);
        AjaxResponseBody result = new AjaxResponseBody();
        
        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);

        }
        
        result.setMsg("success");
        
        result.setEvent(event);

        return ResponseEntity.ok(result);

    }

	
	@RequestMapping(value = "/createevents", method = RequestMethod.POST)
	  public void createEvents(
			  @RequestParam(value = "creatorId") int creatorId, 
			  @RequestParam(value = "eventDescr") String description,
			  @RequestParam(value = "date") String date,
	  		  @RequestParam(value = "hour") int hour,
			  @RequestParam(value = "minute") int minute,
			  @RequestParam(value = "lng") double lng,
			  @RequestParam(value = "lat") double lat)
		{
	    // ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
		java.sql.Date tempDate = java.sql.Date.valueOf(date);
		long longDate = tempDate.getTime();
		tempDate.setTime(longDate + 4320000);
		Event event = new Event();
		 /*event.setCreator(creator);*/ event.setDescription(description); event.setLng(lng); event.setLat(lat); 
				 event.setDate(tempDate); event.setTime(new java.sql.Time(hour, minute, 0));
				 
		         
				 eventRepository.save(event);
	}
			  @RequestMapping(value = "/createeventtest", method = RequestMethod.GET)
			  public String createEventTest()
					  
				{
			    // ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
				User user = new User();
				    userRepository.save(user);
			    	    return "got it";
			
			
			}
			  @RequestMapping(value = "/deleteevent", method = RequestMethod.DELETE)
			    public String deleteFriend(@RequestParam  Integer id) {
			        String returnMessage;
			        if (id == 0)
			            returnMessage = String.format("The event with id %s does not exist!", id);
			        else {
			        	Optional<Event> tempEvent = eventRepository.findById(id);
			        	Event event = tempEvent.get();
			        	eventRepository.deleteById(id);
			            /*
			            //User friend = userRepository.findUsersByEmail(friendEmail).get(0);
			            if (event != null && currentUser != null) {
			                User u = userRepository.findUserByEmail(currentUser.getEmail());
			                    try {
			                        u.removeFriendFromFollowingByEmail(friendEmail);
			                        friend.removeFriendFromFollowerByEmail(u.getEmail());
			                        userRepository.save(u);
			                        userRepository.save(friend);
			                        returnMessage = String.format("Friend %s was removed from your list!", friendEmail);
			                    } catch (Exception e) {
			                        e.printStackTrace();
			                        returnMessage = "There was an error when removing your friend! Check the email you input!";
			                    }

			            } else {*/
			                returnMessage = "Error: one of the entities is null!";
			          //  }
			        }
			        return ReturnMessageHelper.getReturnMessage(returnMessage);
			    }
			  
			  @RequestMapping(value = "/updateevent", method = RequestMethod.POST)
			  public void updateEvent(@RequestBody Event event)  
				{
				//  eventRepository.save(event);
			    // ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
				/*int tempId = event.Id;
				Optional<Event> event = eventRepository.findById(tempId);
				
				java.sql.Date date = java.sql.Date.valueOf("2019-11-28");
				long tempDate = date.getTime();
				date.setTime(tempDate + 4320000);
				event.get().setDate(date);
				
				
				
				eventRepository.save(event.get());
			    */
			    	   
			
			
			}
			  
    @RequestMapping({"/otherevent"})
    public String event(@RequestParam(value="name", required=false, defaultValue="World") String name) 
    {
        return "some evnt stuff";
    }
}