package mo.nbcc.resto.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("0")
public class EventFloorPlan extends FloorPlan{
	
	
	@OneToOne(mappedBy="eventFloorPlan")
	private Event event;
	
	public EventFloorPlan() {
		super();
	}
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public EventFloorPlan(String floorPlanName, Set<RestaurantTable> tables, Event event) {
		super(floorPlanName, tables);
		this.event = event;
	}

	public EventFloorPlan(TemplateFloorPlan plan, Event event) {
		super(plan.getFloorPlanName(), plan.getTables());
		this.event = event;
	}
	

}
