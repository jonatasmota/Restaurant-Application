package mo.nbcc.resto.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FLOOR_PLAN")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PLAN_TYPE", discriminatorType = DiscriminatorType.INTEGER, columnDefinition = "TINYINT")
public abstract class FloorPlan {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FLOOR_PLAN_ID")
	private long floorPlanId;
	
	@Column(name="FLOOR_PLAN_NAME", nullable = false)
	private String floorPlanName;
	
	@OneToMany(mappedBy="floorPlan")
	private Set<RestaurantTable> tables;

	public FloorPlan() {
		super();
	}

	public FloorPlan(String floorPlanName, Set<RestaurantTable> tables) {
		super();
		this.floorPlanName = floorPlanName;
		this.tables = tables;
	}

	public long getFloorPlanId() {
		return floorPlanId;
	}

	public void setFloorPlanId(long floorPlanId) {
		this.floorPlanId = floorPlanId;
	}

	public String getFloorPlanName() {
		return floorPlanName;
	}

	public void setFloorPlanName(String floorPlanName) {
		this.floorPlanName = floorPlanName;
	}

	public Set<RestaurantTable> getTables() {
		return tables;
	}

	public void setTables(Set<RestaurantTable> tables) {
		this.tables = tables;
	}

	@Override
	public String toString() {
		return "FloorPlan [floorPlanId=" + floorPlanId + ", floorPlanName=" + floorPlanName + ", tables=" + tables
				+ "]";
	}
	
	
}
