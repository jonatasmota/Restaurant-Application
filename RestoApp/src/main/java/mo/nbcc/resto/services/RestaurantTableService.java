package mo.nbcc.resto.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mo.nbcc.resto.model.EventFloorPlan;
import mo.nbcc.resto.model.RestaurantTable;
import mo.nbcc.resto.repository.RestaurantTableRepository;

@Service
public class RestaurantTableService {

	private RestaurantTableRepository rtRepo;

	@Autowired
	public RestaurantTableService(RestaurantTableRepository rtRepo) {
		super();
		this.rtRepo = rtRepo;
	}
	
	public RestaurantTable getTableById(long id) {
		return rtRepo.getReferenceById(id);
	}
	
	public RestaurantTable saveTable(RestaurantTable table) {
		return rtRepo.save(table);
	}
	
	public void deleteTable(RestaurantTable table) {
		rtRepo.delete(table);
	}
	
	public List<RestaurantTable> getTablesByFloorPlan(EventFloorPlan plan){
		return rtRepo.findByFloorPlan(plan);
	}
	
	public Set<RestaurantTable> saveTables(Set<RestaurantTable> tables){
		Set<RestaurantTable> saved = new HashSet<RestaurantTable>(rtRepo.saveAll(tables));
		return saved;
	}
	
	public void deleteTables(Set<RestaurantTable> tables){
		rtRepo.deleteAll(tables);
	}
}
