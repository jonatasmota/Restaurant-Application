package mo.nbcc.resto.repository;


import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mo.nbcc.resto.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>{
	
	public Menu findByMenuNameAndMenuCreated(String name, LocalDate date);
	
}
