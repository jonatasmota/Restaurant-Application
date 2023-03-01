package mo.nbcc.resto.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "EVENT")
public class Event {
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EVENT_ID")
	private Long eventId;
	
	@Column(name="EVENT_NAME", nullable = false)
	private String eventName;
	
	@Column(name="EVENT_DATE", nullable = false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate eventDate;
	
	@Column(name="EVENT_DETAILS")
	private String eventDetails;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="EVENT_FLOORPLAN")
	private EventFloorPlan eventFloorPlan;
	
	@ManyToOne()
	@JoinColumn(name = "EVENT_MENU", nullable = false)
	private Menu eventMenu;
	
	@OneToMany(mappedBy = "event")
	private Set<TimeSlot> timeSlots;
	
	@Transient
	private Long eventMenuID;
	
	public Event() {
		super();
	}

	public Event(String eventName, LocalDate eventDate, String eventDetails, Menu eventMenu) {
		super();
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventDetails = eventDetails;
		this.eventMenu = eventMenu;
	}
	
	public Event(String eventName, LocalDate eventDate, String eventDetails, Long eventMenuID) {
		super();
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventDetails = eventDetails;
		this.eventMenuID = eventMenuID;
	}

	public Set<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(Set<TimeSlot> timeSlots) {
		this.timeSlots = timeSlots;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(String eventDetails) {
		this.eventDetails = eventDetails;
	}

	public EventFloorPlan getEventFloorPlan() {
		return eventFloorPlan;
	}

	public void setEventFloorPlan(EventFloorPlan eventFloorPlan) {
		this.eventFloorPlan = eventFloorPlan;
	}

	public Menu getEventMenu() {
		return eventMenu;
	}

	public void setEventMenu(Menu eventMenu) {
		this.eventMenu = eventMenu;
	}

	public Long getEventMenuID() {
		return eventMenuID;
	}

	public void setEventMenuID(Long eventMenuID) {
		this.eventMenuID = eventMenuID;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", eventName=" + eventName + ", eventDate=" + eventDate + ", eventDetails="
				+ eventDetails + ", eventFloorPlan=" + eventFloorPlan + ", eventMenu=" + eventMenu + ", timeSlots="
				+ timeSlots + "]";
	}

	

	
	
	
}
