package model;

import javafx.geometry.Point2D;

public class MyVector extends Point2D {

	public MyVector(double x, double y) {
		super(x, y);
	}
	
	public MyVector limit(double mag) {
		if (this.magnitude() > mag) {
			this.normalize().multiply(mag);
		}
		return this;
	}
	
	public MyVector setMag(double mag) {
		Point2D ret = super.normalize().multiply(mag);
		return new MyVector(ret.getX(), ret.getY()); 
	}

	public MyVector subtract(MyVector vec) {
		Point2D ret = super.subtract(vec); 
		return new MyVector(ret.getX(), ret.getY()); 
		
	}
}
