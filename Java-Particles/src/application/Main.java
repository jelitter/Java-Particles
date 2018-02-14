package application;
	
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MyVector;
import model.Particle;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	private static int NUM_PARTICLES = 50;
	private ArrayList<Particle> particles;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = new AnchorPane();
			Scene scene = new Scene(root,1200, 900);

			particles = new ArrayList<Particle>();
			
			for (int i = 0; i < NUM_PARTICLES; i++) {
				Particle p = new Particle();
				particles.add(p);
				root.getChildren().add(p);
			}
			
			primaryStage.setTitle("Particle test");
			primaryStage.setScene(scene);
			primaryStage.show();
			
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
	    		double x = 860;
    			double y = 540;
    			MyVector target = new MyVector(x, y);
	    		for (Particle p: particles) {
//	    			p.rotate();
	    			p.seek(target);
	    			p.edges();
//	    			p.update();
	    		}
	    	}
	    }.start();
	}
}
