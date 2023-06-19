package main;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Register {
	Stage stage;
	Scene sc;
	
	public Register(Stage stage) {
		this.stage = stage;
		RegisterPage();
		stage.setScene(sc);
		stage.setTitle("Register");
		stage.show();
	}
	
	public void RegisterPage() {
		GridPane gp;
		Label judul, mail, pass, confirmPass;
		TextField email;
		PasswordField password, confirmPassword;			
		Button resetBtn, regBtn,loginBtn;
		HBox HbBtn;
		Alert alert = null;
		
		ArrayList<User> fetchedUser = UserDataContainer.getInstance().getUsers();
			
		//setscene
		gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(5);
		gp.setVgap(5);
		sc = new Scene(gp, 600, 600);
				
		//title
		judul = new Label("Register");
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
		
		//confirm pass
		confirmPass = new Label("Confirm Password : ");
		confirmPassword = new PasswordField();
		gp.add(confirmPass, 0, 3);
		gp.add(confirmPassword, 1, 3);
				
		//btn
		resetBtn = new Button("Reset");
		resetBtn.setMinWidth(90);
		resetBtn.setStyle("-fx-background-color: #1F1F1F;");
		resetBtn.setTextFill(Color.WHITE);
		
		loginBtn = new Button("Login");
		loginBtn.setMinWidth(90);
		loginBtn.setStyle("-fx-background-color: #1F1F1F;");
		loginBtn.setTextFill(Color.WHITE);
		
		regBtn = new Button("Register");
		regBtn.setMinWidth(90);
		regBtn.setStyle("-fx-background-color: #1F1F1F;");
		regBtn.setTextFill(Color.WHITE);
		
		HbBtn = new HBox();
		HbBtn.getChildren().addAll(resetBtn,loginBtn, regBtn);
		HbBtn.setSpacing(5);
		gp.add(HbBtn, 1, 4);
	
		//btn click				
		resetBtn.setOnAction(e ->{
			email.setText("");
			password.setText("");
			confirmPassword.setText("");
		});
		
		loginBtn.setOnAction(e->{
			Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.close();
            new Login(stage);
		});
					
		regBtn.setOnAction(e ->{
			String enteredEmail = email.getText();
            String enteredPassword = password.getText();
            String enteredConfirmPassword = confirmPassword.getText();

            if (enteredEmail.isEmpty() && enteredPassword.isEmpty() && enteredConfirmPassword.isEmpty()) {
                Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Register Failed");
                resetBtnAlert.setHeaderText("Please fill email, password, or confirm password");
                resetBtnAlert.show();
                return;
            }
            else if (enteredEmail.isEmpty()) {
                Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Register Failed");
                resetBtnAlert.setHeaderText("Email can't be empty");
                resetBtnAlert.show();
                return;
            }
            else if (!enteredEmail.endsWith("@email.com")) {
            	Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Register Failed");
                resetBtnAlert.setHeaderText("Email must end with @email.com");
                resetBtnAlert.show();
                return;
            }
            else if (enteredEmail.indexOf('@') != enteredEmail.lastIndexOf('@')) {
            	Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Register Failed");
                resetBtnAlert.setHeaderText("Email must have only one @");
                resetBtnAlert.show();
                return;
            }
            else if (enteredEmail.contains(" ")) {
            	Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Register Failed");
                resetBtnAlert.setHeaderText("Email can't contain a space");
                resetBtnAlert.show();
                return;
            }
            else if (enteredEmail.startsWith("@")) {
            	Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Register Failed");
                resetBtnAlert.setHeaderText("Email must not start with @");
                resetBtnAlert.show();
                return;
            }

            else if (enteredPassword.isEmpty()) {
                Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Register Failed");
                resetBtnAlert.setHeaderText("Password can't be empty");
                resetBtnAlert.show();
                return;
            }
            else if (enteredConfirmPassword.isEmpty()) {
                Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Register Failed");
                resetBtnAlert.setHeaderText("Confirm Password can't be empty");
                resetBtnAlert.show();
                return;
            }
            else if (!enteredPassword.equals(enteredConfirmPassword)) {
                Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                resetBtnAlert.setTitle("Register Failed");
                resetBtnAlert.setHeaderText("Confirm Password doesn't match with password");
                resetBtnAlert.show();
                return;
            }
            
            for (User user : fetchedUser) {
            	if(user.getEmail().equals(enteredEmail)) {
            		Alert resetBtnAlert = new Alert(Alert.AlertType.ERROR);
                    resetBtnAlert.setTitle("Register Failed");
                    resetBtnAlert.setHeaderText("This email already exist");
                    resetBtnAlert.show();
                    return;
            	}
            }
            
            UserDataContainer.getInstance().addUser(enteredEmail, enteredPassword);
            
            Stage stage = (Stage) regBtn.getScene().getWindow();
            stage.close();
            new Login(stage);
            
		});
	}
	
	
}
