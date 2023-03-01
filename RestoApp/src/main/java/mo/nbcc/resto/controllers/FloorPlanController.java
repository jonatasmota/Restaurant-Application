package mo.nbcc.resto.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalLong;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mo.nbcc.resto.model.Coordinates;
import mo.nbcc.resto.model.Event;
import mo.nbcc.resto.model.EventFloorPlan;
import mo.nbcc.resto.model.FloorPlan;
import mo.nbcc.resto.model.Menu;
import mo.nbcc.resto.model.Reservation;
import mo.nbcc.resto.model.RestaurantTable;
import mo.nbcc.resto.model.TimeSlot;
import mo.nbcc.resto.services.CoordinateService;
import mo.nbcc.resto.services.EventService;
import mo.nbcc.resto.services.FloorPlanService;
import mo.nbcc.resto.services.MenuService;
import mo.nbcc.resto.services.ReservationService;
import mo.nbcc.resto.services.RestaurantTableService;
import mo.nbcc.resto.services.TimeSlotService;

@Controller
public class FloorPlanController {
	private FloorPlanService fps;
	private RestaurantTableService rts;
	private CoordinateService cts;
	private EventService es;
	private TimeSlotService tss;
	private MenuService msvc;
	private ReservationService rsSvc;
	
	
	@Autowired
	public FloorPlanController(FloorPlanService fps, RestaurantTableService rts, CoordinateService cts, EventService es,
			TimeSlotService tss, MenuService msvc, ReservationService rssvc) {
		super();
		this.fps = fps;
		this.rts = rts;
		this.cts = cts;
		this.es = es;
		this.tss = tss;
		this.msvc = msvc;
		this.rsSvc = rssvc;
	}
	
	@GetMapping("/getFloorPlans")
	public String getAllFloorPlans(Model model) {
		
		if(fps.getAllFloorPlans().size() < 1) {
			createTestPlans();
		}
		model.addAttribute("floorPlans", fps.getAllFloorPlans().toArray());
		
		return "floorplan/floorPlan";
	}


	@GetMapping("/floorPlanForm")
	@Transactional
	public String newFloorPlanForm(@RequestParam (value = "id", required = false) Long id, Model model) {
		EventFloorPlan plan = new EventFloorPlan();
		List<Event> events = es.getEventsWithoutFloorPlan();
		
		if(id != null) {	
			plan = fps.getFloorPlanById(id);
			if (plan.getEvent() != null) {
				events.clear();
				events.add(plan.getEvent());
			}
		}
		
		model.addAttribute("events", events);
		model.addAttribute("tables", plan.getTables());
		model.addAttribute("floorPlan", plan);
		return "floorplan/newFloorPlan";
	}
	
	//Need more testing, and porting to new models for floorplan.
	@Transactional
	@PostMapping("/saveFloorPlan")
	public String saveFloorPlan(Model model, EventFloorPlan plan, Event event) {
		
		Set<RestaurantTable> tables = new HashSet<RestaurantTable>();
		EventFloorPlan planToSave = new EventFloorPlan();
		List<Event> freePlans = es.getEventsWithoutFloorPlan();
		if(plan.getFloorPlanId() == 0){
			planToSave = new EventFloorPlan(plan.getFloorPlanName(), tables, null);
			if(event.getEventId() != null) {
				Event savedEvent = es.getEventByID(event.getEventId());
				planToSave.setEvent(savedEvent);
				savedEvent.setEventFloorPlan(planToSave);
//				es.saveEvent(savedEvent);
				freePlans.clear();
				freePlans.add(savedEvent);
				
			}
		}else {
			planToSave = fps.getFloorPlanById(plan.getFloorPlanId());
			planToSave.setFloorPlanName(plan.getFloorPlanName());
			
			if(plan.getEvent() == null && event.getEventId() != null) {
				Event savedEvent = es.getEventByID(event.getEventId());
				planToSave.setEvent(savedEvent);
				savedEvent.setEventFloorPlan(planToSave);
//				es.saveEvent(savedEvent);
				freePlans.clear();
				freePlans.add(savedEvent);
			}
		}

		fps.saveFloorPlan(planToSave);
		model.addAttribute("events", freePlans);
		model.addAttribute("tables", planToSave.getTables());
		model.addAttribute("floorPlan", planToSave);
		
		return "floorplan/newFloorPlan";
	}
	
