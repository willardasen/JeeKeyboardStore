package main;

import java.util.ArrayList;

public class KeyboardDataContainer {
	private ArrayList<Keyboard> keyboards;
	
	private KeyboardDataContainer() {
		keyboards = new ArrayList<>();
	}
	
	private static KeyboardDataContainer instance;
	
	public static KeyboardDataContainer getInstance() {
		if(instance == null) {
			instance = new KeyboardDataContainer();
		}
		return instance;
	}
	
	public void addKeyboard(String name, String price, String stock, String description) {
		 for (Keyboard keyboard : keyboards) {
	            if (keyboard.getName().equals(name)) {
	                return;
	            }
	        }
        Keyboard keyboard = new Keyboard(name, price, stock, description);
        keyboards.add(keyboard);
    }
	
	public void updateKeyboard(String name, String newPrice, String newStock, String newDescription) {
		for (Keyboard keyboard : keyboards) {
            if (keyboard.getName().equals(name)) {
                keyboard.setPrice(newPrice);
                keyboard.setStock(newStock);
                keyboard.setDescription(newDescription);
                break;
            }
        }
	}
	
	public void updateStock(String name, String newStock) {
		for (Keyboard keyboard : keyboards) {
            if (keyboard.getName().equals(name)) {
                keyboard.setStock(newStock);
                break;
            }
        }
	}
	
	public ArrayList<Keyboard> getKeyboards() {
        return keyboards;
    }

}
