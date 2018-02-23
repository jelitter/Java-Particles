package model;

public class Vector {
    private double x,y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public static Vector sub(Vector v1, Vector v2) {
        return new Vector(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }

    public Vector sub(Vector other) {
    	this.setX(this.getX() - other.getX());
    	this.setY(this.getY() - other.getY());
    	return this;
    }
    
    public static Vector add(Vector v1, Vector v2) {
        return new Vector(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }
    
    public Vector add(Vector other) {
    	this.setX(this.getX() + other.getX());
    	this.setY(this.getY() + other.getY());
    	return this;
    }
    
    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public void touchObject(){
        this.x = -x * 0.3;
        this.y = -y * 0.3;
    }
    
    public double magSq() {
        double x = this.x;
        double y = this.y;
        return (x*x + y*y);
    }
    
    public void setMag(double mag){
        double old_mag = Math.sqrt((x*x + y*y));
        
        this.x = x * mag / old_mag;
        this.y = y * mag / old_mag;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public String toString(){
        return "("+x + ", " + y +")";
    }
    
}
