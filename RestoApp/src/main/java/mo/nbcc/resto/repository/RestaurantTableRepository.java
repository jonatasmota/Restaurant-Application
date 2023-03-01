package mo.nbcc.resto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mo.nbcc.resto.model.EventFloorPlan;
import mo.nbcc.resto.model.RestaurantTable;
import mo.nbcc.resto.model.TemplateFloorPlan;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long>{

	List<RestaurantTable> findByFloorPlan(EventFloorPlan plan);
	
	List<RestaurantTable> findByFloorPlan(TemplateFloorPlan plan);
}
