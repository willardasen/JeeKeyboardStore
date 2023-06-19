package main;

import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {
	/*
	 * akali pindah scene sama zoom in
	 * */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new Login(primaryStage);

	}

}
