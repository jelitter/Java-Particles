package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Particle;
import view.MainScreen;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static final double WIDTH = 1800;
	public static final double HEIGHT = 1000;

	@Override
	public void start(Stage primaryStage) {

		MainScreen root = new MainScreen(this);
		Scene scene = new Scene(root, WIDTH, HEIGHT);

		primaryStage.setTitle("PhysX");
		primaryStage.setScene(scene);
		//		primaryStage.setMaximized(true);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {root.stopGameLoop(); });

		scene.setOnKeyTyped(k -> {
			String c = k.getCharacter();
			System.out.println("key typed: " + k.getCharacter());
			Particle.changeGravity(c);
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
