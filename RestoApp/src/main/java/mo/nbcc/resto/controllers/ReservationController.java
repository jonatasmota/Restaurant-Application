package mo.nbcc.resto.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mo.nbcc.resto.model.Event;
import mo.nbcc.resto.model.EventFloorPlan;
import mo.nbcc.resto.model.Reservation;
import mo.nbcc.resto.model.RestaurantTable;
import mo.nbcc.resto.model.TimeSlot;
import mo.nbcc.resto.services.EventService;
import mo.nbcc.resto.services.FloorPlanService;
import mo.nbcc.resto.services.ReservationService;
import mo.nbcc.resto.services.TimeSlotService;

@Controller
public class ReservationController {
	ApplicationContext ac;
	ReservationService rs;
	EventService es;
	TimeSlotService ts;
	FloorPlanService fps;

	public ReservationController() {
		super();
	}

	@Autowired
	public ReservationController(ApplicationContext ac, ReservationService rs, EventService es, TimeSlotService ts, FloorPlanService fps) {
		super();
		this.ac = ac;
		this.rs = rs;
		this.es = es;
		this.ts = ts;
		this.fps = fps;
	}

	@GetMapping("/reservations")
	public String index(Model model,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
			@RequestParam(required = false) String query) {
		List<Reservation> reservations = new ArrayList<>();

		if (Objects.nonNull(date) && Objects.nonNull(query) && query != "") {
			reservations = rs.getReservationsByNameAndDate(date, query);
		} else if (Objects.nonNull(date)) {
			reservations = rs.getReservationsByDate(date);
		} else if (Objects.nonNull(query) && query != "") {
			reservations = rs.searchReservations(query);
		} else {
			reservations = rs.getReservations();
		}

		for (Reservation res : reservations) {
			res.setResTime();

			Event event = res.getTimeSlot().getEvent();
			res.setResEvent(event);
		}

		model.addAttribute("reservations", reservations);

		return "reservation/reservationIndex";
	}

	@GetMapping("/create")
	public String createReservation(Model model,
			HttpSession session,
			@RequestParam(name = "id", required = false) Long resID,
			@RequestParam(name = "events", required = false) Long eventID) {
		Reservation reservation = null;
		List<Event> futureEvents = es.getFutureEvents();
		//Event event = es.getEventByID(eventID);
		Event resEvent = null;
		Set<TimeSlot> eventTimeSlots = null;
		
		Set<RestaurantTable> eventTables = null;
		List<Integer> tableSeats = new ArrayList<>();
		
		List<Reservation> eventReservations = new ArrayList<>();
		List<String> seatNumbers = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
		
		if (Objects.nonNull(resID)) {
			reservation = rs.getReservationByID(resID);
		} else {
			reservation = new Reservation();
		}

		if (Objects.nonNull(eventID)) {
			resEvent = es.getEventByID(eventID);
			eventTimeSlots = resEvent.getTimeSlots();
			eventReservations = rs.getReservationsByEvent(resEvent);
			
			EventFloorPlan fp = resEvent.getEventFloorPlan();
			
			if (Objects.nonNull(fp)) {
				eventTables = fp.getTables();
			
				for (RestaurantTable table : eventTables) {
					tableSeats.add(table.getNumberOfSeats());
				}
			}
			
		}

		if (tableSeats.size() > 0) {
			seatNumbers = rs.getAvailableTables(eventReservations, tableSeats);
		}
		
		model.addAttribute("resEvent", resEvent);
		model.addAttribute("reservation", reservation);
		model.addAttribute("futureEvents", futureEvents);
		model.addAttribute("eventTimeSlots", eventTimeSlots);
		model.addAttribute("seatList", seatNumbers);
		
		
		if (session.getAttribute("loggedIn") != null) {
			
			return "reservation/create";
		}
		
		model.addAttribute("events", resEvent);
		return "reservation";
	}

	@PostMapping("/res")
	public String confirmReservation(Model model, @ModelAttribute("reservation") Reservation reservation) {
		Event resEvent = null;
		TimeSlot resTimeSlot = null;

		reservation.setConfirmationNumber(rs.generateConfirmationNumber());

		if (reservation.getTimeSlot() != null)
			resTimeSlot = reservation.getTimeSlot();
		else
			resTimeSlot = ts.getTimeSlotByID(reservation.getResTimeSlotID());

		reservation.setTimeSlot(resTimeSlot);
		resEvent = es.getEventByID(resTimeSlot.getEvent().getEventId());

		reservation.setResEvent(resEvent);

		this.rs.saveReservation(reservation);

		model.addAttribute("resEvent", resEvent);

		return "reservation/confirmation";
	}

	@GetMapping("/res/{conNum}")
	public String findReservationByConfirmationNum(Model model, @PathVariable("conNum") int conNum) {
		Reservation reservation = this.rs.getReservationByConfirmationNumber(conNum);
		TimeSlot  timeSlot = reservation.getTimeSlot();
		Event resEvent = es.getEventByID(timeSlot.getEvent().getEventId());
		
		reservation.setResEvent(resEvent);
		
		model.addAttribute("reservation", reservation);
		model.addAttribute("resEvent", resEvent);
		return "reservation/confirmation";
	}

	@GetMapping("/deleteReservation")
	public String deleteReservation(@RequestParam(value = "id") Long id) {
		rs.deleteReservationByID(id);
		return "redirect:/reservations";
	}

	@GetMapping("/editReservation")
	public String updateMenu(@RequestParam(value = "id") Long id, Model model) {
		List<Event> futureEvents = es.getFutureEvents();
		
		Reservation reservation = rs.getReservationByID(id);
		TimeSlot timeSlot = reservation.getTimeSlot();
		Event resEvent = timeSlot.getEvent();
		Set<TimeSlot> eventTimeSlots = resEvent.getTimeSlots();
		List<Integer> seatNumbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		
		model.addAttribute("reservation", reservation);
		model.addAttribute("resEvent", resEvent);
		model.addAttribute("reservation", reservation);
		model.addAttribute("futureEvents", futureEvents);
		model.addAttribute("eventTimeSlots", eventTimeSlots);
		model.addAttribute("seatList", seatNumbers);
		
		return "reservation/create";
	}

}
