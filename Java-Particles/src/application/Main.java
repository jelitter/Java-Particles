package application;
	
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
//import model.Particle;
import model.Vehicle;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	private static int NUM_PARTICLES = 1;
	public static final double WIDTH = 1300;
	public static final double HEIGHT = 900;

	private ArrayList<Vehicle> vehicles;
	private Point2D target;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = new Pane();
			root.setStyle("-fx-background-color: #eeddcc; "); 
			
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			

			vehicles = new ArrayList<Vehicle>();
			
			for (int i = 0; i < NUM_PARTICLES; i++) {
				vehicles.add(new Vehicle());
			}
			
			root.getChildren().addAll(vehicles);
			
			primaryStage.setTitle("Particle test");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			root.setOnMouseClicked(e -> {
				target = new Point2D(e.getX(), e.getY());
				System.out.println("New target:" + e.getX() + ", " + e.getY());
				animate();
			});
			
			double x = WIDTH/2;
			double y = HEIGHT/2;
			target = new Point2D(x,y); // Initial target
			animate();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void animate() {
		new AnimationTimer() {
	    	@Override
	    	public void handle(long now) {
	    		for (Vehicle v: vehicles) {
	    			v.seek(target);
	    			v.update();
	    			v.display();
	    			v.print();
	    			if (v.getPos().distance(target) < 5) {
	    				double x = Math.floor(Math.random() * WIDTH);
	    				double y = Math.floor(Math.random() * HEIGHT);;
	    				
	    				target = new Point2D(x, y);
	    				animate();
	    			}
	    		}
	    	}
	    }.start();
	}
}
