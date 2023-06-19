package main;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class CatalogueAndCart {
	Stage stage;
	Scene scene;
	
	String user = CartDataContainer.getInstance().getUser();
	
	public CatalogueAndCart(Stage stage) {
		this.stage = stage;
		showCatalogueAndCart();
		stage.setScene(scene);
		stage.setTitle("USER");
		stage.show();
	}
	
	public void showCatalogueAndCart() {
		File audioFile = new File("./src/assets/lofi.mp3");
		Media audioMedia = new Media(audioFile.toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(audioMedia);
		
		mediaPlayer.play();
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		BorderPane bp = new BorderPane();
		
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		MenuItem logout;
		
		logout = new MenuItem("logout");
		
		logout.setOnAction(e->{
			Stage stage = (Stage) logout.getParentPopup().getOwnerWindow();
            stage.close();
            mediaPlayer.stop();
            new Login(stage);
		});

		menu.getItems().add(logout);
		
		menuBar.getMenus().add(menu);
		
		bp.setTop(menuBar);
		
		Label title,hint;
		title = new Label("Keyboard Catalogue");
		hint = new Label("You can Drag & drop item from the catalogue to the cart!");
		VBox hintContainer = new VBox();
		hintContainer.getChildren().add(hint);
		hintContainer.setAlignment(Pos.CENTER);
		bp.setBottom(hintContainer);
		
		ScrollPane catalogue = new ScrollPane();//utk catalogue (kiri)
		BorderPane bpCart = new BorderPane();//utk cart(kanan)
		
		
		ArrayList<Keyboard> fetchedKeyboard = KeyboardDataContainer.getInstance().getKeyboards();
		ArrayList<Cart> fetchedCarts = CartDataContainer.getInstance().getCarts();
		
		VBox keyboardContainer = new VBox();
		int index = 1;
		for(Keyboard keyboard : fetchedKeyboard) {
			GridPane gp;
			
			String name = null ,price = null ,stock = null ,desc = null;
			name = keyboard.getName();
			price = keyboard.getPrice();
			stock = keyboard.getStock();
			desc = keyboard.getDescription();
			
			Label nameLabel,priceLabel,stockLabel,descriptionLabel;

			gp = new GridPane();
			
			Image image ;
			image = new Image("file:src/assets/keyboard" + index +".png");
			
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(200);//lebar
			imageView.setFitWidth(300);//pnjg
			
			nameLabel = new Label("Name:" + name);
			priceLabel = new Label("Price:" + price);
			stockLabel = new Label("Stock:" + stock);
			descriptionLabel = new Label("description:" + desc);
			
			
			imageView.setOnMouseClicked(e->{
				viewPage(imageView, keyboard.getDescription());
			});
			
			handleDragAndDrop(imageView, bpCart, index-1);
			
			gp.add(imageView, 0, 0);
			gp.add(nameLabel, 0, 1);
			gp.add(priceLabel, 0, 2);
			gp.add(stockLabel, 0, 3);
			gp.add(descriptionLabel, 0, 4);
			
			keyboardContainer.getChildren().add(gp);
			index++;	
		}
		
		catalogue.setContent(keyboardContainer);
		
		
		
		//bagian cart(kanan)
		
		Label titleCart = new Label("Your Cart");
		VBox titleCartContainer,ItemContainer,btnCartContainer;

		titleCartContainer = new VBox();
		titleCartContainer.getChildren().add(titleCart);
		Font font = Font.font("Arial", 20);
        titleCart.setFont(font);
		titleCartContainer.setAlignment(Pos.CENTER);
		
		bpCart.setTop(titleCartContainer);
		
		int[] itemNum = {0,0,0,0};
		
		ArrayList<String> items;
		for(Cart cart : fetchedCarts) {
			if(cart.getUser().equals(user)) {
				items = cart.getKeyboards();
				
				int itemIdx = 0;
				for(String keyboardName : items) {
					if(keyboardName.equals(fetchedKeyboard.get(itemIdx).getName()) && itemNum[itemIdx] < Integer.parseInt(fetchedKeyboard.get(itemIdx).getStock()) ) {
						itemNum[itemIdx]++;
					}
					else {
						showErrorAlert("Quantity is already more than stock");
						return;
					}
					itemIdx++;
				}
				break;
			}
		}
		
		ItemContainer = new VBox();
		
		for (int i = 0 ; i < 4 ; i++) {
			if (itemNum[i] != 0) {
				Label item = new Label(fetchedKeyboard.get(i).getName() + " - " + String.valueOf(itemNum[i]));
				ItemContainer.getChildren().add(item);
			}
			
		}
		
		bpCart.setCenter(ItemContainer);
		
		Button buy,clear;
		buy = new Button("Buy");
		clear = new Button("Clear Cart");
		btnCartContainer = new VBox();
		
		buy.setMinWidth(80);
		clear.setMinWidth(80);
		
		buy.setOnMouseClicked(e->{
			int[] stock= {
					Integer.parseInt(fetchedKeyboard.get(0).getStock()) - itemNum[0], //stock keyboard ke 1
					Integer.parseInt(fetchedKeyboard.get(1).getStock()) - itemNum[1], //stock keyboard ke 2
					Integer.parseInt(fetchedKeyboard.get(2).getStock()) - itemNum[2], //stock keyboard ke 3
					Integer.parseInt(fetchedKeyboard.get(3).getStock()) - itemNum[3]  //stock keyboard ke 4
			};
			
			for(int i = 0 ; i < 4 ; i++) {
				if(stock[i] < 0 ) {
					showErrorAlert("Quantity is already more than stock");
					stock[i]++;
					return;
				}
				
				String keyboardName = fetchedKeyboard.get(i).getName();
				KeyboardDataContainer.getInstance().updateStock(keyboardName, String.valueOf(stock[i]));
			}
			//membuat cart menjadi kosong
			for(Cart cart : fetchedCarts) {
				if(cart.getUser().equals(user)) {
					cart.clearCart();
					break;
				}
			}
			new CatalogueAndCart(stage);
		
		});
		
		clear.setOnMouseClicked(e->{
			for(Cart cart : fetchedCarts) {
				if(cart.getUser().equals(user)) {
					cart.clearCart();
					break;
				}
			}
			new CatalogueAndCart(stage);
		});
		
		btnCartContainer.getChildren().addAll(buy,clear);
		
		bpCart.setBottom(btnCartContainer);
		
		SplitPane splitPane = new SplitPane(catalogue,bpCart);
		
		bp.setCenter(splitPane);
		
		scene = new Scene(bp,600,600);
	}
	
	private static final double MAX_SCALE = 2.5;
    private static final double SCALE_DELTA_IN = 1.2;
    private static final double MIN_SCALE = 0.4;
    private static final double SCALE_DELTA_OUT = 0.8;
    double currScale = 1.0;
	
	public void viewPage(ImageView imageView,String description) {
		Stage stage = new Stage();
		BorderPane bp = new BorderPane();
		GridPane gp;
		gp = new GridPane();
		
		ImageView img = new ImageView(imageView.getImage());
		img.setFitHeight(200);//lebar
		img.setFitWidth(300);//pnjg
		
		//String text = description.getText();
		Label desc = new Label(description);	
		
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
	
	int draggedIdx;
	
	public void handleDragAndDrop(ImageView imageView,BorderPane bpCart,int index) {
		//ketika image di drag 
		
		ArrayList<Cart> fetchedCarts = CartDataContainer.getInstance().getCarts();
		ArrayList<Keyboard> fetchedKeyboard = KeyboardDataContainer.getInstance().getKeyboards();
		
		imageView.setOnDragDetected(e->{
			draggedIdx = index;
			Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putImage(imageView.getImage());
			db.setContent(content);
			e.consume();
		});
		
		bpCart.setOnDragOver(e->{
			if(e.getGestureSource() != bpCart && e.getDragboard().hasImage()) {
				e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			e.consume();
		});
		
		bpCart.setOnDragDropped(e->{
			Dragboard db = e.getDragboard();
			boolean success = false;
			
			if(db.hasImage()) {
				//menvalidasi apakah user sudah pernah mempunyai database(user baru/lama)
				boolean exist = false;
				for (Cart cart : fetchedCarts) {
					if (cart.getUser().equals(user)) {
						exist = true;
						break;
					}
				}
				//membuat database kosong ke user yang baru
				if (!exist) {
					CartDataContainer.getInstance().addCart(user);
				}
				
				//tambahkan item gambar yang di drag ke dalam database user tersebut
				for(Cart cart : fetchedCarts) {
					if (cart.getUser().equals(user)) {
						cart.addKeyboard(fetchedKeyboard.get(draggedIdx).getName());
					}
				}
				success = true;
			}
			e.setDropCompleted(success);
			new CatalogueAndCart(stage);
		});
	}
	public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
	}

}
