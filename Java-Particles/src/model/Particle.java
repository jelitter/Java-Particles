package model;

import application.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {
    
	public static enum Type { NORMAL, ATTRACTOR };
	public Type type;
	public static double MAX_SIZE = 6; 
	public static double MIN_SIZE = 0.5;
	public static double REVIVE_CHANCE = 0.01;
    public static double G = 300;
    public static double GRAV_STEP = 50;
//    private final double vel_strength = 0.1;
    public double MIN_SPEED = 1;
    public double MAX_SPEED = 5;
    public double MAX_FORCE = 0.1;
    public final double LIFE_PER_R = 10;
    private double size;
    
    private double life;
    private Particle attractor;
    private final Vector pos;
    private Vector vel;
    private Vector acc;
    private Color color;
    public boolean isDead;

    public Particle(double x, double y) {
        this(x, y, null);
    }
    
    public Particle(double x, double y, Particle attractor) {
    	this.size = MIN_SIZE + Math.random()* (MAX_SIZE-MIN_SIZE);
    	this.life = size * LIFE_PER_R;
    	this.isDead = false;
    	this.type = Type.NORMAL;
        this.pos = new Vector(x, y);
        this.acc = new Vector(0, 0);
//        color = Color.color(Math.random(), Math.random(), Math.random());
//        double rg = Math.random();
        this.setColor();
        if (attractor != null)
        	this.attractor = attractor;
        initVel();
    }
    
    private void initVel(){
    	this.vel = new Vector(-1 + 2 * Math.random(), -1 + 2 * Math.random());
    	this.vel.setMag(Math.random() * MAX_SPEED);
    	
    	this.acc = new Vector(-1 + 2 * Math.random(), -1 + 2 * Math.random());
    	this.acc.setMag(Math.random() * MAX_FORCE);
    }
    
    public static void changeGravity(String dir) {
    	if (dir.equals("w"))
    		Particle.G += GRAV_STEP;
    	else if (dir.equals("s"))
    		Particle.G -= GRAV_STEP;
    	else if (dir.equals("g")) {
    		Particle.G = (Particle.G == 0) ? 300 : 0;
    	}
    } 
    
    public void setType(Type type) {
    	this.type = type;
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
    
    public void setR(double size) {
    	this.size = size;
    }
    
    public double getSize() {
    	return this.size;
    }

    public void attracted() {
    	
    	if (type == Type.NORMAL) {
	        Vector dir = Vector.sub(attractor.getPos(), pos);
	        double dsquared = dir.magSq();
	//        double dist = dir.magnitude();
	        dsquared = constrain(dsquared, 0, 200);
	        double strength = (G / dsquared) / (size);
	        
	        Vector desired = dir;
	        
	//        if (dist < attractor.r) {
	//        	desired.add(dir.mult(-4/r));
	//        }
	
	        this.acc = desired.setMag(strength);
    	}
    }
    
    public void flee(double x, double y) {

    	Vector other = new Vector(x,y);
    	Vector desired = new Vector(0,0); 
    	Vector steer = new Vector(0,0);


    	desired = Vector.sub(this.getPos(), other); // .mult(-1);
    	steer.add(desired.limit(MAX_SPEED));
    	this.applyForce(steer.limit(MAX_FORCE));

    }
    
    public void flee(Particle other) {
    	flee(other.getPos().getX(), other.getPos().getY());
    }
    
    public void seek(double x, double y) {

    	Vector other = new Vector(x,y);
//    	Vector desired = new Vector(0,0); 
//    	Vector steer = new Vector(0,0);
//
//
//    	desired = Vector.sub(other, this.getPos());
//    	steer.add(desired);
//    	this.applyForce(steer.limit(MAX_FORCE));
    	
		Vector desired = other.sub(getPos());
		desired.limit(MAX_SPEED);
		Vector steer = desired.sub(this.vel).mult(-1);
		this.applyForce(steer);
    }
    
    public void seek(Particle other) {
    	flee(other.getPos().getX(), other.getPos().getY());
    }
    

    
    public void attracted2() {
    	Vector desired = Vector.sub(attractor.getPos(), this.getPos()).limit(MAX_SPEED);
    	Vector steer = Vector.sub(desired, this.vel).limit(MAX_SPEED);
    	
    	double mag = steer.magnitude();
        double dist = this.distance(attractor);
        
        System.out.println("Steer: "+ steer +" - Magnitude: " + mag + " - Dist: " + dist);
        this.applyForce(steer);
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
//    	this.acc.add(force).limit(MAX_FORCE/size);
    	this.acc.add(force).limit(MAX_FORCE/size);
    }
    
    public void revive() {
    	
    	this.size = MIN_SIZE + Math.random() * (MAX_SIZE-MIN_SIZE);
    	this.life = size * LIFE_PER_R;
    	
//    	this.vel.set(0, 0);
    	this.initVel();
    	
    	
//    	this.acc.set(0, 0);
        this.setColor();
        this.isDead = false;
    }
    
    public void setColor() {
    	if (type == Type.NORMAL)
    		color = Color.color(1, 1, constrain(size/MAX_SIZE, 0, 1));
    	else
    		color = Color.RED;
    }
    
    public void setSize() {
    	if (type == Type.NORMAL) {
	    	life -= 0.1;
	    	size = life / LIFE_PER_R;
    	}
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
    	
    	if (!isDead) {
    		setSize();
    		if (life < 0.1) {
//    			color = Color.BLACK;
//	    		size = 0;
	    		isDead= true;
    		}
    		setColor();
//    		vel.setMag(constrain(vel.magnitude(), MIN_SPEED, MAX_SPEED));
    		vel = vel.limit(MAX_SPEED);
    		pos.add(vel);
    		edges();
    		vel.add(acc);
    		acc.set(0, 0);
//    		attracted();
//        attracted2();
//        seek();
    	} else {
			if (Math.random() < REVIVE_CHANCE) {
				revive();
			}
		}
    }
    
//    public void edges() {
//    	
//    	double damp;
//    	
//    	if (type == Type.NORMAL)
//    		damp = 0.2;
//    	else
//    		damp = 1;
//    	
//    	if((pos.getX() <= this.size / 2 && this.vel.getX() < 0) || 
//    			(pos.getX() >= (Main.WIDTH - this.size -90) && this.vel.getX() > 0) )  {
//    		vel.setX(vel.getX() * -1);
//    		vel.mult(damp);
//    	}
//
//    	if( (pos.getY() <= this.size / 2 && this.vel.getY() < 0)|| 
//    			(pos.getY() >= (Main.HEIGHT - this.size) && this.vel.getY() > 0)){
//    		vel.setY(vel.getY() * -1);
//    		vel.mult(damp);
//    	}
//    }
    
    public void edges() {
    	
    	double safeDistance = getSize() * 2;
    	
    	if (pos.getX() < getSize() / 2) {
    		pos.setX(getSize() / 2);
    	}
    	
    	if (pos.getY() < getSize() / 2) {
    		pos.setY(getSize() / 2);
    	}
    	
    	if (pos.getX() > Main.WIDTH -90 -getSize()) {
    		pos.setX(Main.WIDTH -90 -getSize());
    	}
    	
    	if (pos.getY() > Main.HEIGHT -getSize()) {
    		pos.setY(Main.HEIGHT -getSize());
    	}
    	
    	double distanceRight = Main.WIDTH - 90 - pos.getY();
    	double distanceBottom = Main.HEIGHT - pos.getY();
    	
    	Vector top = new Vector(0, MAX_SPEED).setMag( safeDistance / pos.getY());
    	Vector bottom = new Vector(0, -MAX_SPEED).setMag( safeDistance / distanceBottom);
    	
    	Vector left = new Vector(MAX_SPEED, 0).setMag( safeDistance / pos.getX());
    	Vector right = new Vector(-MAX_SPEED, 0).setMag( safeDistance / distanceRight);
    	
    	Vector total = top.add(bottom).add(left).add(right).limit(MAX_SPEED);
    	if (pos.getX() < safeDistance || pos.getY() < safeDistance || distanceRight < safeDistance || distanceBottom < safeDistance) {
    		total.setMag(MAX_SPEED);
    	}
    	
    	
//    	System.out.println("Top: " + top + " -- Bottom: " + bottom);
//    	System.out.println("Total: " + total + " -- Pos: " + getPos());
    	
    	applyForce(total);
    	
//    	applyForce(top);
//    	applyForce(bottom);
//    	applyForce(left);
//    	applyForce(right);
    	
    }

    public void draw(GraphicsContext gtx) {
    	gtx.setFill( (size <= MAX_SIZE) ? color : Color.RED);
    	gtx.fillOval(this.pos.getX() - this.size/2, this.pos.getY() - this.size/2, this.size, this.size);
    }
}
