// Import the GUI libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Random;

public class Main {
    
    /////////////////////////////// MAIN METHOD ////////////////////////////////
    // This main method starts the GUI and runs the createMainWindow() method.
    // This method should not be changed.
    ////////////////////////////////////////////////////////////////////////////
    
    public static void main (String [] args) {
        javax.swing.SwingUtilities.invokeLater (new Runnable () {
            public void run () {
                createMainWindow ();
            }
        });
    }
    

    ////////////////////// STATIC VARIABLES AND CONSTANTS //////////////////////
    // Declare the objects and variables that you want to access across
    // multiple methods.
    ////////////////////////////////////////////////////////////////////////////

    static JLabel evilPirateShip;
    static JLabel pirateShip;
    static boolean isPaused = false;
    static int score = 0;
    static int speedBonus = 0;
    static JLabel scoreLabel;
    static int pausedTime = 0;
    static Timer shipMoveTimer;

    
    //////////////////////////// CREATE MAIN WINDOW ////////////////////////////
    // Inside of the method below, you will set up the main window.  Add
    // components and/or layouts to the contentPane.
    ////////////////////////////////////////////////////////////////////////////
    
    /** This method is called by main() to set up the main GUI window. */
    private static void createMainWindow () {
        // Create and set up the window.
        JFrame frame = new JFrame ("Frame Title");
        frame.setSize(951, 401);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setResizable (false);

        // The panel that will hold the components in the frame.
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(950, 400));

        // Create side panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
        sidePanel.setPreferredSize(new Dimension(175, 300));
        contentPane.add(sidePanel, BorderLayout.EAST);
        
