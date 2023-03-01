package mo.nbcc.resto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mo.nbcc.resto.model.Event;
import mo.nbcc.resto.model.EventFloorPlan;
import mo.nbcc.resto.model.FloorPlan;

@Repository
public interface FloorPlanRepository extends JpaRepository<EventFloorPlan, Long> {

	public List<EventFloorPlan> findByFloorPlanName(String name);
	
}
