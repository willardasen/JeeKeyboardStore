package main;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class AdminPage {
	Stage stage;
	Scene scene;
	
	public AdminPage(Stage stage) {
		this.stage = stage;
		manageKeyboard();
		stage.setScene(scene);
		stage.setTitle("Admin");
		stage.show();
	}
	
	public void manageKeyboard() {
		BorderPane bp = new BorderPane(); 
		ScrollPane scrollPane = new ScrollPane();
		
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		MenuItem logout = new MenuItem("logout");
		
		logout.setOnAction(e->{
			Stage stage = (Stage) logout.getParentPopup().getOwnerWindow();
            stage.close();
            new Login(stage);
		});
		
		menu.getItems().add(logout);
		
		menuBar.getMenus().add(menu);
		
		bp.setTop(menuBar);
		
		ArrayList<Keyboard> fetchedKeyboard = KeyboardDataContainer.getInstance().getKeyboards();
		
		VBox keyboardContainer = new VBox();
		int i = 1;
		for(Keyboard keyboard : fetchedKeyboard) {
			GridPane gp;
			
			String name = null ,price = null ,stock = null ,desc = null;
			name = keyboard.getName();
			price = keyboard.getPrice();
			stock = keyboard.getStock();
			desc = keyboard.getDescription();
			
			Label nameLabel,priceLabel,stockLabel,descriptionLabel;
			TextField nameFill,priceFill,stockFill,descriptionFill;
			Button update = new Button("Update");
			
			gp = new GridPane();
			
			Image image ;
			image = new Image("file:src/assets/keyboard" + i +".png");
			
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(200);//lebar
			imageView.setFitWidth(300);//pnjg
			
			nameLabel = new Label("Name:");
			priceLabel = new Label("Price:");
			stockLabel = new Label("Stock:");
			descriptionLabel = new Label("description:");
			
			nameFill = new TextField(name);
			nameFill.setDisable(true);
			priceFill = new TextField(price);
			stockFill = new TextField(stock);
			descriptionFill = new TextField(desc);
			
			update.setOnMouseClicked((e)->{
				validate(priceFill, stockFill, descriptionFill);
				KeyboardDataContainer.getInstance().updateKeyboard(nameFill.getText(), priceFill.getText(), stockFill.getText(), descriptionFill.getText());
			});
			
			imageView.setOnMouseClicked(e->{
				viewPage(imageView,descriptionFill);
			});
			
			gp.add(imageView, 0, 0);
			gp.add(nameLabel, 1, 0);
			gp.add(nameFill, 2, 0);
			gp.add(priceLabel, 3, 0);
			gp.add(priceFill, 4, 0);
			gp.add(stockLabel, 5, 0);
			gp.add(stockFill, 6, 0);
			
			gp.add(descriptionLabel, 1, 1);
			gp.add(descriptionFill, 2, 1);
			gp.setColumnSpan(descriptionFill, 4);
			
			gp.add(update, 2, 2);
			
			keyboardContainer.getChildren().add(gp);
			i++;	
		}
		
		scrollPane.setContent(keyboardContainer);
		
		bp.setCenter(scrollPane);
	
		//nanti diubah
		scene = new Scene(bp,600,600);
	}
	
	public void validate(TextField priceFill, TextField stockFill, TextField descriptionFill) {
		String pf = priceFill.getText();
		String sf = stockFill.getText();
		String df = descriptionFill.getText();
		
		
		if(pf.isEmpty()) {
			showErrorAlert("Make sure price is not empty");
			return;
		}
		
		if(!pf.matches("\\d+")) {
			showErrorAlert("Make sure price is numeric");
			return;
		}
		
		if(Integer.parseInt(pf) <= 0) {
			showErrorAlert("Make sure price is more than zero");
			return;
		}
		
		if(sf.isEmpty()) {
			showErrorAlert("Make sure stock is not empty");
			return;
		}
		
		if(!sf.matches("\\d+")) {
			showErrorAlert("Make sure stock is numeric");
			return;
		}
		
		if(Integer.parseInt(sf) < 0) {
			showErrorAlert("Make sure stock is more than or equal to zero");
			return;
		}
		
		if(df.isEmpty()) {
			showErrorAlert("Make sure description is not empty");
			return;
		}
	}
	
	public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
	}
	
	private static final double MAX_SCALE = 2.5;
    private static final double SCALE_DELTA_IN = 1.2;
    private static final double MIN_SCALE = 0.4;
    private static final double SCALE_DELTA_OUT = 0.8;
    double currScale = 1.0;
    
	public void viewPage(ImageView imageView,TextField description) {
		Stage stage = new Stage();
		BorderPane bp = new BorderPane();
		GridPane gp;
		gp = new GridPane();
		
		ImageView img = new ImageView(imageView.getImage());
		img.setFitHeight(200);//lebar
		img.setFitWidth(300);//pnjg
		
		String text = description.getText();
		Label desc = new Label(text);
		
		Button rotateLeft,rotateRight,zoomIn,zoomOut;
		
		rotateLeft = new Button("Rotate Left");
		rotateRight = new Button("Rotate Right");
		zoomIn = new Button("Zoom In");
		zoomOut= new Button("Zoom Out");
		
		rotateLeft.setOnMouseClicked(e->{
			Rotate left;
			left = new Rotate(-90,img.getFitWidth()/2,img.getFitHeight()/2);
			img.getTransforms().add(left);
		});
		
		rotateRight.setOnMouseClicked(e->{
			Rotate right;
			right = new Rotate(90,img.getFitWidth()/2,img.getFitHeight()/2);
			img.getTransforms().add(right);
		});
		
		//TODO: nanti di tes yg zoom in zoom out
		zoomIn.setOnMouseClicked(e->{ 
			double newScale = currScale * SCALE_DELTA_IN;
			if(newScale > MAX_SCALE) {
				newScale = MAX_SCALE;
			}
			
			img.setScaleX(newScale);
			img.setScaleY(newScale);
			
			currScale = newScale;
		});
		
		zoomOut.setOnMouseClicked(e->{
			double newScale = currScale * SCALE_DELTA_OUT;

            if (newScale < MIN_SCALE) {
                newScale = MIN_SCALE;
            }

            img.setScaleX(newScale);
            img.setScaleY(newScale);

            currScale = newScale;
		});
		
		gp.setHgap(5);
		gp.setVgap(5);
		gp.setPadding(new Insets(10));
		
		zoomIn.setMinWidth(80);
		zoomOut.setMinWidth(80);
		rotateLeft.setMinWidth(80);
		rotateRight.setMinWidth(80);
		
		gp.add(rotateLeft, 0, 0);
		gp.add(rotateRight, 0, 1);
		gp.add(zoomIn, 1, 0);
		gp.add(zoomOut, 1, 1);
		gp.setAlignment(Pos.CENTER);
		
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(img,desc,gp);
		vBox.setAlignment(Pos.CENTER);
		
		//TODO: ganti
		bp.setCenter(vBox);
		
		Scene scene = new Scene(bp, 500, 500);
		stage.setScene(scene);
		stage.setTitle("View Image");
		stage.show();
	}

}
