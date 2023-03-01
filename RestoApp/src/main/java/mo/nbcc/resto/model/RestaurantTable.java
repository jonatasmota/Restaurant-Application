package mo.nbcc.resto.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RESTAURANT_TABLE")
public class RestaurantTable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TABLE_ID")
	private long tableId;
	
	@Column(name="NUMBER_OF_SEATS", nullable = false)
	private int numberOfSeats;
	
	@Column(name="TABLE_NUMBER", nullable = false)
	private int tableNumber;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="FLOOR_PLAN_ID", nullable = false)
	private EventFloorPlan floorPlan;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="TEMPLATE_ID", nullable = true)
	private EventFloorPlan template;
	
	@ManyToOne(optional = true)
	@JoinColumn(name="TABLE_RESERVATION")
	private Reservation reservation;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="TABLE_COORDINATES")
	private Coordinates coordinates;

	public RestaurantTable() {
		super();
	}

	public RestaurantTable(int numberOfSeats, int tableNumber, EventFloorPlan floorPlan, Reservation reservation) {
		super();
		this.numberOfSeats = numberOfSeats;
		this.tableNumber = tableNumber;
		this.floorPlan = floorPlan;
		this.reservation = reservation;
	}

	public long getTableId() {
		return tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public EventFloorPlan getFloorPlan() {
		return floorPlan;
	}

	public void setFloorPlan(EventFloorPlan floorPlan) {
		this.floorPlan = floorPlan;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String toString() {
		return "RestaurantTable [tableId=" + tableId + ", numberOfSeats=" + numberOfSeats + ", tableNumber="
				+ tableNumber + ", floorPlan=" + floorPlan + ", reservation=" + reservation + "]";
	}
	
	
}
