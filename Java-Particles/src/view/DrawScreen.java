package view;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import model.Particle;

public class DrawScreen extends Canvas {

    private GraphicsContext gtx;

    private int amount_particles = 0;
    private int particles_per_klik = 50;

    private final List<Particle> particles;
    private Particle attractor;

    public DrawScreen() {
        particles = new ArrayList<>();
        init();
        build();
    }

    private void init() {
        this.setWidth(Main.WIDTH);
        this.setHeight(Main.HEIGHT);
        gtx = this.getGraphicsContext2D();
    }
    

    private void build() {
        attractor = new Particle(this.getWidth() / 2, this.getHeight() / 2);
        attractor.setR(20);
        
        this.setOnMouseDragged(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                attractor.setX(e.getX());
                attractor.setY(e.getY());
            } else if (e.getButton() == MouseButton.SECONDARY) {
            	
            	for (int i = 0; i < 10; i++) {
            		if (amount_particles < 100000) {
            			amount_particles++;
            			createParticle(e.getX(), e.getY(), attractor);
            		}
            	}
        	}

        });
        
        gtx.setFill(Color.rgb(0, 0, 0, 1));
        gtx.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        
        this.setOnMouseClicked(e -> {
            //get type of mouse click
            MouseButton button = e.getButton();

            //if left mouse click, change pos of attractor to mouse pos
            if (button == MouseButton.PRIMARY) {
                attractor.setX(e.getX());
                attractor.setY(e.getY());
            } 
        });
        
        
        
    }

    private void createParticle(double x, double y, Particle a) {
        Particle p = new Particle(x, y, a);
        particles.add(p);
    }

    public void draw() {
        gtx.setFill(Color.rgb(0, 0, 0, 0.5));
        gtx.fillRect(0, 0, this.getWidth(), this.getHeight());

        particles.forEach(p -> {
            p.draw(gtx);
        });

        attractor.draw(gtx);

        gtx.fillText("Particles: " + amount_particles + "\nGravity: " + Particle.G, 20, 50);
    }

    public void update() {
        particles.forEach(p -> {
//        	p.flee(particles);
            p.update();
        });
    }

}
