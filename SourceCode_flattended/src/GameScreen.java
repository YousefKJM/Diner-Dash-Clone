import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;


@SuppressWarnings("serial")
public class GameScreen  extends JFrame {
	
	private JPanel HUD;
	private CanvasPane cavans = new CanvasPane();
	private JButton pause;
	private static JLabel scorelbl;
	private JLabel timelbl;
	private Waitress waitress;
	private ChefTable chefTab;
	private Customers [] custs;
	private Meals [] meal;
    private Countdown countdown;
    private Timer timer;
    private int count , fond, numTable = 0;
    public static int scored , counterCusts = 0;
    private Scanner fi;
	private Players [] slist;
    private EnterCustomers threadEnter = new EnterCustomers();
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    
	
	public GameScreen(Countdown countdown) throws  IOException {
		
        this.countdown = countdown;
		setSize(Constants.windowWidth, Constants.windowHight);
        setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(false);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		getContentPane().setBackground(Color.WHITE);
		setUndecorated(true);
		
		
		// Method to initialize the Drawings 
		initializeVariables();
		threadEnter.start();		
		
		
		

		
		try {
			fi = new Scanner(new FileInputStream("PlayersData.txt"));
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.exit(1);
		}	
		while(fi.hasNextLine()){
		count++;
		fi.nextLine();
		}
		slist = new Players [count];
		    
		FileReader fr=new FileReader("PlayersData.txt");
		    BufferedReader br=new BufferedReader(fr);
		    String line="";
		    String[] arrs=null;
		    int num=0;
		    while ((line=br.readLine())!=null) {
		        arrs=line.split("	");
		        slist[num] = new Players ( arrs[0], Integer.valueOf(arrs[1]));
		        num++;
		    }		    
		    br.close();
		    fr.close();
		
		// HUP >>  Head-Up Display Panel 
		HUD = new JPanel(new BorderLayout());
		pause = new JButton("Pause"); 
		pause.setFont(new Font("Arial", Font.PLAIN, 20));
		pause.setBackground(Color.WHITE);
		pause.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
            	timer.stop();
            	threadEnter.suspend();
            	
    			Object[] options = {"Resume", "Exit"};
    			int n = JOptionPane.showOptionDialog(null, "What would you like to do?", null,
    					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options,  options[0]); 
    			 switch (n) {
    	            case JOptionPane.YES_OPTION:
    	            	timer.start();
    	            	threadEnter.resume();

    	            break;
    	            case JOptionPane.NO_OPTION:    	            	
    	          	 Object[] options1 = {"Yes", "No"};
    	              			int n1 = JOptionPane.showOptionDialog(null, "You will lost Your Score, Are you Sure?", null,
    	              					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options1,  options1[0]); 
    	              			 switch (n1) {
    	              	            case JOptionPane.YES_OPTION:
    	              	            	
    	              	                setVisible(false);
      	            	            	new MainMenu().setVisible(true);
    	              	                  
    	              	           
    	              	            break;
    	              	            case JOptionPane.NO_OPTION:
    	              	     
    	              	            	timer.start();
    	            	            	threadEnter.resume();

    	              	            	
    	              	            break;
    	                     	}
    	                  break;
    	        }
            }
		});
		
		
		scorelbl = new JLabel("Score: 000", SwingConstants.CENTER);
		scorelbl.setFont(new Font("Arial", Font.PLAIN, 20));
		
		timelbl = new JLabel("Time: 10:00");
		timelbl.setFont(new Font("Arial", Font.PLAIN, 20));
		
		HUD.add(pause, BorderLayout.LINE_START);
		HUD.add(scorelbl);
		HUD.add(timelbl, BorderLayout.LINE_END);
		
		HUD.setBorder(BorderFactory.createLineBorder(Color.black));
		HUD.setPreferredSize(new Dimension(Constants.windowWidth, 40));

		add(HUD, BorderLayout.PAGE_START);

		add(cavans);
		
		startTimer();
		
		setVisible(true);
	}
	
	public static void updatePlayer(Players[] player){
		PrintWriter out = null;
		try
		{ out = new PrintWriter(new FileOutputStream("PlayersData.txt")); }
		catch (FileNotFoundException e)
		{System.out.println(e);
		 System.exit(1);}
		for(int i = 0; i < player.length; i++)
		{ out.print(player[i].toString());
			if (i!=(player.length)) {
				out.println(""); }
		}
		out.close(); }
		
	private void initializeVariables() {
	
		Constants.table[0] = new Table(Constants.table0X,Constants.table0Y ,new Color(156,93,82) );
		Constants.table[1] = new Table(Constants.table1X,Constants.table1Y,new Color(156,93,82));
		Constants.table[2] = new Table(Constants.table2X,Constants.table2Y, new Color(156,93,82) );
		Constants.table[3] = new Table(Constants.table3X,Constants.table3Y,new Color(156,93,82) );
		
		custs = new Customers[7];
		int yH = 0;
		for(int i =0; i < custs.length; i++){
			custs[i] = new Customers(Constants.windowWidth/15, (Constants.windowHight/6)+ 20 + yH ,(Constants.windowWidth/15)*2,Color.GREEN );
			yH +=70;
		}
		
		meal = new Meals[4];
		for(int i = 0; i < meal.length; i++) {
			meal[i] = new Meals(0,0,Color.WHITE);
			meal[i].setTaken(true);
		}
		chefTab = new ChefTable(Constants.KitchX, Constants.KitchY);
		
		waitress = new Waitress((Constants.windowWidth/2)+20, (Constants.windowHight/2)+20);
	} 
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new GameScreen(new Countdown("10:00"));
	}
	
	class CanvasPane extends JPanel {
		public CanvasPane() {
            setPreferredSize(new Dimension(Constants.panelW,Constants.panelH));
			setLocation(Constants.panelX,Constants.panelY);
			setBackground(Color.WHITE);
			
			addMouseListener( new MouseAdapter()  {   
			@SuppressWarnings("deprecation")
			@Override
	            public void mousePressed(MouseEvent e) {
				
				//Costumers clicked 	
	            for(int i = 0; i < custs.length; i++) 
	                if(custs[i].isClicked(e.getX(), e.getY())) {
	                	if(!custs[i].isSelected()) {
		                	custs[i].setSelected(true);
	                		for(int j = 0; j< custs.length; j++)
	                			if(j != i)
	                				custs[j].setSelected(false);
	                		waitress.setSelected(false);
		                	repaint();
	                	} else {
	                		custs[i].setSelected(false);
	                		repaint();
	                	}
	            	}
	                
	             //waitress clicked
	             if(waitress.isClicked(e.getX(), e.getY())) {
	                		if(!waitress.isSelected()) {
		                		waitress.setSelected(true);
		                		for(int j = 0; j < custs.length; j++)
		                			custs[j].setSelected(false);
	                		repaint();
	                	} else {
	                		waitress.setSelected(false);
	                		repaint();
	                	}
	                }
	                
	               // table clicked
	                for(int i = 0; i < Constants.table.length; i++)   {
	                if(Constants.table[i].isClicked(e.getX(), e.getY())) {
	                	if(waitress.isSelected()) {
	                		waitress.setSelected(false);
	                		waitress.setPositionCircle(Constants.table[i].xPosition + 25, Constants.table[i].yPosition -60);
	                	  if(!(Constants.table[i].getColor().equals(new Color(156,93,82))) && waitress.getColor().equals(Color.GRAY) && meal[i].isTaken()) {
	                			waitress.setColor(Constants.table[i].getColor());
	                			meal[i].setTaken(false);
	                		}
	                		numTable = i;
	                		repaint();
	                	} 
	                	for(int j = 0; j < custs.length; j++)  {
	                	if(custs[j].isSelected() && Constants.table[i].isServiceAve() && custs[j].isCurrCust()) {
	                		custs[j].setPositionCircle(Constants.table[i].xPosition -60, Constants.table[i].yPosition + 30, Constants.table[i].xPosition+110);
	                		custs[j].setTabNum(i);
	                		custs[j].setSelected(false);
	                		custs[j].setCurrCust(false);
	                		custs[j].setColor(Color.GREEN);
	                		
	                		Constants.table[i].setServiceAve(false);
	                		Constants.table[i].setCustNum(j);
	                		
	                		custs[j].getStatusOnCarpet().stop();
	                        executor.submit(Constants.table[i].getStatusWhileOrder());
	                		custs[j].getStatusAfterOrder().start();
	                		
	                		repaint();
	                	}
	               	}
	               if(waitress.getColor().equals(meal[i].getColor()) && meal[i].isDeliver() && custs[Constants.table[i].getCustNum()].isDrawn()) {
	                		waitress.setColor(Color.GRAY);
	                	
	                		custs[Constants.table[i].getCustNum()].getStatusAfterOrder().stop();
	                		custs[Constants.table[i].getCustNum()].getStatusWhileEat().start();
	                		custs[Constants.table[i].getCustNum()].setColor(Color.GREEN);
	                		Constants.table[i].setColor(new Color(156,93,82));
	                		Constants.table[i].setServiceAve(true);
	                		meal[i].setTaken(true);
	                	}
                		meal[i].setDeliver(false);
	                }
	                if(!custs[Constants.table[i].getCustNum()].isDrawn() && !(Constants.table[i].getColor().equals(new Color(156,93,82))) && meal[i].isDrawnMeal()) {
//	                	
                		waitress.setColor(Color.GRAY);
                		custs[Constants.table[i].getCustNum()].getStatusAfterOrder().stop();
                		meal[i].setDrawMeal(false);
                		meal[i].setDeliver(true);
                		Constants.table[i].setColor(new Color(156,93,82));
                		Constants.table[i].setServiceAve(true);
	                	meal[i].setTaken(true);

                	}
                	else if(!custs[Constants.table[i].getCustNum()].isDrawn() && !(Constants.table[i].getColor().equals(new Color(156,93,82))) && !meal[i].isDrawnMeal()) {
                	
                		waitress.setColor(Color.GRAY);
                		custs[Constants.table[i].getCustNum()].getStatusAfterOrder().stop();
                		meal[i].setDrawMeal(false);
                		meal[i].setDeliver(true);
                		Constants.table[i].setColor(new Color(156,93,82));
                		Constants.table[i].setServiceAve(true);
                	}
	           } // end of table clicked
	                
	           //chef table clicked
	          if(chefTab.isClicked(e.getX(), e.getY()))  {
	        	  if(waitress.isSelected() && !(waitress.getColor().equals(Color.GRAY))) {
	        		  waitress.setPositionCircle(Constants.KitchX + (Constants.KitchW / 2) -25, Constants.KitchH +15);
	        		  waitress.setSelected(false);
	           
	                if(!(Constants.table[0].getColor().equals(new Color(156,93,82))) && numTable == 0) {
	                	meal[0] = new Meals(Constants.MealX, Constants.MealY, Constants.table[0].getColor());
	                	meal[0].getTimerMeal().start();
	                	}
	                else if(!(Constants.table[1].getColor().equals(new Color(156,93,82))) && numTable == 1) {
	                	meal[1] = new Meals(Constants.MealX + 90, Constants.MealY, Constants.table[1].getColor());
	                	meal[1].getTimerMeal().start();
	                	}
	                else if(!(Constants.table[2].getColor().equals(new Color(156,93,82))) && numTable == 2) {
	                	meal[2] = new Meals(Constants.MealX + 180, Constants.MealY, Constants.table[2].getColor());
	                	meal[2].getTimerMeal().start();
	                	}
	                else if(!(Constants.table[3].getColor().equals(new Color(156,93,82))) && numTable == 3) {
	                	meal[3] = new Meals(Constants.MealX + 270, Constants.MealY, Constants.table[3].getColor());
	                	meal[3].getTimerMeal().start();
	                	}
	                waitress.setColor(Color.GRAY);
	                repaint();
	                }
	           }
	                
	          // meals clicked
	          for(int i = 0; i < meal.length; i++)
	    	  if(meal[i].isClicked(e.getX(), e.getY()) && meal[i].isDrawnMeal() && waitress.isSelected())  {
	    	     waitress.setSelected(false);
	    	     waitress.setColor(meal[i].getColor());	
	    	     waitress.setPositionCircle(Constants.KitchX + (Constants.KitchW / 2) -25, Constants.KitchH +15);
	    	     meal[i].setDrawMeal(false);
	    	     meal[i].setDeliver(true);
	    	     repaint();
	    	    }
	          
	          
	          // check if All customers are finished
	          if(counterCusts == 7) {
					timer.stop();
				    threadEnter.stop();
				    Object[] options = { "Ok", "Cancel" };
				    JPanel panel = new JPanel();
				    panel.add(new JLabel("Please Enter Your Name"));
				    JTextField findname = new JTextField(10);
				    panel.add(findname);
				    int result = JOptionPane.showOptionDialog(null, panel, "Search or Add",
			    	        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
			    	        options, null);
			       if (result == JOptionPane.YES_OPTION) {
			    	 
			       Players [] player = slist;
			       int a = -1;
			       for(int i = 0 ;i < slist.length;i++) {
			    	if(slist[i].getName().equals(findname.getText())) {
			    		a = i;
			    		fond = 1;
			         }
			       }
			           if(!findname.getText().equals("") && fond == 1) {
			               player[a].updScore(scored);
			               updatePlayer(player);
			                 
			               setVisible(false);
						   new MainMenu().setVisible(true);
			         	} 
			           else if (!findname.getText().equals("") &&fond != 1) {
			            Object[] options1 = {"Yes", "No"};
			            int n1 = JOptionPane.showOptionDialog(null, "There Is No Such Name In Our DataBase,Do You Want To Add This As A New Name?", null,
			          	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options1,  options1[0]); 
			            switch (n1) {
			            case JOptionPane.YES_OPTION:	
			              try{
			              	  FileWriter fw = new FileWriter("PlayersData.txt", true);
			              	  BufferedWriter bw = new BufferedWriter(fw);
			              	  bw.write(findname.getText()+"	"+scored+ "\n");
			              	  bw.close();
			              	            
			                 setVisible(false);
				             new MainMenu().setVisible(true);
			              	 } catch (IOException e1) {
			              	       System.out.println(e1);
			              	 }
			            break;
			              	            
			          	case JOptionPane.NO_OPTION:
			              	 setVisible(false);
			            	 new MainMenu().setVisible(true);	
			            break;
			              }
			            }
			       /*    else if(findname.getText().equals(""))  {
				    		 JOptionPane.showMessageDialog(null,"You have entered Nothing! Please, Enter your Name... ");
				    		 
				    	 } */
			       }
			       else {
			       setVisible(false);
	    	       new MainMenu().setVisible(true);
			       }
			       counterCusts = 0;
			   } //end of if-else CustsCounter
			} // end of mousePressed Listener 	
		}); // end of Mouse Listener
	} // end of CanvasPane Class Constructor
			
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// Chef Table
			chefTab.drawChefTable(g);
			
			// carpet 
			g.setColor(new Color(255,204,153));
			g.fillRect(Constants.carpetX, Constants.carpetY, Constants.carpetW, Constants.carpetH);
					
			//Reception 
			g.setColor(new Color(0,100,0));
			g.fillRect(Constants.ReceptX, Constants.ReceptY, Constants.ReceptW, Constants.ReceptH);
			
			// Tables
			Constants.table[0].drawTable(g);
			Constants.table[1].drawTable(g);
			Constants.table[2].drawTable(g);
			Constants.table[3].drawTable(g);
			
			// Meals
			for(int i = 0; i < meal.length; i ++)
				if(meal[i].isDrawnMeal()) 
					meal[i].drawMeals(g);
			
			// Customers
			for(int i = 0; i< custs.length; i++)
			if(custs[i].isDrawn() ) 
				custs[i].drawCustomers(g);
			
			// Waitress
			waitress.drawWaitress(g);
			
		} // end of paintComponent Method
	} // end of CanvasPane Inner Class
	
	private class EnterCustomers extends Thread {
		  Random r = new Random();
	        public void run() {
	          for(int i = 0; i< custs.length; i++)  {
	        	  if(!custs[i].isDrawn()) {
					doNothing(r.nextInt(20000));
					repaint();
					custs[i].setDrawn(true);
	        	  }
	          	  if(custs[i].isDrawn()) {
	          		 custs[i].getStatusOnCarpet().start();
	          	  }
	          }
	       } 
	   } // end of EnterCustomers Thread Class
	  
	  public static void redStatus() {
		  scored -= 100;
		  scorelbl.setText("Score: "+scored);
		  counterCusts++;
	  }
	  
	  public static void winStatus() {
		  scored += 150;
		  scorelbl.setText("Score: "+scored);
		  counterCusts++;
	  }
	   
	   
	  public static void doNothing(int milliseconds)
      {
          try {
              Thread.sleep(milliseconds);
          } catch(InterruptedException e) {
              System.out.println("Unexpected interrupt");
              System.exit(0);
          }
      }
	  
	  
	private void startTimer() {
	        timelbl.setText( countdown.toReadableString() ); // extract method
	        timer = new Timer( 1000, new TimerListener() );
	        timer.setRepeats( true );
	        timer.start();   
	 }
	
	 
	 private class TimerListener implements ActionListener  {
	        public void actionPerformed( ActionEvent e ) {
	        	countdown.decrement(); // separate layout from logic
	            timelbl.setForeground( countdown.isCloseToEnd() ? Color.RED : Color.BLACK ); // extract conditions
	            if( countdown.isFinished() ) {  // extract conditions	       
	                timelbl.setText("Time's up");
	                timer.stop();
	                Object[] options = { "OK", "CANCEL" };
				    JPanel panel = new JPanel();
				    panel.add(new JLabel("Please Enter Your Name"));
				    JTextField findname = new JTextField(10);
				    panel.add(findname);
			       int result = JOptionPane.showOptionDialog(null, panel, "Search or Add",
			    	        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
			    	        options, null);
			       if (result == JOptionPane.YES_OPTION) {
			       Players [] player = slist;
			       int a = -1;
			       for(int i = 0 ;i < slist.length;i++) {
			    	if(slist[i].getName().equals(findname.getText())) {
			    		a = i;
			    		fond = 1;
			         }
			       }
			           if(fond == 1) {
			               player[a].updScore(scored);
			               updatePlayer(player);
			                 
			               setVisible(false);
						   new MainMenu().setVisible(true);
			         	} 
			           else if (fond != 1) {
			            Object[] options1 = {"Yes", "No"};
			            int n1 = JOptionPane.showOptionDialog(null, "There Is No Such Name In Our DataBase,Do You Want To Add This As A New Name?", null,
			          	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options1,  options1[0]); 
			            switch (n1) {
			            case JOptionPane.YES_OPTION:	
			              try{
			              	  FileWriter fw = new FileWriter("PlayersData.txt", true);
			              	  BufferedWriter bw = new BufferedWriter(fw);
			              	  bw.write(findname.getText()+"	"+scored+ "\n");
			              	  bw.close();
			              	            
			                 setVisible(false);
				             new MainMenu().setVisible(true);
			              	 } catch (IOException e1) {
			              	       System.out.println(e1);
			              	 }
			            break;
			              	            
			          	case JOptionPane.NO_OPTION:
			              	 setVisible(false);
			            	 new MainMenu().setVisible(true);	
			            break;
			              }
			            }
			       }
			       else {
			       setVisible(false);
	    	       new MainMenu().setVisible(true);
			       }
			       counterCusts = 0;
	           }
	           else {
	             timelbl.setText("Time:"+countdown.toReadableString() );
	           }
	        }	
	    } // end of TimerListener Inner Class

} // end of GameScreen class