package mo.nbcc.resto.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mo.nbcc.resto.model.Event;
import mo.nbcc.resto.model.Reservation;
import mo.nbcc.resto.model.TimeSlot;
import mo.nbcc.resto.repository.ReservationRepository;

@Service
public class ReservationService {
	
	private ReservationRepository rr;
	
	@Autowired
	public ReservationService(ReservationRepository rr) {
		super();
		this.rr = rr;
	}
	
	public List<Reservation> getReservations() {
		return rr.findAll();
	}
	
	public Reservation saveReservation(Reservation t) {
		return rr.save(t);
	}
	
	public Reservation getReservationByID(Long reservationId) {
		return rr.getReferenceById(reservationId);
	}
	
	public void deleteReservationByID(Long reservationId) {
		rr.deleteById(reservationId);
	}
	
	public Reservation getReservationByConfirmationNumber(int confirmationNumber) {		
		return rr.findByConfirmationNumber(confirmationNumber);
	}
	
	public List<Reservation> getReservationsByName(String firstName, String lastName) {
		List<Reservation> resByName;
		
		if(firstName != null && lastName != null) {
			resByName = rr.findByFirstNameAndLastName(firstName, lastName);
		} else if (firstName != null) {
			resByName = rr.findByFirstName(firstName);
		} else if (lastName != null) {
			resByName = rr.findByLastName(lastName);
		} else {
			resByName = new ArrayList<>();
		}
		
		return resByName;
	}
	
	public List<Reservation> searchReservations(String searchQuery) {
		List<Reservation> allReservations = rr.findAll();
		List<Reservation> filteredReservations = new ArrayList<Reservation>();
		
		//Splits the query string into a list of strings with a blank space as the separator
		String[] searchQueries = searchQuery.split("\\s+");
		
		if (searchQueries.length <= 0) {
			filteredReservations = allReservations;
		} else {
			for (Reservation res : allReservations) {
				String fNameLower = res.getFirstName().toLowerCase();
				String lNameLower = res.getLastName().toLowerCase();
				int confNum = res.getConfirmationNumber();
				
				for (String query : searchQueries) {
					query = query.toLowerCase();
					
					if (
							((isInt(query) && Integer.parseInt(searchQuery) == confNum)
							|| fNameLower.contains(query) 
							|| lNameLower.contains(query))
							&& filteredReservations.indexOf(res) == -1
					) {					
						filteredReservations.add(res);
					}
				}
			}
		}
		return filteredReservations;
	}
	
	public List<Reservation> getReservationsByNameAndDate(LocalDate date, String searchQuery) {
		List<Reservation> reservationsByDate = this.getReservationsByDate(date);
		List<Reservation> filteredReservations = new ArrayList<>();
		
		String[] searchQueries = searchQuery.split("\\s+");
		
		for (Reservation res : reservationsByDate) {
			for (String query : searchQueries) {
				if ((isInt(query) && res.getConfirmationNumber() == Integer.parseInt(query)) || res.getFirstName().contains(query) || res.getLastName().contains(query)) {
					filteredReservations.add(res);
				}
			}
		}
		
		return filteredReservations;
	}
	
	public int generateConfirmationNumber() {
		int code = 1 + (int)(Math.random() * 1000000);
		
		while (!Objects.isNull(rr.findByConfirmationNumber(code))){
			code = 1 + (int)(Math.random() * 1000000);
		}
		
		return code;
	}
	
	public LocalDate getReservationDate(Reservation reservation) {
		TimeSlot timeslot = reservation.getTimeSlot();
		Event event = timeslot.getEvent();
		
		return event.getEventDate();
	}
	
	public List<Reservation> getReservationsByDate(LocalDate date) {
		List<Reservation> allReservations = rr.findAll();
		List<Reservation> reservationsByDate = new ArrayList<>();
		
		for (Reservation reservation : allReservations) {
			LocalDate resDate = getReservationDate(reservation);
			if (resDate.equals(date))
				reservationsByDate.add(reservation);
		}
		
		return reservationsByDate;
	}
	
	public List<Reservation> getReservationsByEvent(Event event) {
		Set<TimeSlot> eventTimeSlots = event.getTimeSlots();
		List<Reservation> eventReservations = new ArrayList<>();
		
		for (TimeSlot ts : eventTimeSlots) {
			List<Reservation> resByTimeSlot = rr.findByTimeSlot(ts);
			for (Reservation res : resByTimeSlot) {
				eventReservations.add(res);
			}
		}
		
		return eventReservations;
	}
	
	public boolean isInt(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public List<String> getAvailableTables(List<Reservation> eventReservations, List<Integer> tableSeats) {
		List<String> dropdownList = new ArrayList<>();
		
		//Key = array containing the number of people who can be seated at a table of this size, Value = default number of seats
		Integer[] tableOfEight = {7, 8, 9, 10};
		Integer[] tableOfSix = {4, 5, 6};
		Integer[] tableOfFour = {2, 3, 4};
		Integer[] tableOfTwo = {1, 2};
		
		HashMap<Integer[], Integer> seatsByTableType = new LinkedHashMap<>();
		seatsByTableType.put(tableOfTwo, 2);
		seatsByTableType.put(tableOfFour, 4);
		seatsByTableType.put(tableOfSix, 6);
		seatsByTableType.put(tableOfEight, 8);
		
		int tableIndex = -1;
		boolean found = false;
		
		List<Integer> reservedSeats = new ArrayList<>();
		
		for (Reservation res : eventReservations) {
			reservedSeats.add(res.getSeats());
		}
		
		Collections.sort(reservedSeats);
		System.out.println(reservedSeats);
		
		for (Integer rs : reservedSeats) {
			for (Integer[] possibleSeats : seatsByTableType.keySet()) {
				for (Integer seats : possibleSeats) {
					if (rs == seats) {
						System.out.println(rs);
						int value = seatsByTableType.get(possibleSeats);
						tableIndex = tableSeats.indexOf(value);
						tableSeats.remove(tableIndex);
						found = true;
						
						break;
					}
					if (found)
						break;
				}
				if (found)
					break;
			}
			found = false;
		}
		
		Collections.sort(tableSeats);
		System.out.println(tableSeats);
		
		for (Integer tableType : seatsByTableType.values()) {
			if (tableSeats.contains(tableType)) {
				dropdownList.add(Integer.toString(tableType));
			} 
			//In a future iteration, rework CSS & JS Validation to grey out list options that don't parse as integers
			/*
				 *
				 * else { dropdownList.add(Integer.toString(tableType) + " - Unavailable"); }
				 */
		}
		
		Collections.sort(dropdownList);
		System.out.println(dropdownList);
		
		return dropdownList;
	}
}
