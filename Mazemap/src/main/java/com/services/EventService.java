package com.services;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Component;
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

@Component
public class EventService
{
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private UserRepository userRepository;

    
    public Set<Event> getEventData() 
	  {
    	java.util.Date current = new java.util.Date();
        Date today = new Date(current.getTime());
        HashSet<Event> allEvents = new HashSet<Event>();
		eventRepository.findAll().forEach(allEvents::add);
	    Set<Event> activeEvents = new HashSet<Event>();
        for (Event event : allEvents) {
            if (event.getDate().after(today) && event.getDate().before(new Date(today.getTime() + 7l*24l*60l*60l*1000l))) {
               activeEvents.add(event);
            }

        }
        return activeEvents;
	  }
    public Set<Event> getUserFilteredEventData(User user) 
	  {
    	List<Event> userFilteredEvents = eventRepository.findEventsByCreator(user);
		return new HashSet<Event>(userFilteredEvents);
	  }
    
    public void createEvent(//User user,
            Event event) {
		
		Optional<User> user2 = userRepository.findById(13);
		if(user2.isPresent()) {
			User saveUser = user2.get();
			event.setCreator(saveUser);
		}
		eventRepository.save(event);
    }
    public String deleteEvent(//User user,
			@RequestParam  Integer id) {
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
    
    public void updateEvent(Event event)  
	{
		eventRepository.save(event);
	}
}