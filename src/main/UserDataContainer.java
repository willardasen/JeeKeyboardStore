package main;

import java.util.ArrayList;

public class UserDataContainer {
	private ArrayList<User> users;

    private UserDataContainer() {
        users = new ArrayList<>();
    }

    private static UserDataContainer instance;

    public static UserDataContainer getInstance() {
        if (instance == null) {
            instance = new UserDataContainer();
        }
        return instance;
    }

    public void addUser(String email, String password) {
        User user = new User(email, password);
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