        // Create score
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel scoreTitle = new JLabel("Score");
        scoreTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreTitle.setForeground(new Color(255, 0, 0));
        scoreTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidePanel.add(scoreTitle);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        scoreLabel = new JLabel("0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setForeground(new Color(255, 0, 0));
        scoreLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidePanel.add(scoreLabel);
        sidePanel.add(Box.createVerticalGlue());
        
        // Create action buttons
        JLabel actionLabel = new JLabel("Action");
        actionLabel.setFont(new Font("Arial", Font.PLAIN, 20));      
        actionLabel.setForeground(new Color(255, 0, 0));
        actionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidePanel.add(actionLabel);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 20));
        newGameButton.setForeground(new Color(255, 0, 0));
        newGameButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidePanel.add(newGameButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        newGameButton.addActionListener(new NewGameListener());
        
        JButton musicToggleButton = new JButton("Music Off");
        musicToggleButton.setFont(new Font("Arial", Font.PLAIN, 20));   
        musicToggleButton.setForeground(new Color(255, 0, 0));
        musicToggleButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidePanel.add(musicToggleButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton pauseButton = new JButton("Pause");
        pauseButton.setFont(new Font("Arial", Font.PLAIN, 20));
        pauseButton.setForeground(new Color(255, 0, 0));
        pauseButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidePanel.add(pauseButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        pauseButton.addActionListener(new PauseListener());
        
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        quitButton.setForeground(new Color(255, 0, 0));
        quitButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidePanel.add(quitButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        quitButton.addActionListener(new QuitListener());
        
        // Create the main playing panel
        JLayeredPane gamePane = new JLayeredPane();
        contentPane.add(gamePane, BorderLayout.CENTER);
        
        // Add map image
        JLabel mapImage = new JLabel(new ImageIcon("world-map.jpg"));
        mapImage.setSize(775, 400);
        mapImage.addMouseListener(new MapClickListener());
        //mapImage.setLocation(x, y);
        gamePane.add(mapImage);
        
        // Add the pirate ship
        pirateShip = new JLabel(new ImageIcon("new-pirate-ship.png"));
        pirateShip.setSize(40, 40);
        pirateShip.addMouseListener(new ShipClickListener());
        gamePane.setLayer(pirateShip, 10);
        gamePane.add(pirateShip);

        // NEW: Add the evil pirate ship
        evilPirateShip = new JLabel(new ImageIcon("pirate-ship.png"));
        evilPirateShip.setSize(40, 40);
        evilPirateShip.addMouseListener(new EvilShipClickListener());
        gamePane.setLayer(evilPirateShip, 10);
        gamePane.add(evilPirateShip);
        
        // Randomize the pirate ship location
        moveShip();
        
        // Create a timer to move ship
        shipMoveTimer = new Timer(2000, new ShipMoveListener());
        shipMoveTimer.start();
       
        // Add the panel to the frame
        frame.setContentPane(contentPane);

        //size the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    ////////////////////////////// HELPER METHODS //////////////////////////////
    // Methods that you create to manage repetitive tasks.
    ////////////////////////////////////////////////////////////////////////////

    /** Move the ship to a random location on the screen. */
    public static void moveShip () {
    	// Randomize the pirate ship location
        Random generator = new Random();
        int xShip = generator.nextInt(735);
        int yShip = generator.nextInt(360);
        pirateShip.setLocation(xShip, yShip);
    }

    /** NEW: Move the ship to a random location on the screen. */
    public static void moveEvilShip () {
    	// Randomize the evil pirate ship location
        Random generator = new Random();
        int xShip = generator.nextInt(735);
        int yShip = generator.nextInt(360);
        evilPirateShip.setLocation(xShip, yShip);
    }
    

    ///////////////////////////// EVENT LISTENERS //////////////////////////////
    // Subclasses that handle events (button clicks, mouse clicks and moves,
    // key presses, timer expirations)
    ////////////////////////////////////////////////////////////////////////////
    
    /** Listener that makes the pirate ship move on a timer. */
    private static class ShipMoveListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
        	moveShip();
            moveEvilShip();
            speedBonus = 0;
        }
    }
    
    /** Listener that makes the score increase when the ship is clicked */
    public static class ShipClickListener implements MouseListener {
        public void mouseClicked (MouseEvent event) {
        	// Score to increase by 1 plus the speed bonus.
        	score += speedBonus + 1;
            speedBonus ++;
        	scoreLabel.setText(String.valueOf(score));
        	shipMoveTimer.setDelay(shipMoveTimer.getDelay() - 50);
        	shipMoveTimer.setInitialDelay(shipMoveTimer.getDelay());
        	shipMoveTimer.restart();
        	
        	moveShip();
            moveEvilShip();
        }
        
        public void mousePressed (MouseEvent event) {
        }

        public void mouseReleased (MouseEvent event) {
        }

        public void mouseEntered (MouseEvent event) {
        }

        public void mouseExited (MouseEvent event) {
        }
    }
    
    /** NEW: Listener that makes the score decrease when the evil ship is clicked */
    public static class EvilShipClickListener implements MouseListener {
        public void mouseClicked (MouseEvent event) {
        	// Score to decrease by 1 plus the speed bonus.
        	score -= speedBonus + 1;
            speedBonus ++;
        	scoreLabel.setText(String.valueOf(score));
        	shipMoveTimer.setDelay(shipMoveTimer.getDelay() - 50);
        	shipMoveTimer.setInitialDelay(shipMoveTimer.getDelay());
        	shipMoveTimer.restart();
        	
        	moveShip();
            moveEvilShip();
        }
        
        public void mousePressed (MouseEvent event) {
        }

        public void mouseReleased (MouseEvent event) {
        }

        public void mouseEntered (MouseEvent event) {
        }

        public void mouseExited (MouseEvent event) {
        }
    }
    
    /** NEW: Listener that makes speed bonus zero and moves both ships */
    public static class MapClickListener implements MouseListener {
        public void mouseClicked (MouseEvent event) {
            shipMoveTimer.restart();
        	speedBonus = 0;
        	moveShip();
            moveEvilShip();
        }
        
        public void mousePressed (MouseEvent event) {
        }

        public void mouseReleased (MouseEvent event) {
        }

        public void mouseEntered (MouseEvent event) {
        }

        public void mouseExited (MouseEvent event) {
        }
    }
    
    /** Listener that the game end when Quit button is pressed. */
    private static class QuitListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
        	int answer = JOptionPane.showConfirmDialog(null, 
        			"Are you sure you want to quit?", "Quit?", 
        			JOptionPane.YES_NO_OPTION);
        	if (answer == JOptionPane.YES_OPTION) {
        		System.exit(0);
        	}
        }
    }
    
    /** Listener that the game end when Quit button is pressed. */
    private static class NewGameListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
        	int answer = JOptionPane.showConfirmDialog(null, 
        			"Are you sure you want to start a new game?", "New Game?", 
        			JOptionPane.YES_NO_OPTION);
        	if (answer == JOptionPane.YES_OPTION) {
        		score = 0;
        		scoreLabel.setText("0");
        		shipMoveTimer.setDelay(2000);
        		shipMoveTimer.setInitialDelay(2000);
        		shipMoveTimer.restart();
        	}
        }
    }
    /** Listener that pauses the game. */
    private static class PauseListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            if (isPaused == false) {
                pausedTime = shipMoveTimer.getDelay();
                shipMoveTimer.stop();
                pirateShip.removeMouseListener(new ShipClickListener());
                isPaused = true;
            }
            else {
                shipMoveTimer.setInitialDelay(pausedTime);
                shipMoveTimer.start();
                isPaused = false;
            }
            
        }
    }
}