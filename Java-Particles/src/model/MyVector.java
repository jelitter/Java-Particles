package model;

import javafx.geometry.Point2D;

public class MyVector extends Point2D {

	public MyVector(double x, double y) {
		super(x, y);
	}
	
	public MyVector limit(double mag) {
		if (this.magnitude() > mag) {
			this.setMag(mag);
		}
		return this;
	}
	
	public MyVector setMag(double mag) {
//		MyVector vec = this.normalize().multiply(mag);
//		Point2D p = new Point2D(0,0);
		return this.normalize().multiply(mag);
		
	}

	public MyVector subtract(MyVector vec) {
		Point2D ret = super.subtract(vec); 
		return new MyVector(ret.getX(), ret.getY()); 
		
	}
	
	public MyVector add(MyVector vec) {
		Point2D ret = super.add(vec);
		return new MyVector(ret.getX(), ret.getY()); 
//		return new MyVector(this.getX() + vec.getX(), this.getY() + vec.getY()); 
	}
	
	public MyVector multiply(double mag) {
		Point2D ret = super.multiply(mag); 
		return new MyVector(ret.getX(), ret.getY()); 
		
	}
	public MyVector normalize() {
//		Point2D ret = super.normalize(); 
		return (this.magnitude() == 0) ? this : this.div(this.magnitude());  
		
	}
	
	private MyVector div(double magnitude) {
		return this.multiply(1/magnitude);
	}

	public String toString() {
		return "[" + this.getX() + ", " + this.getY() + "]";
	}
	public void print() {
		System.out.println(this);
	}
}
