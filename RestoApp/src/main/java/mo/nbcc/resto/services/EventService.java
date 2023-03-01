package mo.nbcc.resto.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mo.nbcc.resto.model.Event;
import mo.nbcc.resto.repository.EventRepository;

@Service
public class EventService {
	
	private EventRepository eventrepository;
	private Event event;
	
	@Autowired
	public EventService(EventRepository eventrepository) {
		super();
		this.eventrepository = eventrepository;
	}
	
	public List<Event> getAllEvents(){
		return eventrepository.findAll();
	}
	
	public Event saveEvent(Event ev) {
		return eventrepository.save(ev);
	}
	
	public Event getEventByID(Long id) {
		return eventrepository.getReferenceById(id);
	}
	
	public List<Event> getEventsByName(String eventName) {
		List<Event> events;
		if(eventName.length() != 0) {
			events = eventrepository.findByEventName(eventName);
		}
		else {
			events = new ArrayList<>();
		}
		return events;
	}
	
	public List<Event> findByEventNameContainingIgnoreCase(String eventName){
		List<Event> events = eventrepository
							.findByEventNameContainingIgnoreCase(eventName);
		return events;
	}
	
	public List<Event> getEventByDate(LocalDate date){
		List<Event> events;
		if(date == event.getEventDate()) {
			events = eventrepository.findByEventDate(date);
		}else {
			events = new ArrayList<>();
		}
		return events;
	}
	
	public void deleteEventByID(Long eventId) {
		eventrepository.deleteById(eventId);
	}
	
	public void deleteEvent(Event event) {
		eventrepository.delete(event);
	}
	
	public List<Event> getFutureEvents(){
		return eventrepository.findEventsAfterDate(LocalDate.now());
	}
	
	public List<Event> getEventsWithoutFloorPlan(){
		return eventrepository.findByEventFloorPlanIsNull();
	}
}
