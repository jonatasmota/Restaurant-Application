package mo.nbcc.resto.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "MENU")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MENU_ID")
	private long menuId;
	
	@Column(name = "MENU_NAME", nullable = false)
	private String menuName;

	@Column(name = "MENU_DETAILS")
	private String menuDetails;

	@Column(name = "MENU_CREATED_DATE", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate menuCreated;

	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
	private Set<MenuItem> items;

	@OneToMany(mappedBy = "eventMenu")
	private Set<Event> events;

	public Menu() {
		super();
	}

	public Menu(String menuName, String menuDetails, LocalDate menuCreated, Set<MenuItem> items, Set<Event> events) {
		super();
		this.menuName = menuName;
		this.menuDetails = menuDetails;
		this.menuCreated = menuCreated;
		this.items = items;
		this.events = events;
	}

	public Menu(long menuId, String menuName, LocalDate menuCreated, String menuDetails) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuDetails = menuDetails;
		this.menuCreated = menuCreated ;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDetails() {
		return menuDetails;
	}

	public void setMenuDetails(String menuDetails) {
		this.menuDetails = menuDetails;
	}

	public LocalDate getMenuCreated() {
		return menuCreated;
	}

	public void setMenuCreated(LocalDate menuCreated) {
		this.menuCreated = menuCreated;
	}

	public Set<MenuItem> getItems() {
		return items;
	}

	public void setItems(Set<MenuItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", menuName=" + menuName + ", menuDetails=" + menuDetails + ", menuCreated="
				+ menuCreated + ", items=" + items + ", events=" + events + "]";
	}

}
