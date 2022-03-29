/*
 * Kevin Drake
 * 3/28/22
 * This class Defines the items saved to an Account
 */
import java.io.*;
import java.util.*;

public class Item implements Serializable {
	private String itemName;
	private String itemDescription;
	private double price;
	
	public Item() {	
	}
	
	public Item(String name, String description, double price) {
		setItemName(name);
		setItemDescription(description);
		this.setPrice(price);
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
