package model;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Particle extends Polygon {

	//	public static int sides = 3;
	public static final double MAXVEL = 1.0;
	public static final double WIDTH = 1300.0;
	public static final double HEIGHT = 900.0;

	private double r, vitality, angle, rotation;
	private int sides;
	private MyVector pos, vel, acc;
	private Color col;


	public Particle() {

		this.r = 2 + Math.random() * 30;
		this.vitality = 100;
		this.angle = Math.random() * 360;
		this.rotation = (-1 + Math.random() * 2) * (5 * Math.random());
		this.sides = (int) (2 + Math.floor(Math.random() * 6));

		this.pos = new MyVector(Math.random()*WIDTH, Math.random()*HEIGHT);
		this.vel = new MyVector(0,0);
		this.acc = new MyVector(0,0);
//		this.vel = new MyVector(-1 + 2 * Math.random()/100, -1 + 2 * Math.random()/100);
//		this.acc = new MyVector(-1 + 2 * Math.random()/100, -1 + 2 * Math.random()/100);
		this.col = Color.color(Math.random(), Math.random(), Math.random());
		this.setFill(this.col);
		this.setRotate(this.angle);
		this.setOpacity(0.2 + Math.random());

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
//		this.vel.add(this.acc);
		this.vel.limit(Particle.MAXVEL);
		this.pos.add(this.vel);
		this.acc.setMag(0);

		this.setTranslateX(this.getTranslateX() + this.pos.getX());
		this.setTranslateY(this.getTranslateX() + this.pos.getY());
	}
	public void applyForce(MyVector force) {
		this.acc.add(force);
	}
	public void seek(MyVector target) {
		MyVector desired = target.subtract(this.pos).setMag(Particle.MAXVEL);
		MyVector steer = desired.subtract(this.vel).limit(Particle.MAXVEL);
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
		if (this.vel.getX() > 0 && this.pos.getX() > 1300) {
			this.applyForce(new MyVector(-Particle.MAXVEL, 0));
		}

		// BOTTOM
		if (this.vel.getY() > 0 && this.pos.getY() > 900) {
			this.applyForce(new MyVector(0, -Particle.MAXVEL));
		}
	}
	
	public void print() {
		System.out.println("Pos: " + this.pos);
		System.out.println("Vel: " + this.vel);
	}
	
}
