package testforclass;

abstract public class Shape {
	public abstract  void shape();
	public abstract void setColor();
//	public Shape(){
//		shape();
//		setColor();
//	}
	public  void draw(){
		shape();
		setColor();
	}
}
