package model;


import application.Main;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Vehicle extends Polygon {

	final double MAXSPEED = 1.0;
	final double MAXFORCE = 0.2;
	int sides = 3;
	double health, r;
	Color color;
	private Point2D pos;
	private Point2D vel;
	private Point2D acc;
	
	public Vehicle() {
		
		this.r = (float) (30 + Math.random() * 20);
		this.color = Color.color(Math.random(), Math.random(), Math.random());
		this.setFill(this.color);
		this.setStroke(this.color.darker());
		this.setStrokeWidth(this.r/10);
		this.setPos(new Point2D(Math.random()*Main.WIDTH, Math.random()*Main.HEIGHT)); 
		this.setVel(new Point2D(0, 0));
		this.setAcc(new Point2D(0, 0));
		
		ObservableList<Double> list = this.getPoints();

		for (int i = 0; i < sides; i++) {
			list.add(this.pos.getX() + r * Math.cos(2 * i * Math.PI / sides)); 
			list.add(this.pos.getY() - r * Math.sin(2 * i * Math.PI / sides));
		}
	}
	
	public void steer() {
		
	}
	
	public void update() {
		this.setVel(this.getVel().add(this.getAcc()));
		
		if (this.getVel().magnitude() > MAXSPEED)
			this.setVel(this.getVel().normalize().multiply(MAXSPEED));
		
		this.setPos(this.getPos().add(this.getVel()));
		this.setAcc(this.getAcc().multiply(0));
	}
	
	public void applyForce(Point2D force) {
		this.setAcc(this.getAcc().add(force));
	}
	
	public void seek(Point2D target) {
		Point2D desired = target.subtract(this.getPos());
		desired.normalize().multiply(MAXSPEED);
		Point2D steer = desired.subtract(this.getVel());
		this.applyForce(steer);
	}
	
	public void display() {
		this.setTranslateX(this.pos.getX());
		this.setTranslateY(this.pos.getY());
		double angle = this.getPos().angle(this.getVel());
		this.setRotate(angle + 180);
	}
	
	public void print() {
		System.out.println("Speed: " + getVel().magnitude() +" - Acc: " + getAcc().magnitude());
	}
	
	
	
	
//	Getters & Setters

	public Point2D getPos() {
		return pos;
	}

	public void setPos(Point2D pos) {
		this.pos = pos;
	}

	public Point2D getVel() {
		return vel;
	}

	public void setVel(Point2D vel) {
		this.vel = vel;
	}

	public Point2D getAcc() {
		return acc;
	}

	public void setAcc(Point2D acc) {
		this.acc = acc;
	}
	
	
}
