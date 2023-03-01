package mo.nbcc.resto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mo.nbcc.resto.model.Event;

import mo.nbcc.resto.model.Menu;
import mo.nbcc.resto.model.TimeSlot;
import mo.nbcc.resto.repository.EventRepository;
import mo.nbcc.resto.services.EventService;
import mo.nbcc.resto.services.MenuService;
import mo.nbcc.resto.services.TimeSlotService;

@Controller
@RequestMapping
public class EventController {

	private EventService eventService;
	private MenuService menuService;
	
	@Autowired
	public EventController(EventService eventService, MenuService menuService) {
		this.eventService = eventService;
		this.menuService = menuService;
	}
	
	@GetMapping("/events")
	public String getAllEvents(@RequestParam(value = "search", required = false) 
									String search, Model model) {
		if(search != null && !search.isBlank()) {
			model.addAttribute("events", eventService.findByEventNameContainingIgnoreCase(search));
		}else {
			model.addAttribute("events", eventService.getAllEvents());
		}
		return "event/index";
	}
	
	@GetMapping("/newEvent")
	public String addEvent(Model model) {
		Event event = new Event();
		List<Menu> menus = menuService.getAllMenus();
		
		model.addAttribute("events", event);
		model.addAttribute("menuList", menus);
		
		return "event/addEvent";
	}
	
	@PostMapping("/saveEvent")
	public String saveEvent(@ModelAttribute("event") Event event, Model model){
		Menu eventMenuId = menuService.getMenuById(event.getEventMenuID());
		Event saveEvent = new Event();
		if(event.getEventId() == null) {
			saveEvent = new Event(event.getEventName(), event.getEventDate(),
					event.getEventDetails(), eventMenuId);
			eventService.saveEvent(saveEvent);
		}
		else {
			event.setEventMenu(eventMenuId);
			eventService.saveEvent(event);
		}
		model.addAttribute("events", saveEvent);
    
		return "redirect:/events";
	}
		
	@GetMapping("/editEvent{eventId}")
	public String editEvent(@RequestParam(value = "eventId") Long eventId,  Model model) {
		Event event = eventService.getEventByID(eventId);
		List<Menu> menus = menuService.getAllMenus();
		model.addAttribute("menuList", menus);
		model.addAttribute("events", event);
		return "event/editEvent";
	}
	
	@GetMapping("/deleteEvent{eventId}")
	public String deleteEvent(@RequestParam(value = "eventId") Long eventId, Model model) {
		Event event = eventService.getEventByID(eventId);
		eventService.deleteEvent(event);
		return "redirect:/events";
	}
	
}
