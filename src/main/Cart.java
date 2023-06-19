package main;

import java.util.ArrayList;

public class Cart {
	private String user;
	private ArrayList<String> keyboards;
	
	public Cart(String user, ArrayList<String> keyboards) {
		this.user = user;
		this.keyboards = keyboards;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ArrayList<String> getKeyboards() {
		return keyboards;
	}

	public void setKeyboards(ArrayList<String> keyboards) {
		this.keyboards = keyboards;
	}
	
	public void addKeyboard(String keyboard) {
		this.keyboards.add(keyboard);
	}
	
	public void clearCart() {
		this.keyboards.clear();
	}	
}
