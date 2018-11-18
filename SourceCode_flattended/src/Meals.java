import java.awt.Color;
import java.awt.Graphics;

public class Meals extends Shape implements Runnable{
	
	boolean drawn, deliver, taken;
	Thread thread= new Thread(this);

	
	public Meals(int x, int y , Color c) {
		super(x, y, Constants.MealLen, Constants.MealLen);
		this.xPosition = x;
		this.yPosition = y;
		this.color = c;

	}
	
	
	public void drawMeals(Graphics g)
	{
		this.drawn = true;
		this.deliver = false;
		this.taken = true;
		
		g.setColor(this.color);
		g.fillRect(this.xPosition, this.yPosition, this.width, this.hight);
	}
	
	public void setDrawMeal(boolean drawn) {
		this.drawn = drawn;
		
		
	}
	
	public boolean isDrawnMeal() {
		return this.drawn;
	}
	
	public boolean isTaken() {
		return this.taken;
	}
	
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	
	
	public void setPositionMeal(int x , int y, Color c) {
		
		this.xPosition = x;
		this.yPosition = y;
		this.color = c;
	}
	public boolean isDeliver() {
		return this.deliver;
	}
	public void setDeliver(boolean deliver) {
		this.deliver = deliver;
	}
	
	
	public Thread  getTimerMeal() {
			return this.thread;
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
	
	
	public boolean isClicked(int xMouse, int yMouse) {
		if(xMouse <= this.xPosition + Constants.MealLen && xMouse >= this.xPosition && yMouse <= this.yPosition + Constants.MealLen && yMouse >= this.yPosition) 
			return true;
		else 
			return false;
	
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		doNothing(10000);
		setDrawMeal(true);
	}
	
	
}
