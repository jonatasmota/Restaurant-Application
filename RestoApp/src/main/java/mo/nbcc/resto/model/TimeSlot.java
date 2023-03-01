package mo.nbcc.resto.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="TIME_SLOTS")
public class TimeSlot {
	
	@Id
	@Column(name="TIME_SLOT_ID")
	@GeneratedValue
	private long id;
	
	@Column(name="HOUR")
	private int hour;
	
	@Column(name="MINUTE")
	private int minute;
	
	@OneToMany(mappedBy = "timeSlot")
	private Set<Reservation> reservations;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "EVENT_ID")
	private Event event;
	
	@Transient
	private Long eventTimeslotID;

	public TimeSlot() {
		super();
	}

	public TimeSlot(int hour, int minute, Event event) {
		super();
		this.hour = hour;
		this.minute = minute;
		this.event = event;
	}
	
	public TimeSlot(int hour, int minute, Long eventTimeslotID) {
		super();
		this.hour = hour;
		this.minute = minute;
		this.eventTimeslotID = eventTimeslotID;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	public Long getEventTimeslotID() {
		return eventTimeslotID;
	}
	
	public void setEventTimeslotID(Long eventTimeslotID) {
		this.eventTimeslotID = eventTimeslotID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	@Override
	public String toString() {
		return "TimeSlot [id=" + id + ", hour=" + hour + ", minute=" + minute + ", reservations=" + reservations
				+ ", event=" + event + "]";
	}
}
