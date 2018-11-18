import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;


@SuppressWarnings("serial")
public class Leaderboard extends JFrame{
	
	
	//private JPanel contents;
	private JLabel leaderlbl, sortlbl;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPan;
	private JButton sort, back;
	private JRadioButton nameButton, scoreButton, AsceButton, DescButton;
	private ButtonGroup bgroup, bgroup1;
	private ArrayList<Players> players;
	
	
	public Leaderboard() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Constants.windowWidth, Constants.windowHight);
        setLocationRelativeTo(null);
		setLayout(null);
		setUndecorated(true);
		setResizable(false);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		getContentPane().setBackground(Color.WHITE);
		
		
		// Leaderboard Label ---------------------------------------
		leaderlbl = new JLabel("Leaderboard" ,SwingConstants.CENTER);
		leaderlbl.setFont(new Font("SansSerif", Font.BOLD, 70));
		leaderlbl.setBounds(0, 10, 1240, 100);
		add(leaderlbl);
        //--------------------------------------------------------

		
		
		// Sort By Label ---------------------------------------
		sortlbl = new JLabel("Sort By: " ,SwingConstants.LEFT);
		sortlbl.setFont(new Font("Arial", Font.PLAIN, 30));
		sortlbl.setBounds(50, 140, 300, 100);
		add(sortlbl);
        //--------------------------------------------------------



        //Button Radio Group 1 ---------------------------------
		 nameButton = new JRadioButton("Names", true);
		 nameButton.setFont(new Font("Arial", Font.PLAIN, 40));
		 nameButton.setBounds(60, 230, 200, 50);
		 nameButton.setBackground(Color.WHITE);
		 add(nameButton);
		 
		 
         scoreButton  = new JRadioButton("Scores");
         scoreButton.setFont(new Font("Arial", Font.PLAIN, 40));
         scoreButton.setBounds(60, 290, 200, 50);
         scoreButton.setBackground(Color.WHITE);
		 add(scoreButton);
        
         bgroup = new ButtonGroup();
         bgroup.add(nameButton);
         bgroup.add(scoreButton);
         //--------------------------------------------------------

         
         // Draw Line ----------------------------------------------
 		JPanel panel = new JPanel() {
             @Override
             public void paintComponent(Graphics g) {
                 super.paintComponent(g);
                 g.setColor(Color.BLACK);
                 g.drawLine(0, 0, 300, 0);
             }
         };
         panel.setBounds(60, 370, 260, 1);
         add(panel);
         //--------------------------------------------------------
         
         
        //Button Radio Group 2 ---------------------------------
         AsceButton  = new JRadioButton("Ascending", true);
         AsceButton.setFont(new Font("Arial", Font.PLAIN, 40));
         AsceButton.setBounds(60, 390, 250, 50);
         AsceButton.setBackground(Color.WHITE);
		 add(AsceButton);
         
         DescButton  = new JRadioButton("Desending");
         DescButton.setFont(new Font("Arial", Font.PLAIN, 40));
         DescButton.setBounds(60, 450, 250, 50);
         DescButton.setBackground(Color.WHITE);
		 add(DescButton);
         
         bgroup1 = new ButtonGroup();
         bgroup1.add(AsceButton);
         bgroup1.add(DescButton);
         //--------------------------------------------------------

         
         // Sort Button -------------------------------------------
         sort = new JButton("Sort");
	     sort.setFont(new Font("Arial", Font.PLAIN, 30));
	     sort.setBounds(60, 550, 250, 50);
	     sort.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	            	if(nameButton.isSelected() && AsceButton.isSelected()){
	         		 Collections.sort(players, new Comparator<Players>() {
				    		    public int compare(Players v1, Players v2) {
				    		        return v2.name.compareTo(v1.name);  // Ascending Names
				    		    }
				    		});    
	         		   resetTable();
	         		  addRowToJTable();  
	               }
	            	if(nameButton.isSelected() && DescButton.isSelected()){
		         		 Collections.sort(players, new Comparator<Players>() {
					    		    public int compare(Players v1, Players v2) {
					    		        return v1.name.compareTo(v2.name);  // Descending Names
					    		    }
					    		});    
		         		   resetTable();
		         		  addRowToJTable();  
		            }
	            	if(scoreButton.isSelected() && AsceButton.isSelected()){
	            		 Collections.sort(players, new Comparator<Players>() {
	             	        public int compare(Players p1, Players p2) {
	             	            return p1.score - p2.score; // Ascending Scores
	             	        }
	             	    }); 	 
		         		   resetTable();
		         		  addRowToJTable();  
		            }
	            	if(scoreButton.isSelected() && DescButton.isSelected()){
	            		 Collections.sort(players, new Comparator<Players>() {
	             	        public int compare(Players p1, Players p2) {
	             	            return p2.score - p1.score; // Descending Scores
	             	        }
	             	    }); 	 
		         		   resetTable();
		         		  addRowToJTable();  
		            }
	            }
	        });
	     add(sort);
         //--------------------------------------------------------
	     

        // Back Button -------------------------------------------   
	     back = new JButton("Back");
	     back.setFont(new Font("Arial", Font.PLAIN, 30));
	     back.setBounds(60, 610, 250, 50);
	     back.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	            	setVisible(false);
	  		     new MainMenu().setVisible(true); 
	            }
	        });
	     	add(back);
         //--------------------------------------------------------

       
	     initComponents();
	        
	   // To Add the data from file to the ArrayList 
			players = new ArrayList<Players>();
	        Scanner fi2 = null;
	    	try {
	    		fi2 = new Scanner(new FileInputStream("PlayersData.txt"));
	    	} catch (FileNotFoundException e) {
	    		System.out.println(e);
	    		System.exit(1);
	    	}   
	        while(fi2.hasNext()) {       
	        	String name = fi2.next();
	        	int score = fi2.nextInt(); 

	        	Players data = new Players(name, score);
	        	players.add(data);
	        }	    
	     addRowToJTable();	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Leaderboard();
	}
	
	// Added rows from ArrayList to JTable ----------------------------
    public void addRowToJTable() {
    	model = (DefaultTableModel) table.getModel();
        Object rowData[] = new Object[2];
        for(int i = 0; i < players.size(); i++) {
            rowData[0] = players.get(i).name;
            rowData[1] = players.get(i).score;
            model.addRow(rowData);
        }         
    }
    
	// Initialize JTable and its Components ----------------------------
    private void initComponents() {
        table = new JTable();
        table.setRowHeight(60);
	    table.setEnabled(false);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 30));
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));
        table.setFont(new Font("SansSerif", Font.PLAIN, 20));
        table.setModel(new DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Name", "Score"
            }
        ));

        // ScrollPan 
		scrollPan = new JScrollPane(table);
    	scrollPan.getViewport().setBackground(Color.WHITE);
    	scrollPan.setBounds(400, 120, 800, 550);	
    	
    	add(scrollPan);
		setVisible(true);		
	}


 
	void resetTable() {
	  model.setRowCount(0);
	}
	
	// this class related to the header of JTable
	private static class HeaderRenderer implements TableCellRenderer {
	    DefaultTableCellRenderer renderer;
	    public HeaderRenderer(JTable table) {
	        renderer = (DefaultTableCellRenderer)
	            table.getTableHeader().getDefaultRenderer();
	        renderer.setHorizontalAlignment(JLabel.CENTER);
	    }
	    @Override
	    public Component getTableCellRendererComponent(
	        JTable table, Object value, boolean isSelected,
	        boolean hasFocus, int row, int col) {
	        return renderer.getTableCellRendererComponent(
	            table, value, isSelected, hasFocus, row, col);
	    }
	}
} // end of Leaderboard Class
