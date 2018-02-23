package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
//import model.Particle;
import model.Vehicle;
import view.MainScreen;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Main extends Application {

	private static int NUM_PARTICLES = 20;
	public static final double WIDTH = 1300;
	public static final double HEIGHT = 900;

	private ArrayList<Vehicle> vehicles;
	private Point2D target;

	@Override
	public void start(Stage primaryStage) {

		MainScreen root = new MainScreen(this);
		Scene scene = new Scene(root, 800, 600);

		primaryStage.setTitle("PhysX");
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {root.stopGameLoop(); });
	}

	public static void main(String[] args) {
		launch(args);
	}
}
