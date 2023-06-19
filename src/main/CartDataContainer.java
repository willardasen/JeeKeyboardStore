package main;

import java.util.ArrayList;

public class CartDataContainer {
	private String user;
	private ArrayList<Cart> carts;
	
	private CartDataContainer() {
		carts = new ArrayList<>();
	}
	
	private static CartDataContainer instance;
	
	public static CartDataContainer getInstance() {
		if (instance == null) {
			instance = new CartDataContainer();
		}
		return instance;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getUser() {
		return user;
	}
	
	public void addCart(String user) {
		Cart cart = new Cart(user, new ArrayList<String>());
		carts.add(cart);
	}
	
	public ArrayList<Cart> getCarts(){
		return carts;
	}
	
}
