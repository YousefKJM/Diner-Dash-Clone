import java.awt.Color;
import java.awt.Graphics;


public class Table extends Shape implements Runnable {
	
	boolean selected, service;
	int i;
	Thread thread4 = new Thread(this);
	Color mealTabColor [] = {Color.RED, Color.YELLOW, Color.BLUE, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.CYAN};


	public Table(int x, int y , Color c) {
		super(x, y, Constants.tableLen, Constants.tableLen);
		this.xPosition = x;
		this.yPosition = y;
		this.color = c;
		this.selected = false;
		this.service = true;
		
	}
	
	
	
	public void drawTable(Graphics g)
	{
		g.setColor(this.color);
		g.fillRect(this.xPosition, this.yPosition, this.width, this.hight);
		g.setColor(Color.BLACK);
		g.drawRect(this.xPosition, this.yPosition, this.width, this.hight);	
	}
	
	
	
	
	public boolean isClicked(int xMouse, int yMouse) {
		if(xMouse <= this.xPosition + Constants.tableLen && xMouse >= this.xPosition && yMouse <= this.yPosition + Constants.tableLen && yMouse >= this.yPosition) 
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
	
	public void setServiceAve(boolean service) {
		this.service = service;
	}
	public boolean isServiceAve() {
		return this.service;
	}
	
	public void setCustNum(int i) {
		this.i = i;
	}
	public int getCustNum() {
		return this.i;
	}
	public Thread getStatusWhileOrder() {
		
		return 	thread4;
	}
	
	
	
	
	
	public static void doNothing(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e)
        {
            System.out.println("Unexpected interrupt");
            System.exit(0);
        }
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		doNothing(5000);
		setColor(mealTabColor[getCustNum()]);
		
	}

}
