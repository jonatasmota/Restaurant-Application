package mo.nbcc.resto.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="RESERVATIONS")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="RESERVATION_ID")
	private long reservationId;
	
	@Column(name="SEATS")
	private int seats;

	@Column(name="PHONE")
	private String phone;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="PREFERED_CONTACT")
	private String preferedContact;
	
	@Column(name="REQUESTS")
	private String requests;
	
	@Column(name="FIRSTNAME")
	private String firstName;
	
	@Column(name="LASTNAME")
	private String lastName;
	
	@Column(name="CONFIRMATION_NUMBER")
	private int confirmationNumber;
	
	/*
	 * @OneToOne(cascade=CascadeType.DETACH)
	 * 
	 * @JoinColumn(name="TIME_SLOT_ID") private TimeSlot timeSlot;
	 */
	
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="TIME_SLOT_ID", nullable = false)
	private TimeSlot timeSlot;
	
	@OneToMany(mappedBy="reservation")
	private Set<RestaurantTable> table;
		
	@Transient
	private Long resTimeSlotID;
	
	@Transient
	private String resTime;
	
	@Transient
	private Event resEvent;
	
	public Reservation() {
		super();
	}

	public Reservation(int seats, String phone, String email, String preferedContact, String requests,
			String firstName, String lastName, int confirmationNumber, Long resTimeSlotID) {
		super();
		this.seats = seats;
		this.phone = phone;
		this.email = email;
		this.preferedContact = preferedContact;
		this.requests = requests;
		this.firstName = firstName;
		this.lastName = lastName;
		this.confirmationNumber = confirmationNumber;
		this.resTimeSlotID = resTimeSlotID;
	}
	
	
	 public Reservation(int seats, String phone, String email, String preferedContact, String requests,
			 String firstName, String lastName, int confirmationNumber, TimeSlot timeSlot, Long resTimeSlotID) {
		 super();
		 this.seats = seats;
		 this.phone = phone;
		 this.email = email;
		 this.preferedContact = preferedContact;
		 this.requests = requests;
		 this.firstName = firstName;
		 this.lastName = lastName;
		 this.confirmationNumber = confirmationNumber;
		 this.timeSlot = timeSlot;
	}
	 

	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long id) {
		this.reservationId = id;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPreferedContact() {
		return preferedContact;
	}

	public void setPreferedContact(String preferedContact) {
		this.preferedContact = preferedContact;
	}

	public String getRequests() {
		return requests;
	}

	public void setRequests(String requests) {
		this.requests = requests;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(int confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	public Long getResTimeSlotID() {
		return resTimeSlotID;
	}

	public void setResTimeSlotID(Long resTimeSlotID) {
		this.resTimeSlotID = resTimeSlotID;
	}
	
	public String getResTime() {
		return resTime;
	}
	
	public void setResTime() {
		this.resTime = Integer.toString(this.timeSlot.getHour()) + ":" + Integer.toString(this.timeSlot.getMinute());
	}

	public Event getResEvent() {
		return resEvent;
	}

	public void setResEvent(Event resEvent) {
		this.resEvent = resEvent;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + reservationId + ", seats=" + seats + ", phone=" + phone + ", email=" + email
				+ ", preferedContact=" + preferedContact + ", requests=" + requests + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", confirmationNumber=" + confirmationNumber + ", timeSlot=" + timeSlot
				+ ", table=" + table + "]";
	}	
}
