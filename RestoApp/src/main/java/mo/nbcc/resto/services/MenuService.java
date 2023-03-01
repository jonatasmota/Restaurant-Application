package mo.nbcc.resto.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mo.nbcc.resto.model.Menu;
import mo.nbcc.resto.repository.MenuRepository;

@Service
public class MenuService {

	private MenuRepository menuRepo;

	@Autowired
	public MenuService(MenuRepository menuRepo) {
		super();
		this.menuRepo = menuRepo;
	}
		
	//display all menu records
	public List<Menu> getAllMenus(){
		return menuRepo.findAll();
	}
	
	//pagination
	public Page<Menu> getAllMenus(Pageable pageable){
		return menuRepo.findAll(pageable);
	}
	
	public Page<Menu> getMenuByName(String value, Pageable pageable) {
		Menu menu = new Menu();
		menu.setMenuName(value);

		ExampleMatcher.GenericPropertyMatcher propertyMatcher = ExampleMatcher.GenericPropertyMatchers.contains()
				.ignoreCase();
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher("menuName", propertyMatcher);
		Example<Menu> menuExample = Example.of(menu, exampleMatcher);
		return menuRepo.findAll(menuExample, pageable);
	}
	
	public Menu saveMenu(Menu menu) {
		return menuRepo.save(menu);
	}
		
	public Menu getMenuById(Long menuId) {
		return menuRepo.getReferenceById(menuId);
	}
	
	public void deleteMenuById(Long menuId) {
		menuRepo.deleteById(menuId);
	}
	
	public void deleteMenu(Menu menu) {
		menuRepo.delete(menu);
	}

	public Optional<Menu> getUniqueMenuByNameAndDate(String name, LocalDate date) {
		return Optional.ofNullable(menuRepo.findByMenuNameAndMenuCreated(name, date));
	}
	
}


