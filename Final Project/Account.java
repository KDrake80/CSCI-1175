/*
 * Kevin Drake
 * 3/28/22
 * This Class defines the Accounts the client uses
 */
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Account implements Serializable, Collection {
	private String accountName;
	private String accountPassword;
	private Date dateCreated;
	private int itemCount;
	private List<Item> items;
	private final String accountID;
	
	public Account() {
		dateCreated = new Date();
		items = new ArrayList<Item>();
		accountID = "BA" + (int)(Math.random() * 100);
	}
	public Account(String name) {
		items = new ArrayList<Item>();
		accountName = name;
		dateCreated = new Date();
		itemCount = 0;
		accountID = "BA" + (int)(Math.random() * 100);
	} 
	public Account(String name, String pass) {
		items = new ArrayList<Item>();
		accountName = name;
		accountPassword = pass;
		dateCreated = new Date();
		itemCount = 0;
		accountID = "BA" + (int)(Math.random() * 100);
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountPassword() {
		return accountPassword;
	}
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void addItem(String name, String description, double price) {
		Item i = new Item(name, description, price);
		items.add(i);
		itemCount++;
	}
	public void addItem(Item i) {
		items.add(i);
		itemCount++;
	}
	public Item getItem(int index) {
		return items.get(index);
	}
	public void removeItem(int index) {
		items.remove(index);
		itemCount--;
	}
	public List<Item> getList(){
		return items;
	}
	@Override
	public int size() {
		return items.size();
	}
	@Override
	public boolean isEmpty() {
		if (items.size() > 0)
			return false;
		else
			return true;
	}
	@Override
	public boolean contains(Object o) {
		return false;
	}
	@Override
	public Iterator iterator() {	
		return items.iterator();
	}
	@Override
	public Object[] toArray() {
		return items.toArray();
	}
	@Override
	public Object[] toArray(Object[] a) {
		return null;
	}
	@Override
	public boolean add(Object e) {
		return false;
	}
	@Override
	public boolean remove(Object o) {
		// Extra Method from Collections interface
		return false;
	}
	@Override
	public boolean containsAll(Collection c) {
		// Extra Method from Collections interface
		return false;
	}
	@Override
	public boolean addAll(Collection c) {
		// Extra Method from Collections interface
		return false;
	}
	@Override
	public boolean removeAll(Collection c) {
		// Extra Method from Collections interface
		return false;
	}
	@Override
	public boolean retainAll(Collection c) {
		// Extra Method from Collections interface
		return false;
	}
	@Override
	public void clear() {
		items.clear();
		
	}
	public String getAccountID() {
		return accountID;
	}
}
