package model;

import application.Main;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Particle extends Polygon {

	//	public static int sides = 3;

	public static final double MAXVEL = 1;

	private double r, vitality, angle, rotation;
	private int sides;
	public MyVector pos, vel, acc;
	private Color col;
	private double dist;
	
	public Particle(double x, double y) {
		this();
		this.pos = new MyVector(x, y);
	}


	public Particle() {

		this.r = 30 + Math.random() * 20;
		
		this.vitality = 100;
		this.angle = Math.random() * 360;
		this.rotation = (-1 + Math.random() * 2) * (5 * Math.random());
		this.sides = (int) (3 + Math.floor(Math.random() * 5));

		this.pos = new MyVector(Math.random()*Main.WIDTH/4, Math.random()*Main.HEIGHT/4);
		this.vel = new MyVector(0,0);
		this.acc = new MyVector(0,0);
		this.col = Color.color(Math.random(), Math.random(), Math.random());
		this.setFill(this.col);
		this.setStroke(this.col.darker());
		this.setStrokeWidth(this.r/10);
//		this.setRotate(this.angle);
//		this.setOpacity(0.2 + Math.random());

		ObservableList<Double> list = this.getPoints();

		for (int i = 0; i < sides; i++) {
			list.add(this.pos.getX() + r * Math.cos(2 * i * Math.PI / sides)); 
			list.add(this.pos.getY() - r * Math.sin(2 * i * Math.PI / sides));
		}
	}

	public void rotate() {
		this.angle += this.rotation;
		this.setRotate(this.angle);
	}

	public void update() {
		this.vel = this.vel.add(this.acc);
		this.vel = this.vel.limit(Particle.MAXVEL);
		this.pos = this.pos.add(this.vel);
		this.acc = this.acc.setMag(0);

//		this.setTranslateX(this.getParent().getLayoutX() + this.pos.getX());
//		this.setTranslateY(this.getParent().getLayoutY() + this.pos.getY());
		this.setRotate(this.vel.angle(this.pos));
		
		this.setTranslateX(this.getParent().getLayoutX() + this.pos.getX());
		this.setTranslateY(this.getParent().getLayoutY() + this.pos.getY());
//		this.setTranslateX(this.getTranslateX() + this.pos.getX());
//		this.setTranslateY(this.getTranslateX() + this.pos.getY());
	}
	
//	public void update2() {
//		this.pos = this.pos.add(this.vel);
//		this.setTranslateX(this.pos.getX());
////		this.setTranslateY(this.pos.getY());
//	}
	
	public void applyForce(MyVector force) {
		this.acc = this.acc.add(force);
	}
	public void seek(MyVector target) {
		MyVector desired = target.subtract(this.pos).setMag(Particle.MAXVEL);
		this.dist = this.pos.distance(target); 
		
		MyVector steer = desired.subtract(this.vel).limit(Particle.MAXVEL);
//		System.out.println("Desired:" + desired);
//		System.out.println("Steer:" + steer);
		this.applyForce(steer);
	}

	public void edges() {

		// TOP
		if (this.vel.getY() < 0 && this.pos.getY() < 0) {
			this.applyForce(new MyVector(0, Particle.MAXVEL));
		}

		// LEFT
		if (this.vel.getX() < 0 && this.pos.getX() < 0) {
			this.applyForce(new MyVector(Particle.MAXVEL, 0));
		}

		// RIGHT
//		if (this.vel.getX() > 0 && this.pos.getX() > Main.WIDTH) {
		if (this.vel.getX() > 0 && this.pos.getX() > ((Pane) this.getParent()).getWidth()) {
			this.applyForce(new MyVector(-Particle.MAXVEL, 0));
		}

		// BOTTOM
//		if (this.vel.getY() > 0 && this.pos.getY() > Main.HEIGHT) {
		if (this.vel.getY() > 0 && this.pos.getY() > ((Pane) this.getParent()).getHeight()) {
			this.applyForce(new MyVector(0, -Particle.MAXVEL));
		}
	}
	
	public void print() {
		System.out.println("Pos: " + this.pos);
		System.out.println("Vel: " + this.vel);
		System.out.println("Acc: " + this.acc);
		System.out.println("Dist: " + this.dist);
		System.out.println();
	}
	
}
