package mo.nbcc.resto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mo.nbcc.resto.model.Gallery;
import mo.nbcc.resto.repository.GalleryRepository;

@Service
public class GalleryService {

	@Autowired
	private GalleryRepository galleryRepository;
	
	public void saveImage(Gallery gallery) {
		galleryRepository.save(gallery);	
	}

	public List<Gallery> getAllActiveImages() {
		return galleryRepository.findAll();
	}

	public Gallery getImageById(long id) {
		return galleryRepository.getReferenceById(id);
	}
	
	public void deleteImageById(long id) {
		galleryRepository.deleteById(id);
	}
	
	public List<Gallery> findImageByName(String name) {
		return galleryRepository.findGalleryByName(name);
	}
}
