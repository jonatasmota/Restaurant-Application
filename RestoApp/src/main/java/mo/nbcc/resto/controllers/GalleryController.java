package mo.nbcc.resto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mo.nbcc.resto.model.Gallery;
import mo.nbcc.resto.services.GalleryService;

@Controller
public class GalleryController {

	private GalleryService galleryService;
	
	@Autowired
	public GalleryController(GalleryService galleryService) {
		this.galleryService = galleryService;
	}
	
	
    @GetMapping("/gallery")
    public String viewGallery(Model model) {
        model.addAttribute("listImages", galleryService.getAllActiveImages());
        return "gallery/index";
    }
    
    @GetMapping("/showNewGalleryForm")
    public String showNewGalleryForm(Model model) {
    	// create model attribute to bind form data
    	Gallery gallery = new Gallery();
    	model.addAttribute("gallery", gallery);
    	return "gallery/addImage";
    }
    
    @PostMapping("/saveImage")
    public String addNewImage(@ModelAttribute("gallery") Gallery gallery) {
    	// add product to database
    	galleryService.saveImage(gallery);
    	return "redirect:/gallery";    	
    }
    
    @GetMapping("/editImage/{id}")
    public String editImage(@PathVariable ( value = "id") long id, Model model) {
    
     Gallery gallery = galleryService.getImageById(id);
     
     model.addAttribute("gallery", gallery);
     return "gallery/editImage";
    }
    
    @GetMapping("/deleteImage/{id}")
    public String deleteImage(@PathVariable (value = "id") long id) {
     
     this.galleryService.deleteImageById(id);
     return "redirect:/gallery";
    }
    
    @GetMapping("/findImage/{name}")
    public ResponseEntity<List<Gallery>> findImage(@PathVariable(value = "name") String name) {
    	List<Gallery> images = galleryService.findImageByName(name);
    	
    	return new ResponseEntity<List<Gallery>>(images, HttpStatus.OK);
    }

    
}
