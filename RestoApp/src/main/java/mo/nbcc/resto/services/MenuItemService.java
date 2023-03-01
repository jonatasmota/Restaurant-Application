package mo.nbcc.resto.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import mo.nbcc.resto.model.Menu;
import mo.nbcc.resto.model.MenuItem;
import mo.nbcc.resto.repository.MenuItemRepository;
import mo.nbcc.resto.repository.MenuRepository;

@Service
public class MenuItemService {
	
	private MenuItemRepository menuItemRepo;
	private MenuRepository menuRepo;

	@Autowired
	public MenuItemService(MenuItemRepository menuItemRepo, MenuRepository menuRepo) {
		super();
		this.menuItemRepo = menuItemRepo;
		this.menuRepo = menuRepo;
	}
	
	public Menu getMenuItemsByMenuId(Long menuId) {
		return menuRepo.getReferenceById(menuId);
	}
	
	//display all menu items records
	public List<MenuItem> getAllMenuItems(Set<MenuItem> set){
		return menuItemRepo.findAll();
	}
	
	//pagination
	public Page<MenuItem> getAllMenuItems(Pageable pageable){
		return menuItemRepo.findAll(pageable);
	}
	
	public Page<MenuItem> getMenuItemByName(String value, Pageable pageable) {
		MenuItem menuItem = new MenuItem();
		menuItem.setMenuItemName(value);
		
		ExampleMatcher.GenericPropertyMatcher propertyMatcher = ExampleMatcher.GenericPropertyMatchers.contains()
				.ignoreCase();
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher("menuItemName", propertyMatcher);
		Example<MenuItem> menuItemExample = Example.of(menuItem, exampleMatcher);
		return menuItemRepo.findAll(menuItemExample, pageable);
	}
	
	public MenuItem saveMenuItem(MenuItem menuItem) {
		return menuItemRepo.save(menuItem);
	}
	
	public MenuItem getMenuItemById(Long menuItemId) {
		return menuItemRepo.getReferenceById(menuItemId);
	}
		
	public void deleteMenuItem(MenuItem menuItem) {
		menuItemRepo.delete(menuItem);
	}
	public void deleteByMenuItemId(Long menuItemId) {
		menuItemRepo.deleteById(menuItemId);
	}
	
	public List<MenuItem> getMenuItemByMenu(Menu menu){
		return menuItemRepo.findByMenu(menu);
	}
	
	public Set<MenuItem> saveMenuItems(Set<MenuItem> menuItems){
		Set<MenuItem> savedMenuItems = new HashSet<MenuItem>(menuItemRepo.saveAll(menuItems));
		return savedMenuItems;
	}
	
	public void deleteMenuItems(Set<MenuItem> menuItems) {
		menuItemRepo.deleteAll(menuItems);
	}
}
