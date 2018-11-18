import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainMenu extends JFrame implements ActionListener {
	
	JLabel nameOfGame;
	JButton playButton ,LeaderButton , exitButton;
	
public MainMenu() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Constants.windowWidth, Constants.windowHight);
        setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		getContentPane().setBackground(Color.WHITE);
		setUndecorated(true);
		
		
        
		nameOfGame = new JLabel("Cook Block" ,SwingConstants.CENTER);
		nameOfGame.setFont(new Font("SansSerif", Font.BOLD, 70));
		nameOfGame.setBounds(0, 80, 1240, 100);
		add(nameOfGame);
		
		playButton = new JButton("Play");
		playButton.setFont(new Font("SansSerif", Font.BOLD, 40));
		playButton.setBounds(380, 250, 500, 100);
		playButton.addActionListener(this);
		add(playButton);
		
		LeaderButton = new JButton("Leaderboaed");
		LeaderButton.setFont(new Font("SansSerif", Font.BOLD, 40));
		LeaderButton.setBounds(380, 370, 500, 100);
		LeaderButton.addActionListener(this);
		add(LeaderButton);
		
		exitButton = new JButton("Exit");
		exitButton.setFont(new Font("SansSerif", Font.BOLD, 40));
		exitButton.setBounds(380, 490, 500, 100);
		exitButton.addActionListener(this);
		add(exitButton);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainMenu();
	}
	
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == playButton) {
			 this.setVisible(false);
		     try {
				new GameScreen(new Countdown("10:00")).setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		else if(arg0.getSource() == LeaderButton) {
			
			this.setVisible(false);
		     new Leaderboard().setVisible(true); 
		}
		
		else if(arg0.getSource() == exitButton) {
			System.exit(0);
		}
	}
} // end of MainMenu Class
