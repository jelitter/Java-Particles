package view;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import model.Particle;
import model.Vector;

public class DrawScreen extends Canvas {

    private GraphicsContext gtx;

    private int numAlive = 0;
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
        attractor.MAX_SPEED = 20;
        
        this.setOnMouseDragged(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                attractor.setX(e.getX());
                attractor.setY(e.getY());
            } else if (e.getButton() == MouseButton.SECONDARY) {
            	
            	for (int i = 0; i < 2; i++) {
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

        if (numAlive > 0) {
        gtx.fillText("Particles: " + amount_particles + 
        			 "\nAlive:  " + numAlive + " ("+ (numAlive*100/amount_particles) +"%)" +
        		     "\nGravity: " + Particle.G
        		     , 20, 50);
        } else {
        	gtx.fillText("Particles: " + amount_particles + 
       			 "\nAlive:  " + numAlive +
       		     "\nGravity: " + Particle.G
       		     , 20, 50);
        }
        
    }

    public void update() {

    	double sumx = 0;
    	double sumy = 0;
    	numAlive = 0;
    	
    	for (Particle p : particles) {
    		if (!p.isDead) {
    			numAlive++;
	    		sumx += p.getPos().getX();
	    		sumy += p.getPos().getY();
    		}
    	};
    	if (numAlive > 2) {
	    	sumx = sumx / numAlive;
	    	sumy = sumy / numAlive;
//	    	System.out.println("Sumx, sumy: " + sumx + ", " + sumy);
//	    	attractor.flee(new Particle(sumx, sumy));
	    	if ((sumx < 1) ||
	    	   (sumy < 1) ||
	    	   (sumx > Main.WIDTH) ||
	    	   (sumy > Main.HEIGHT)) {
	    		attractor.setX(Main.WIDTH/2);
	            attractor.setY(Main.HEIGHT/2);
	    	} else {
	    		attractor.setX(sumx);
	    		attractor.setY(sumy);
	    	}
	    	attractor.edges();
	    	
    	} else {
    		attractor.setX(Main.WIDTH/2);
            attractor.setY(Main.HEIGHT/2);
    	}
    	
        
        for (Particle p : particles) {
        	if (!p.isDead) {
        		p.update();
        	} else {
        		if (Math.random() < 0.005) {
        			p.revive();
        		}
        	}
        }
    }

}
 