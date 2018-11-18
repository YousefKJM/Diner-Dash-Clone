import java.awt.Color;
import java.awt.Graphics;

public class ChefTable extends Shape{
	
	
	public ChefTable(int x, int y) {
		super(x, y, Constants.KitchW, Constants.KitchH);
		this.xPosition = x;
		this.yPosition = y;
		this.color = Color.WHITE;
		
	}
	
	
	
	public void drawChefTable(Graphics g)
	{
		g.setColor(this.color);
		g.fillRect(this.xPosition, this.yPosition, this.width, this.hight);
		g.setColor(Color.BLACK);
		g.drawRect(this.xPosition, this.yPosition, this.width, this.hight);	
	}
	public boolean isClicked(int xMouse, int yMouse) {
		if(xMouse <= this.xPosition + Constants.KitchW && xMouse >= this.xPosition && yMouse <= this.yPosition + Constants.KitchH && yMouse >= this.yPosition) 
			return true; 
		else 
			return false;
	
	}
}
