package main;

import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UserWindow {
	Stage stage;
	Scene scene;
	
	public UserWindow(Stage stage) {
		this.stage = stage;
		showWindow();
		stage.setScene(scene);
		stage.setTitle("USER");
		stage.show();
	}
	
	public void showWindow() {
		
//		KeyboardDataContainer.getInstance().addKeyboard("Igoltech Keebs XO200", "800000", "56","Classic black and yellow combination with modern architecture." );
//		
//		String name = null,price,stock,decs;
//		
//		ArrayList<Keyboard> fetchedKeyboard = KeyboardDataContainer.getInstance().getKeyboards();
//		
//		for(Keyboard keyboard : fetchedKeyboard) {
//			name = keyboard.getName();
//			price = keyboard.getPrice();
//			stock = keyboard.getStock();
//			decs = keyboard.getStock();
//		}
		
		
		GridPane gp = new GridPane();
		BorderPane bp = new BorderPane();
		VBox container = new VBox();
		
		Label title = new Label("NEW KEYBOARD ARRIVED");
		Font font = Font.font("Arial", FontWeight.BLACK, 40);
        title.setFont(font);
		
		File file = new File("./src/assets/keyboard.mp4");
		Media media = new Media(file.toURI().toString());
		MediaPlayer player = new MediaPlayer(media);
		MediaView view = new MediaView(player);
		
		view.setFitHeight(300);
		view.setFitWidth(400);
		
		player.setAutoPlay(true);
		player.setCycleCount(MediaPlayer.INDEFINITE);
		
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		MenuItem viewKeyboard,logout;
		
		logout = new MenuItem("logout");
		viewKeyboard = new MenuItem("View Keyboard");
		
		viewKeyboard.setOnAction(e->{
			Stage stage = (Stage) viewKeyboard.getParentPopup().getOwnerWindow();
            stage.close();
            player.stop();
            new CatalogueAndCart(stage);
		});
		
		logout.setOnAction(e->{
			Stage stage = (Stage) logout.getParentPopup().getOwnerWindow();
            stage.close();
            player.stop();
            new Login(stage);
		});
		
		menu.getItems().addAll(viewKeyboard,logout);
		
		menuBar.getMenus().add(menu);
			
		Button continueBtn = new Button("CONTINUE>>");
		continueBtn.setStyle("-fx-background-color: #555555;");
		continueBtn.setTextFill(Color.WHITE);
		
		
		continueBtn.setOnAction(e->{
			Stage stage = (Stage) continueBtn.getScene().getWindow();
            stage.close();
            player.stop();
            new CatalogueAndCart(stage);
		});

 		container.getChildren().add(view);
 		container.setAlignment(Pos.CENTER);
 		container.setPadding(new Insets(20));
 		
		gp.setAlignment(Pos.CENTER);
		gp.add(title, 0, 0);
		gp.add(container, 0, 1);
		gp.add(continueBtn, 0, 2);
 		
		bp.setCenter(gp);
		
		scene = new Scene(bp,600,600);
		
	}

}
