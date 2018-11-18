import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Waitress extends Shape{
	
	boolean selected;
	
	public Waitress(int x, int y) {
		super(x, y, Constants.circleRad, Constants.circleRad);
		this.xPosition = x;
		this.yPosition = y;
		this.color = Color.GRAY;
		this.selected = false;
	}
	
	public void drawWaitress(Graphics g)
	{
        Graphics2D g2d = (Graphics2D) g;

       
        if(this.selected) {
        	g2d.setPaint(Color.BLACK);
        	g2d.setStroke(new BasicStroke(5));
        }
        if(!this.selected) {
        	g2d.setPaint(Color.BLACK);
        	g2d.setStroke(new BasicStroke(0));
        }
        
       
        
        g2d.setColor(this.color);
        g2d.fillOval(this.xPosition, this.yPosition, this.width, this.hight);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(this.xPosition, this.yPosition, this.width, this.hight);
        	
	}
		
	public void setPositionCircle(int x , int y) {
		this.xPosition = x;
		this.yPosition = y;
	}
	public boolean isClicked(int xMouse, int yMouse) {
		if(xMouse <= this.xPosition + Constants.circleRad && xMouse >= this.xPosition && yMouse <= this.yPosition + Constants.circleRad && yMouse >= this.yPosition) 
			return true;
		else 
			return false;
	
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isSelected() {
		return this.selected;
	}
	





}


	