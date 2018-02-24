package model;

import java.util.List;

import application.Main;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Particle {
    
	public static final double MAX_SIZE = 6; 
	public static final double MIN_SIZE = 1;
    public static double G = 300;
//    private final double vel_strength = 0.1;
    public final double MIN_SPEED = 1;
    public final double MAX_SPEED = 60;
    public final double MAX_FORCE = 30;
    private double r;
    
    private Particle attractor;
    private final Vector pos;
    private Vector vel;
    private Vector acc;
    private Color color;

    public Particle(double x, double y) {
        this(x, y, null);
    }
    
    public static void changeGravity(String dir) {
    	double GRAV_STEP = 20;
    	if (dir.equals("w"))
    		Particle.G += GRAV_STEP;
    	else if (dir.equals("s"))
    		Particle.G -= GRAV_STEP;
    	else if (dir.equals("g")) {
    		Particle.G = (Particle.G == 0) ? 300 : 0;
    	}
    } 
    
    
    public Particle(double x, double y, Particle attractor) {
    	this.r = MIN_SIZE + Math.random()* (MAX_SIZE-MIN_SIZE);
        pos = new Vector(x, y);
        acc = new Vector(0, 0);
//        color = Color.color(Math.random(), Math.random(), Math.random());
        double rg = Math.random();
        color = Color.color(1, 1, r/MAX_SIZE);
        if (attractor != null)
        	this.attractor = attractor;
        initVel();
    }
    
    private void initVel(){
    	this.vel = new Vector(Math.random(), Math.random());
    	this.vel.limit(MAX_SPEED);
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
        double dist = dir.magnitude();
        dsquared = constrain(dsquared, 0, 200);
        double strength = (G / dsquared) / (r);
        
        Vector desired = dir;
        
        if (dist < attractor.r*r) {
        	desired.add(dir.mult(-4/r));
        }

        this.acc = desired.setMag(strength);
    }
    
    public void flee(Particle other) {
    	
    	if (this.distance(other) < this.r * 2) {
    		Vector desired = Vector.sub(other.getPos(), this.getPos()).limit(MAX_SPEED);
        	Vector steer = Vector.sub(desired, this.vel).limit(MAX_SPEED).mult(-1);
        	this.applyForce(steer.limit(MAX_FORCE));
    	}
    }
    
    public void flee(List<Particle> particles) {
    	Vector desired = new Vector(0,0); 
    	Vector steer = new Vector(0,0);
    	
    	for (Particle p : particles) {
    		if (this.distance(p) < this.r*2) {
	    		desired = Vector.sub(this.getPos(), p.getPos());
	    		steer.add(desired);
    		}
    	}
    	this.applyForce(steer.limit(MAX_FORCE));
    	
    }
    
    public void attracted2() {
        
    	Vector desired = Vector.sub(attractor.getPos(), this.getPos()).limit(MAX_SPEED);
    	Vector steer = Vector.sub(desired, this.vel).limit(MAX_SPEED);
    	
    	double mag = steer.magnitude();
        double dist = this.distance(attractor);
        
//        desired.setMag(mag * 0.999);
        
//        if (dist > 200) {
//        	desired.setMag(MAX_SPEED);
//        } else {
//        	desired.setMag(dist/200);
//        }
        
        System.out.println("Steer: "+ steer +" - Magnitude: " + mag + " - Dist: " + dist);

        
//        Vector steer = desired.limit(MAX_SPEED).sub(this.vel).limit(MAX_FORCE);
//        System.out.println("Distance: " + dist + "\nSteer: " + steer);
        this.applyForce(steer.limit(MAX_FORCE));
       
    }
    
    public double distance(Particle other) {
    	return this.pos.sub(other.pos).magnitude();
    }
    
//    public void seek() {
//		Vector desired = attractor.getPos().sub(getPos());
//		desired.setMag(MAX_SPEED);
//		Vector steer = desired.sub(this.vel);
//		this.applyForce(steer);
//	}
//    
//    public void seek(Vector target) {
//		Vector desired = target.sub(getPos());
//		desired.setMag(MAX_SPEED);
//		Vector steer = desired.sub(this.vel);
//		this.applyForce(steer);
//	}
    
    public void applyForce(Vector force) {
    	this.acc.add(force).limit(MAX_FORCE);
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
        
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        vel.add(acc); // .limit(MAX_SPEED / (r*r));
        vel.setMag(constrain(vel.magnitude(), MIN_SPEED, MAX_SPEED/r));
        acc.set(0, 0);


        attracted();
//        attracted2();
//        seek();
        edges();
    }
    
    public void edges() {
    	double damp = 0.2;
    	
    	if((pos.getX() <= this.r && this.vel.getX() < 0) || 
    			(pos.getX() >= Main.WIDTH - this.r && this.vel.getX() > 0) )  {
    		vel.setX(vel.getX() * -1);
    		vel.mult(damp);
    	}

    	if( (pos.getY() <= this.r && this.vel.getY() < 0)|| 
    			(pos.getY() >= Main.HEIGHT - this.r && this.vel.getY() > 0)){
    		vel.setY(vel.getY() * -1);
    		vel.mult(damp);
    	}
    }

    public void draw(GraphicsContext gtx) {
    	//        gtx.setFill(Color.rgb(100, 105, 255, 1));
    	gtx.setFill( (r < MAX_SIZE) ? color : Color.RED);
    	gtx.fillOval(this.pos.getX() - this.r/2, this.pos.getY() - this.r/2, this.r, this.r);
    }
}
