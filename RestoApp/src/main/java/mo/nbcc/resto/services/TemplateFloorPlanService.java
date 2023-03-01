package mo.nbcc.resto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mo.nbcc.resto.model.TemplateFloorPlan;
import mo.nbcc.resto.repository.TemplateFloorPlanRepository;

@Service
public class TemplateFloorPlanService {

	private TemplateFloorPlanRepository tfpr;

	@Autowired
	public TemplateFloorPlanService(TemplateFloorPlanRepository tfpr) {
		this.tfpr = tfpr;
	}
	
	public List<TemplateFloorPlan> getAllTemplates(){
		return tfpr.findAll();
	}
	
	public TemplateFloorPlan getTemplateById(long id) {
		return tfpr.getReferenceById(id);
	}
	
	public TemplateFloorPlan saveTemplate(TemplateFloorPlan plan) {
		return tfpr.save(plan);
	}
	
	public void deleteTemplate(TemplateFloorPlan plan) {
		tfpr.delete(plan);
	}
	
	public List<TemplateFloorPlan> searchFloorPlanByName(String name){
		return tfpr.findByFloorPlanName(name);
	}
	
}
