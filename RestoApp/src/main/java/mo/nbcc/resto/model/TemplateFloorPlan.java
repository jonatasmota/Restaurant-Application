package mo.nbcc.resto.model;

import java.util.Set;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class TemplateFloorPlan extends FloorPlan{

	public TemplateFloorPlan() {
		super();
	}

	public TemplateFloorPlan(String floorPlanName, Set<RestaurantTable> tables) {
		super(floorPlanName, tables);
	}
	
	
}
