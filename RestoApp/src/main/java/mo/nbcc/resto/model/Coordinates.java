package mo.nbcc.resto.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TABLE_COORDINATE")
public class Coordinates {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="COORDINATE_ID")
	private long cId;
	
	@OneToOne(mappedBy="coordinates")
	private RestaurantTable table;
	
	@Column(name="X_AXIS", nullable = false)
	private float xAxis;
	
	@Column(name="Y_AXIS", nullable = false)
	private float yAxis;

	public Coordinates() {
		super();
	}

	public Coordinates(RestaurantTable table, float xAxis, float yAxis) {
		super();
		this.table = table;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	public long getcId() {
		return cId;
	}

	public void setcId(long cId) {
		this.cId = cId;
	}

	public RestaurantTable getTable() {
		return table;
	}

	public void setTable(RestaurantTable table) {
		this.table = table;
	}

	public float getxAxis() {
		return xAxis;
	}

	public void setxAxis(float xAxis) {
		this.xAxis = xAxis;
	}

	public float getyAxis() {
		return yAxis;
	}

	public void setyAxis(float yAxis) {
		this.yAxis = yAxis;
	}

	@Override
	public String toString() {
		return "Coordinates [cId=" + cId + ", table=" + table + ", xAxis=" + xAxis + ", yAxis=" + yAxis + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cId, table, xAxis, yAxis);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		return cId == other.cId && Objects.equals(table, other.table)
				&& Float.floatToIntBits(xAxis) == Float.floatToIntBits(other.xAxis)
				&& Float.floatToIntBits(yAxis) == Float.floatToIntBits(other.yAxis);
	}
	
	
}
