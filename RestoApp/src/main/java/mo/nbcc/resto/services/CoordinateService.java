package mo.nbcc.resto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mo.nbcc.resto.model.Coordinates;
import mo.nbcc.resto.repository.CoordinateRepository;

@Service
public class CoordinateService {

	@Autowired
	private CoordinateRepository cRepo;
	
	public Coordinates getCoordinate(long id) {
		return cRepo.getReferenceById(id);
	}
	
	public Coordinates saveCoordinates(Coordinates coordinates) {
		return cRepo.save(coordinates);
	}
	
	public void deleteCoordinates(Coordinates coordinates) {
		cRepo.delete(coordinates);
	}
}
