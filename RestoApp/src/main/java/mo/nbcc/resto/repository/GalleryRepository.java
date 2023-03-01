package mo.nbcc.resto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mo.nbcc.resto.model.Gallery;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
	public List<Gallery> findGalleryByName(String name);
}
