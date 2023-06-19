package main;

import java.util.ArrayList;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Login {
	Stage stage;
	Scene sc;
	
	public Login(Stage stage) {
		this.stage = stage;
		loginPage();
		stage.setScene(sc);
		stage.setTitle("Login");
		stage.show();
	}
	
	
	public void loginPage() {
		GridPane gp;
		
		Label judul, mail, pass;
		TextField email;
		PasswordField password;			
		Button logBtn, regBtn;
		HBox HbBtn;
		Alert alert = null;
		
		KeyboardDataContainer.getInstance().addKeyboard("Igoltech Keebs XO200", "800000", "56","Classic black and yellow combination with modern architecture." );
		KeyboardDataContainer.getInstance().addKeyboard("Dark Black RGB", "1500000", "116" ,"RGB LED has come to this futuristic keyboard with elegant style." );
		KeyboardDataContainer.getInstance().addKeyboard("Watermelon Keebs Z200", "750000", "9","Did you ever seen watermelon in keyboard? Now you see!" );
		KeyboardDataContainer.getInstance().addKeyboard("Igoltech Classic Keebs", "1250000", "56","Business keyboard with modern design make your style cool." );
		
		UserDataContainer.getInstance().addUser("test@email.com","test");//dummy data
			
		ArrayList<User> fetchedUser = UserDataContainer.getInstance().getUsers();
		
		//setscene
		gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(5);
		gp.setVgap(5);
		sc = new Scene(gp, 600, 600);
		
		//title
		judul = new Label("Jee Keyboard Store");
		judul.setStyle("-fx-font-weight: bold;");
		judul.setStyle("-fx-font-size: 20px");
		gp.add(judul, 0, 0);
		GridPane.setColumnSpan(judul, 3);
		GridPane.setHalignment(judul, HPos.CENTER);
				
		//email
		mail = new Label("Email : ");
		email = new TextField();
		gp.add(mail, 0, 1);
		gp.add(email, 1, 1);
		
		//pass
		pass = new Label("Password : ");
		password = new PasswordField();
		gp.add(pass, 0, 2);
		gp.add(password, 1, 2);
		
		//btn
		logBtn = new Button("Login");
		logBtn.setMinWidth(90);
		logBtn.setStyle("-fx-background-color: #1F1F1F;");
		logBtn.setTextFill(Color.WHITE);
		
		regBtn = new Button("Register");
		regBtn.setMinWidth(90);
		regBtn.setStyle("-fx-background-color: #1F1F1F;");
		regBtn.setTextFill(Color.WHITE);
		
		HbBtn = new HBox();
		HbBtn.getChildren().addAll(logBtn, regBtn);
		HbBtn.setSpacing(5);
		gp.add(HbBtn, 1, 4);
		
		//btn click		
		logBtn.setOnAction(e ->{
			String enteredEmail = email.getText();
            String enteredPassword = password.getText();
            
            if (enteredEmail.equals("admin") && enteredPassword.equals("admin")) {
            	Stage stage = (Stage) logBtn.getScene().getWindow();
                stage.close();
                new AdminPage(stage);
                return;
            }

            if (enteredEmail.isEmpty() && enteredPassword.isEmpty()) {
                Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Login Failed");
                resetBtnAlert.setHeaderText("Please fill email and password");
                resetBtnAlert.show();
                return;
            }
            else if (enteredEmail.isEmpty()) {
                Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Login Failed");
                resetBtnAlert.setHeaderText("Email can't be empty");
                resetBtnAlert.show();
                return;
            }
            else if (enteredPassword.isEmpty()) {
                Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Login Failed");
                resetBtnAlert.setHeaderText("Password can't be empty");
                resetBtnAlert.show();
                return;
            }
            
            boolean valid = false;
            for (User user : fetchedUser) {
            	if(user.getEmail().equals(enteredEmail) && user.getPassword().equals(enteredPassword)) {
            		user.setEmail(enteredEmail);
            		user.setPassword(enteredPassword);
            		fetchedUser.add(user);
            		CartDataContainer.getInstance().setUser(enteredEmail);
            		Stage stage = (Stage) logBtn.getScene().getWindow();
                    stage.close();
                    new UserWindow(stage);
                    return;
            	}
            }
          	
            if(!valid) {
            	Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Login Failed");
                resetBtnAlert.setHeaderText("Email or password is wrong");
                resetBtnAlert.show();
                return;
            }
		});
		
		regBtn.setOnAction(e ->{
			Stage stage = (Stage) regBtn.getScene().getWindow();
            stage.close();
            new Register(stage);
		});
		
	}
	
	
}
