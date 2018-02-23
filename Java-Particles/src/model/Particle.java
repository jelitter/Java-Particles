package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Particle {
    
    private final double G = 250;
    private final double vel_strength = 1;
    private double r;
    
    private Particle attractor;
    private final Vector pos;
    private Vector vel;
    private Vector acc;
    private Color color;

    public Particle(double x, double y) {
//    	this(x, y, attractor);
    	r = 2;
        pos = new Vector(x, y);
        acc = new Vector(0, 0);
        color = Color.color(Math.random(), Math.random(), Math.random());
        detVel();
    }
    
    public Particle(double x, double y, Particle attractor) {
    	r = 2;
        pos = new Vector(x, y);
        acc = new Vector(0, 0);
        color = Color.color(Math.random(), Math.random(), Math.random());
        this.attractor = attractor;
        detVel();
    }
    
    private void detVel(){
        this.vel = new Vector(Math.random() * vel_strength * 2 - vel_strength, Math.random() * vel_strength * 2 - vel_strength);
    }

    public Vector getPos() {
        return pos;
    }

    public void setX(double x) {
        this.pos.setX(x);
    }

    public void setY(double y) {
        this.pos.setY(y);
    }

    public void attracted() {
        Vector dir = Vector.sub(attractor.getPos(), pos);
        double dsquared = dir.magSq();
        dsquared = constrain(dsquared, 100, 400);
        double strength = G / dsquared;
        dir.setMag(strength);
        this.acc = dir;
    }

    private double constrain(double getal, double min, double max) {
        if (getal > max) {
            return max;
        } else if (getal < min) {
            return min;
        } else {
            return getal;
        }
    }

    public void update() {
        pos.add(vel);
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        
        
        if(pos.getX() <= 20){
            vel.touchObject();
        }
        
        if(pos.getX() >= primaryScreenBounds.getWidth() - 20){
            vel.touchObject();
        }
        
        if(pos.getY() <= 20){
            vel.touchObject();
        }
        
        if(pos.getY() >= primaryScreenBounds.getHeight() - 50){
            vel.touchObject();
        }
        
        vel.add(acc);
        attracted();
    }

    public void draw(GraphicsContext gtx) {
//        gtx.setFill(Color.rgb(100, 105, 255, 1));
        gtx.setFill(color);
        gtx.fillOval(this.pos.getX(), this.pos.getY(), this.r, this.r);
    }
}
