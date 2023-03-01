package mo.nbcc.resto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mo.nbcc.resto.model.TemplateFloorPlan;

@Repository
public interface TemplateFloorPlanRepository extends JpaRepository<TemplateFloorPlan, Long> {

	List<TemplateFloorPlan> findByFloorPlanName(String name);
}
