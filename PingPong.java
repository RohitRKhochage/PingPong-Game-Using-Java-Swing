import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

class PingPong{
	
	private JFrame f;
	private JPanel background,left,right;
	private JTextField ForFocus;
	private JLabel ball,winner,starting;
	private ImageIcon image;
	private final Set<Integer> pressedKeys = new HashSet<>();
	boolean canStart = false, isFirst = true;
	
	Ball ballThread = new Ball();
	
	public PingPong(){
		f = new JFrame();
		f.setTitle("PingPong");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000,500);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setLayout(null);
		f.setVisible(true);
		
		background = new JPanel();
		background.setBackground(Color.cyan);
		background.setLayout(null);
		background.setBounds(0,0,1000,500);
		
		left = new JPanel();
		left.setBackground(Color.blue);
		left.setLayout(null);
		left.setBounds(4,4,10,84);
		background.add(left);
		f.add(background);
		
		right = new JPanel();
		right.setBackground(Color.blue);
		right.setLayout(null);
		right.setBounds(970,4,10,84);
		background.add(right);
		
		ForFocus = new JTextField();
		ForFocus.setBounds(0,0,0,0);
		ForFocus.setVisible(true);
		background.add(ForFocus);
		ForFocus.requestFocus();
		ForFocus.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				try{
				pressedKeys.add(e.getKeyCode());
				}catch(Exception ex){}
			}
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				try{
				pressedKeys.remove(e.getKeyCode());
				}catch(Exception ex){}
			}
		});
		
		image = new ImageIcon("redball.png");
		image = new ImageIcon(image.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH));
		ball = new JLabel(image);
		ball.setBounds(472,211,image.getIconWidth(),image.getIconHeight());
		background.add(ball);
		
		winner = new JLabel("Ping Pong");
		winner.setBounds(300,80,400,100);
		winner.setForeground(Color.red);
		winner.setFont(new Font("TimesRoman",Font.BOLD,80));
		background.add(winner);
		
		starting = new JLabel("Please press 'Enter' key to start the game....");
		starting.setBounds(250,300,500,50);
		starting.setForeground(Color.red);
		starting.setFont(new Font("TimesRoman",Font.BOLD,25));
		background.add(starting);
		
		Panels panelsThread = new Panels();
		panelsThread.start();
		
	}
	
	public class Panels extends Thread{
		public void run(){
			while(true){
				try{
					for (Iterator<Integer> it = pressedKeys.iterator(); it.hasNext();) {
						int key = it.next();
						if(key == KeyEvent.VK_ENTER){
							winner.setVisible(false);
							starting.setVisible(false);
							if(isFirst){
								try{Thread.sleep(1000);}catch(Exception e){}
								ballThread.start();
								isFirst = false;
							}
							canStart = true;
						}else if(key == 'A' || key == 'a'){
							if(left.getY()>=14){
								left.setBounds(left.getX(),left.getY()-1,left.getWidth(),left.getHeight());
							}
						}else if(key == 'Z' || key == 'z'){
							if(left.getY()<=370){
								left.setBounds(left.getX(),left.getY()+1,left.getWidth(),left.getHeight());
							}
						}else if(key == 'K' || key == 'k'){
							if(right.getY()>=14){
								right.setBounds(right.getX(),right.getY()-1,right.getWidth(),right.getHeight());
							}
						}else if(key == 'M' || key == 'm'){
							if(right.getY()<=370){
								right.setBounds(right.getX(),right.getY()+1,right.getWidth(),right.getHeight());
							}
						}
						
						Iterator<Integer> it1 = pressedKeys.iterator();
						int i = 0;
						while(it1.hasNext()) {
							i++;
							it1.next();
						}
						if(i!=1){
							try{Thread.sleep(1);}catch(Exception e){}
						}else{
							try{Thread.sleep(2);}catch(Exception e){}
						}
					}
				}catch(Exception ex){}
			}
		}
	}
	
	public class Ball extends Thread{
		public void run(){
			
			Random random = new Random();
			int randomValue = random.nextInt(2);
			int directionX = 0;
			int directionY = 0;
			if(randomValue == 0){
				directionX = -1;
			}else{
				directionX = 1;
			}
			randomValue = random.nextInt(2);
			if(randomValue == 0){
				directionY = -1;
			}else{
				directionY = 1;
			}
			
			while(true){
				
				if(ball.getX()==7 && (left.getY()-30)<=ball.getY() && ball.getY()<=(left.getY()+74)){
					directionX = 1;
				}
				
				if(ball.getX()==938 && (right.getY()-30)<=ball.getY() && ball.getY()<=(right.getY()+74)){
					directionX = -1;
				}
				
				if(ball.getY()==0){
					directionY = 1;
				}
				
				if(ball.getY()==422){
					directionY = -1;
				}
				
				if(ball.getX()==1000 || ball.getX()==-55){
					
					if(ball.getX()==1000){
						winner.setText("Player 1 is winner");
					}else{
						winner.setText("Player 2 is winner");
					}
					winner.setBounds(200,80,800,100);
					winner.setVisible(true);
					
					starting.setVisible(true);
					
					ball.setBounds(472,211,image.getIconWidth(),image.getIconHeight());
					
					canStart = false;
					while(!canStart){
						try{Thread.sleep(1000);}catch(Exception e){}
					}
					
				}else{
				
					ball.setBounds(ball.getX()+directionX,ball.getY()+directionY,ball.getWidth(),ball.getHeight());
					
					try{Thread.sleep(3);}catch(Exception e){}
					
				}
				
			}
			
		}
	}
	
	public static void main(String args[]){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new PingPong();
			}
		});
	}
}