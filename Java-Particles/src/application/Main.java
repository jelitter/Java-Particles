package application;
	
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MyVector;
import model.Particle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	public static final double WIDTH = 1600;
	public static final double HEIGHT = 800;
	
	private static int NUM_PARTICLES = 50;
	private ArrayList<Particle> particles;
	private Particle target;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = new Pane();
			root.setStyle("-fx-background-color: #eeddcc; "); 
			
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			

			particles = new ArrayList<Particle>();
			
			for (int i = 0; i < NUM_PARTICLES; i++) {
				Particle p = new Particle();
				p.localToParent(root.getLayoutBounds());
				particles.add(p);
				root.getChildren().add(p);
			}
			
			primaryStage.setTitle("Particle test");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			root.setOnMouseClicked(e -> {
				target = new Particle(e.getX(), e.getY());
				System.out.println("New target:" + e.getX() + ", " + e.getY());
				animate();
			});
			
			double x = WIDTH/2;
			double y = HEIGHT/2;
			target = new Particle(x,y);
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
	    		for (Particle p: particles) {
//	    			p.rotate();
	    			p.seek(target.pos);
//	    			p.print();
//	    			target.print();
	    			p.edges();
	    			p.update();
//	    			p.update2();
	    		}
	    	}
	    }.start();
	}
}
