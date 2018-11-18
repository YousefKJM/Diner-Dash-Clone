import java.awt.Color;

public class Shape {
	int xPosition, yPosition;
	int width, hight;
	Color color;
	int inscoretap  = 0;
	int counterCusts = 0;
	
	public Shape(int x, int y, int width, int hight)
	{
		this.xPosition = x;
		this.yPosition = y;
		this.width = width;
		this.hight = hight;
	}
	
	public void setColor(Color c)
	{
		this.color = c;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setPosition(int x, int y)
	{
		this.xPosition = x;
		this.yPosition = y;
	}
	
	public int getScoreIn() {	
	      return inscoretap;
	}
	
	public int getCounterCusts() { 
		return counterCusts;
		
	}
}