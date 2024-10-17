package view;

import controller.BrickBraker;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.*;

/**
 * Παράθυρο εκτέλεσης BrickBraker
 */
public class View extends JFrame {
    private ClassLoader cldr;
    private JPanel gridPanel;
    private JPanel infoPanel;
    private int rows;
    private int cols;
    private int gridWidth;
    private int gridHeight;
    
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JLabel nextLevelScoreLabel;
    final int brickWidth = 50;
    final int brickHeight = 30;
    BrickBraker brickbraker;
    
    
    /**
     * Δημιουργεί το παράθυρο BrickBraker  και το γεμίζει με τα βασικά JSwing συστατικά
     * @param brickbraker , ο controller του BrickBraker
     */
    public View(BrickBraker brickbraker) {
        this.brickbraker = brickbraker;
        this.rows = brickbraker.getBoard().getRows();
        this.cols = brickbraker.getBoard().getCols();
        gridWidth = cols*brickWidth;
        gridHeight = rows*brickHeight;
        cldr = this.getClass().getClassLoader();
        
        this.setResizable(false);
        this.setTitle("BrickBraker");
        int frameW = cols*(brickWidth+2);
        int frameH = rows*(brickHeight+2)+50;
        this.setPreferredSize(new Dimension(frameW, frameH));
        this.setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // DO_NOTHING_ON_CLOSE);         

        
        //grid panel
        gridPanel = new JPanel(new GridLayout(rows, cols, 0, 0));
        gridPanel.setBounds(0, 0, gridWidth, gridHeight);
        //bricks
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                BrickButton bb = new BrickButton(i,j);
                gridPanel.add(bb);
            }
        }        
        
        infoPanel = new JPanel();
        infoPanel.setBounds(0, gridHeight, gridWidth, 30);
        infoPanel.setBackground(Color.lightGray);
        infoPanel.setOpaque(true);         
        scoreLabel = new JLabel("0     ");
        levelLabel = new JLabel("1     ");
        nextLevelScoreLabel = new JLabel("0     ");
        
        infoPanel.add(new JLabel("Level:"));
        infoPanel.add(scoreLabel);
        infoPanel.add(new JLabel("Score:"));
        infoPanel.add(levelLabel);
        infoPanel.add(new JLabel("Next Level:"));
        infoPanel.add(nextLevelScoreLabel);
        
        this.add(gridPanel);
        this.add(infoPanel);
        pack();
    }
    
    /** 
     * Ενημερώνει το UI με τα τουβλάκια του παιχνιδιού
     */
    public void updateGrid() {
        this.rows = brickbraker.getBoard().getRows();
        this.cols = brickbraker.getBoard().getCols();        
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(rows, cols, 0, 0));
        for (int i=rows-1; i>=0; i--) {
            for (int j=0; j<cols; j++) {
                Brick b = brickbraker.getBoard().getBrick(i, j);
                if (b==null) {
                    gridPanel.add(new JLabel());
                }
                else {
                    BrickButton button = new BrickButton(i,j);

                    String path;
                    if (b instanceof BombBrick) {
                        path = "bomb.png";
                        button.setIcon(getImage(path));
                        button.setOpaque(true);
                    } 
                    if (b instanceof ColorBombBrick) {
                        path = "colorbomb.png";
                        button.setIcon(getImage(path));
                        button.setOpaque(true);
                    }  
                    if (b instanceof NewLineBrick) {
                        path = "newline.png";
                        button.setIcon(getImage(path));
                        button.setOpaque(true);
                    } 
                    if (b instanceof SuffleBrick) {
                        path = "suffle.png";
                        button.setIcon(getImage(path));
                        button.setOpaque(true);
                    }               
                    if (b instanceof JokerBrick) {
                        path = "joker.png";
                        button.setIcon(getImage(path));
                        button.setOpaque(true);
                    }   

                    // brick colors
                    Color color;
                    color = switch (b.getColor()) {
                        case blue -> Color.blue;
                        case pink -> Color.pink;
                        case cyan -> Color.cyan;
                        case yellow -> Color.yellow;
                        case green -> Color.green;
                        case magenta -> Color.magenta;
                        case orange -> Color.orange;
                        case black -> Color.black;
                        case red -> Color.red;
                        case nocolor -> Color.white;
                        default -> Color.white;
                    };
                    button.setBackground(color);
                    button.setBorder(BorderFactory.createLineBorder(Color.gray,1));
                    button.addMouseListener(new ButtonListener());

                    gridPanel.add(button);
                }
            }
        }
        
        scoreLabel.setText(brickbraker.getLevel()+"     ");
        levelLabel.setText(brickbraker.getScore()+"     ");
        nextLevelScoreLabel.setText(brickbraker.getNextScore()+"     ");
        
        //repaint();
        pack();
    }    
        
        
    /**
     * Βάζει μια εικόνα σε ένα JButton
     * @param μονοπάτι προς εικόνα
     * @return αντικείμενο ImageIcon
     */
    private ImageIcon getImage(String path) { // image for card
        try {
            path = "/"+path;
            Image img = ImageIO.read(getClass().getResource(path)).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
    
        } catch (Exception ex) {
            System.out.println(ex);
            System.exit(1);
            return null;
        }
    } 

    
    /**
     * Button listener class for BrickButton
     * - Καλεί την action() του αντίστοιχου τούβλου
     * - Εμφανίζει μήνυμα εάν τελείωσε το παιχνίδι
     */
    private class ButtonListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {    
            BrickButton bb = ((BrickButton) e.getSource());
            System.out.println("clicked "+bb);
            brickbraker.action(bb.get_x(), bb.get_y());
        }
        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }        
    }
}
