package mo.nbcc.resto.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;

@Entity
@Table(name = "MENU_ITEM")
public class MenuItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MENU_ITEM_ID")
	private long menuItemId;
	
	@Column(name="ITEM_NAME", nullable = false)
	private String menuItemName;
	
	@Column(name="ITEM_DESCRIPTION")
	private String menuItemDescription;
	
	@Column(name="ITEM_PRICE", nullable = false)
	@Digits(integer=8, fraction=2)
	private float menuItemPrice;
	
	@Column(name="ITEM_IMG_URL")
	private String menuItemImgUrl;

	@ManyToOne()
	@JoinColumn(name="ITEM_MENU_ID", nullable = false)
	public Menu menu;
	
	
	public MenuItem() {
		super();
	}

	public MenuItem(String menuItemName, String menuItemDescription, float menuItemPrice, String menuItemImgUrl, Menu menu) {
		super();
		this.menuItemName = menuItemName;
		this.menuItemDescription = menuItemDescription;
		this.menuItemPrice = menuItemPrice;
		this.menuItemImgUrl = menuItemImgUrl;
		this.menu = menu;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public long getMenuItemId() {
		return menuItemId;
	}


	public void setMenuItemId(long menuItemId) {
		this.menuItemId = menuItemId;
	}


	public String getMenuItemName() {
		return menuItemName;
	}


	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}


	public String getMenuItemDescription() {
		return menuItemDescription;
	}


	public void setMenuItemDescription(String menuItemDescription) {
		this.menuItemDescription = menuItemDescription;
	}


	public float getMenuItemPrice() {
		return menuItemPrice;
	}


	public void setMenuItemPrice(float menuItemPrice) {
		this.menuItemPrice = menuItemPrice;
	}
	
	public String getMenuItemImgUrl() {
		return menuItemImgUrl;
	}

	public void setMenuItemImgUrl(String menuItemImgUrl) {
		this.menuItemImgUrl = menuItemImgUrl;
	}

	@Override
	public String toString() {
		return menuItemName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(menu, menuItemDescription, menuItemId, menuItemName, menuItemPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		return Objects.equals(menu, other.menu) && Objects.equals(menuItemDescription, other.menuItemDescription)
				&& menuItemId == other.menuItemId && Objects.equals(menuItemName, other.menuItemName)
				&& Float.floatToIntBits(menuItemPrice) == Float.floatToIntBits(other.menuItemPrice);
	}

}