	@Transactional
	@GetMapping("/deleteFloorPlan/{id}")
	public String deleteFloorPlan(@PathVariable (value = "id") long id, Model model) {
		EventFloorPlan plan = fps.getFloorPlanById(id);
		rts.deleteTables(plan.getTables());
		fps.deleteFloorPlan(plan);
		
		return "redirect:/getFloorPlans";
	}
	
	@Transactional
	@PostMapping("/saveTable")
	public String saveTable(RestaurantTable table, EventFloorPlan plan, RedirectAttributes ra) {
		plan = fps.getFloorPlanById(plan.getFloorPlanId());
		Set<RestaurantTable> tables = plan.getTables();
		table.setFloorPlan(fps.getFloorPlanById(plan.getFloorPlanId()));
		rts.saveTable(table);
		tables.add(table);
		plan.setTables(tables);
		fps.saveFloorPlan(plan);
		
		ra.addFlashAttribute("table", table);
		ra.addAttribute("id", plan.getFloorPlanId());
		return "redirect:/floorPlanForm";
	}
	
	@Transactional
	@PostMapping("/moveTable")
	@ResponseStatus(value = HttpStatus.OK)
	public void saveTable(@RequestBody RestaurantTable table) {
		RestaurantTable tableToSave = rts.getTableById(table.getTableId());
		Coordinates newCoordinates;
		
		if(table.getCoordinates().getcId() != 0 ) {
			newCoordinates = cts.getCoordinate(table.getCoordinates().getcId());
			newCoordinates.setxAxis(table.getCoordinates().getxAxis());
			newCoordinates.setyAxis(table.getCoordinates().getyAxis());
		}else {
			newCoordinates = new Coordinates(tableToSave, table.getCoordinates().getxAxis(), table.getCoordinates().getyAxis());
		}
	
		tableToSave.setCoordinates(newCoordinates);
		cts.saveCoordinates(newCoordinates);
		rts.saveTable(tableToSave);
		
		
	}
	
	
	@GetMapping("/viewTable/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody RestaurantTable viewTable(@PathVariable (value = "id") long id, Model model) {
		RestaurantTable table = rts.getTableById(id);
		return table;
	}
	
	@GetMapping("/deleteTable/{id}")
	public String deleteTable(@PathVariable (value = "id") long id, Model model, RedirectAttributes ra) {
		RestaurantTable table = rts.getTableById(id);
		rts.deleteTable(table);
		ra.addAttribute("id", table.getFloorPlan().getFloorPlanId());
		return "redirect:/floorPlanForm";
	}
	/**
	 * Creates 2 test FloorPlans
	 */
	@Transactional
	public void createTestPlans() {
		LocalDate now = LocalDate.now();
		Menu menu = new Menu("Test Menu", "None", now, null, null);
		msvc.saveMenu(menu);

		Event event = new Event("TestEvent",now,"test",menu);
		TimeSlot timeSlot = new TimeSlot(3, 45, event);
		Set<RestaurantTable> tables = new HashSet<RestaurantTable>();
		EventFloorPlan plan = new EventFloorPlan("Test1", tables, event);
		plan.setEvent(event);
		tables.add(new RestaurantTable(2, 1, plan, null));
		tables.add(new RestaurantTable(3, 2, plan, null));
		tables.add(new RestaurantTable(4, 3, plan, null));
		
		Event event2 = new Event("TestEvent2",now,"test",menu);
		TimeSlot timeSlot2 = new TimeSlot(3, 45, event2);
		Set<RestaurantTable> tables2 = new HashSet<RestaurantTable>();
		EventFloorPlan plan2 = new EventFloorPlan("Test1", tables2, event2);
		Reservation res = new Reservation(5, "test", "test", "test", null, null, null, 0, timeSlot2, null);
		plan2.setEvent(event2);
		tables2.add(new RestaurantTable(4, 4, plan2, res));
		tables2.add(new RestaurantTable(5, 5, plan2, res));
		tables2.add(new RestaurantTable(6, 6, plan2, null));
		
		es.saveEvent(event);
		es.saveEvent(event2);
		fps.saveFloorPlan(plan);
		
		tss.saveTimeSlot(timeSlot);
		
		tss.saveTimeSlot(timeSlot2);
		fps.saveFloorPlan(plan2);
		
		rsSvc.saveReservation(res);
		rts.saveTables(tables);
		rts.saveTables(tables2);
		event.setEventFloorPlan(plan);
		es.saveEvent(event);
		es.saveEvent(event2);
	}
}
