package mo.nbcc.resto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mo.nbcc.resto.model.Event;
import mo.nbcc.resto.model.EventFloorPlan;
import mo.nbcc.resto.model.FloorPlan;
import mo.nbcc.resto.repository.FloorPlanRepository;

@Service
public class FloorPlanService {

	private FloorPlanRepository fpRepo;

	@Autowired
	public FloorPlanService(FloorPlanRepository fpRepo) {
		super();
		this.fpRepo = fpRepo;
	}
	
	public List<EventFloorPlan> getAllFloorPlans(){
		return fpRepo.findAll();
	}
	
	public EventFloorPlan getFloorPlanById(long id) {
		return fpRepo.getReferenceById(id);
	}
	
	public EventFloorPlan saveFloorPlan(EventFloorPlan plan) {
		return fpRepo.save(plan);
	}
	
	public void deleteFloorPlan(EventFloorPlan plan) {
		fpRepo.delete(plan);
	}
	
	public List<EventFloorPlan> searchFloorPlanByName(String name){
		return fpRepo.findByFloorPlanName(name);
	}
	
}
