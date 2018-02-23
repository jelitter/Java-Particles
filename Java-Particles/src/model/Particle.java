package model;

import application.Main;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Particle {
    
	public static final double MAX_SIZE = 10; 
	public static final double MIN_SIZE = 2;
    private final double G = 200;
    private final double vel_strength = 3;
    public final double MAX_SPEED = 3;
    public final double MAX_FORCE = 2;
    private double r;
    
    private Particle attractor;
    private final Vector pos;
    private Vector vel;
    private Vector acc;
    private Color color;

    public Particle(double x, double y) {
//    	this(x, y, attractor);
        this(x, y, null);
    }
    
    public Particle(double x, double y, Particle attractor) {
    	this.r = Math.floor(MIN_SIZE + Math.random()*(MAX_SIZE-MIN_SIZE));
        pos = new Vector(x, y);
        acc = new Vector(0, 0);
//        color = Color.color(Math.random(), Math.random(), Math.random());
        double rg = Math.random();
        color = Color.color(rg, rg, 1);
        if (attractor != null)
        	this.attractor = attractor;
        detVel();
    }
    
    private void detVel(){
//        this.vel = new Vector(Math.random() * vel_strength * 2 - vel_strength, Math.random() * vel_strength * 2 - vel_strength);
    	this.vel = new Vector(Math.random(), Math.random());
    	this.vel.setMag(vel_strength);
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
    
    public void setR(double r) {
    	this.r = r;
    }

    public void attracted() {
        Vector dir = Vector.sub(attractor.getPos(), pos);
        double dsquared = dir.magSq();
        dsquared = constrain(dsquared, 50, 200);
        double strength = (G / dsquared) * (1/r);
        dir.setMag(strength);
        this.acc = dir;
    }
    
    public void seek() {
		Vector desired = attractor.getPos().sub(getPos());
		desired.setMag(1).mult(MAX_SPEED);
		Vector steer = desired.sub(this.vel);
		this.applyForce(steer);
	}
    
    public void applyForce(Vector force) {
    	this.acc.add(force);
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
        
        
        if(pos.getX() <= this.r){
//            vel.touchObject();
        	vel.setX(vel.getX() * -1);
        }
        
//        if(pos.getX() >= primaryScreenBounds.getWidth() - 20){
        if(pos.getX() >= Main.WIDTH - this.r){
//            vel.touchObject();
        	vel.setX(vel.getX() * -1);
        }
        
        if(pos.getY() <= this.r){
//            vel.touchObject();
        	vel.setY(vel.getY() * -1);
        }
        
//        if(pos.getY() >= primaryScreenBounds.getHeight() - 50){
        if(pos.getY() >= Main.HEIGHT - this.r){
//            vel.touchObject();
        	vel.setY(vel.getY() * -1);
        }
        
        vel.add(acc);
//        acc.setMag(0);
        
        attracted();
//        seek();
    }

    public void draw(GraphicsContext gtx) {
//        gtx.setFill(Color.rgb(100, 105, 255, 1));
        gtx.setFill(color);
        gtx.fillOval(this.pos.getX(), this.pos.getY(), this.r, this.r);
    }
}
