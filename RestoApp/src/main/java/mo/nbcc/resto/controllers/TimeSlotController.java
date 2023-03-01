package mo.nbcc.resto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mo.nbcc.resto.model.Event;
import mo.nbcc.resto.model.TimeSlot;
import mo.nbcc.resto.services.EventService;
import mo.nbcc.resto.services.TimeSlotService;

@Controller
public class TimeSlotController {
	
	private TimeSlotService timeSlotService;
	private EventService eventService;
	
	@Autowired
	public TimeSlotController(TimeSlotService timeSlotService, EventService eventService) {
		this.timeSlotService = timeSlotService;
		this.eventService = eventService;
	}
	
	@GetMapping("/timeslots")
	public String getAllTimeSlots(Model model) {
		model.addAttribute("timeslots", this.timeSlotService.getTimeSlots());
		return "timeslot/index";
	}
	
	@GetMapping("/newTimeslot")
	public String addTimeSlot(Model model) {
		TimeSlot timeslot = new TimeSlot();
		List<Event> events = this.eventService.getAllEvents();
		
		model.addAttribute("timeslots", timeslot);
		model.addAttribute("events", events);
		
		return "timeslot/addTimeslot";
	}
	
	@PostMapping("/saveTimeslot")
	public String saveTimeSlot(@ModelAttribute("timeslot") TimeSlot timeslot, Model model) {
		Event eventTimeslotID = eventService.getEventByID(timeslot.getEventTimeslotID());
		TimeSlot newTimeslot = new TimeSlot();
		if(timeslot.getId() == 0) {
			newTimeslot = new TimeSlot(timeslot.getHour(), timeslot.getMinute(),
					eventTimeslotID);
			timeSlotService.saveTimeSlot(newTimeslot);
		}else {
			timeslot.setEvent(eventTimeslotID);
			timeSlotService.saveTimeSlot(timeslot);
		}
		
		model.addAttribute("timeslots", newTimeslot);
		return "redirect:/timeslots";
	}
	
	@GetMapping("/editTimeslot")
	public String updateTimeSlot(@RequestParam(value= "id") Long id, Model model) {
		TimeSlot timeslot = timeSlotService.getTimeSlotByID(id);
		List<Event> events = eventService.getAllEvents();
		
		model.addAttribute("timeslots", timeslot);
		model.addAttribute("events", events);
		return "timeslot/editTimeslot";
	}
	
	@GetMapping("/deleteTimeslot{id}")
	public String deleteTimeSlot(@RequestParam(value= "id") Long id, Model model) {
		TimeSlot timeslot = timeSlotService.getTimeSlotByID(id);
		timeSlotService.deleteTimeSlotByID(id);
		return "redirect:/timeslots";
	}
}
