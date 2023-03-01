package mo.nbcc.resto.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mo.nbcc.resto.model.Coordinates;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinates, Long>{

	
}

