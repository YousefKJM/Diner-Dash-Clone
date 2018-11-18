import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Customers extends Shape implements Runnable {
	
	int xx;
	int i;
	boolean selected, currCust;
	boolean drawn ;
	Thread thread = new Thread(this);
	Thread1 thread1 = new Thread1();
	Thread2 thread2 = new Thread2();

	public Customers(int x, int y,int xx, Color c) {
		super(x, y, Constants.circleRad, Constants.circleRad);
		this.xx = xx;
		this.color = c;
		this.selected = false;
		this.currCust = true;		
		
	}
	
	
	public void setPositionCircle(int x , int y, int xx) {
		this.xPosition = x;
		this.yPosition = y;
		this.xx = xx;
	//	this.color = c;
	}
	
	
	public void drawCustomers(Graphics g)
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
        
        
		this.drawn = true;
		

        g2d.setColor(this.color);
        g2d.fillOval(this.xPosition, this.yPosition, this.width, this.hight);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(this.xPosition, this.yPosition, this.width, this.hight);
		
        g2d.setColor(this.color);
        g2d.fillOval(this.xx, this.yPosition, this.width, this.hight);
        g2d.setColor(Color.BLACK);
		g2d.drawOval(this.xx, this.yPosition, this.width, this.hight);
		
		
		
        
	}
	
	public boolean isClicked(int xMouse, int yMouse) {
		if(xMouse <= this.xPosition + Constants.circleRad && xMouse >= this.xPosition && yMouse <= this.yPosition + Constants.circleRad && yMouse >= this.yPosition) 
			return true;
		else if(xMouse <= this.xx + Constants.circleRad && xMouse >= this.xx && yMouse <= this.yPosition + Constants.circleRad && yMouse >= this.yPosition) {
			return true;
		}
		else 
			return false;
	
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isSelected() {
		return this.selected;
	}
	
	public void setCurrCust(boolean currCust) {
		this.currCust = currCust;
	}
	public boolean isCurrCust() {
		return this.currCust;
	}
	
	public boolean isDrawn() {
		return this.drawn;
	}
	
	public void setDrawn(boolean drawn) {
		this.drawn = drawn;
	}
	
	
	
	
	
	public void setTabNum(int i) {
		this.i = i;
	}
	
	public Thread  getStatusOnCarpet() {
			return thread ;
	    }
	public Thread getStatusAfterOrder() {
		
		return 	thread1;
	}
	
	public Thread getStatusWhileEat() {
		return thread2;
		
	}
	
	
		
	
		
		 


	
		public void run() {
		
			doNothing(25000);
			setColor(Color.YELLOW);
		    doNothing(10000);
		    setColor(Color.RED);
		    doNothing(5000);
		    setDrawn(false);
		    GameScreen.redStatus();
			
		}
		
		private class Thread1 extends Thread {
			 
		        public void run() {		        
		        doNothing(30000);
			    setColor(Color.YELLOW);
		        doNothing(10000);
			    setColor(Color.RED);
		        doNothing(5000);
		        setDrawn(false);
			    GameScreen.redStatus();

		        } 
		   }
		
		private class Thread2 extends Thread {
			 
	        public void run() {
	        doNothing(10000);
	        setDrawn(false);
		    GameScreen.winStatus();

	        } 
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

		
	

	
	

}