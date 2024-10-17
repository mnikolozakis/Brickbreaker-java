
package model;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 */
public class Board {
    private Brick[][] grid = new Brick[100][100];
    int rows;
    int cols;
    int colors;
    int specials;
    
    
    /**
     * Δημιουργεί ένα πλέγμα μεγέθους rows x cols, με colors χρώματα και specials ειδικά τούβλάκια
     * @param rows, ο αριθμός γραμμών του πλέγματος
     * @param cols, ο αριθμός στηλών του πλέγματος
     * @param colors, πόσα διαφορετικά χρώματα μπορούν να έχουν τα τουβλάκια
     * @param specials, πόσα ειδικά τουβλάκια μπορεί να έχει το πλέγμα
     */
    public Board(int rows, int cols, int colors, int specials) {
        this.rows = rows;
        this.cols = cols;
        this.colors = colors;
        this.specials = specials;
        
        /* αρχικά βάλε μόνο simple */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new SimpleBrick(colors);
            }
        }
        
        /* άλλαξε ακριβώς colors τούβλα σε special */
        Random rand = new Random();
        for (int a=0; a<specials; a++) {
            int i,j;
            do {
                i = rand.nextInt(rows);    
                j = rand.nextInt(cols);    
            } while ( !(grid[i][j] instanceof SimpleBrick));
            
            int r = rand.nextInt(5);  
            switch (r) {
                case 0 -> grid[i][j] = new BombBrick();
                case 1 -> grid[i][j] = new ColorBombBrick(colors);
                case 2 -> grid[i][j] = new NewLineBrick();
                case 3 -> grid[i][j] = new SuffleBrick(colors);
                case 4 -> grid[i][j] = new JokerBrick();
            }
        }   
    }

    /**
     * Επιστρέφει τον αριθμό των διαφορετικών χρωμάτων που έχει το πλέγμα
     * @return, αριθμός διαφορετικών χρωμάτων 
     */
    public int getColors() {
        return colors;
    }

    /**
     * Επιστρέφει τις γραμμές του πλέγματος
     * @return τις γραμμές
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * Επιστρέφει τις στήλες του πλέγματος
     * @return οι στήλες
     */
    public int getCols() {
        return cols;
    }
    
    
    /** 
     * Επιστρέφειτο πλέγμα 
     * @return το πλέγμα
     */
    public Brick[][] getGrid() {
        return grid;
    }    
    
    /**
     * Επιστρέφει το τούβλο στη θέση i,j
     * @param i, γραμμή
     * @param j, στήλη
     * @return , το τούβλο
     */
    public Brick getBrick(int i, int j){
        return grid[i][j];
    }
    
    /**
     * Ενημερώνει για το εάν το πλέγμα είναι γεμάτο με τούβλα
     * @return true αν είναι πλήρες
     */
    public boolean isFull() {
        /* έλεγξε μόνο την επάνω γραμμή */
        for (int j=0; j<cols; j++) 
            if (grid[rows-1][j]!=null) 
                return false;
        return true;
    }
   
    
    
    /**
     * Αναθέτει στο τούβλο στη θέση x,y να κάνει την απαλοιφή
     * @param x, γραμμή
     * @param y, στήλη
     * @return, τούβλα 
     */
    public int action(int x, int y) {
        if (grid[x][y]==null) return 0;
        if (!(grid[x][y] instanceof ActionBrick)) return 0;
        ActionBrick ab = (ActionBrick) grid[x][y];
        int points = ab.action(x, y, this);
//        System.out.println(this);
//        System.out.println("fixing rows...");
        fixRows();
//        System.out.println(this);
//        System.out.println("fixing columns...");
        fixColumns();
        System.out.println(this);
        return points;
    }
    
    
    /**
     * Συμπληρώνει τα κενά στο πλέγμα προς τα κάτω
     */
    public void fixRows() {        
        for (int j = 0; j < cols; j++) {    
            for (int i = 0; i < rows; i++) {    
                /*  στην κενή θέση ψάξε για το επόμενο τούβλο προς τα πάνω
                    και "κατέβασέ το"
                */
                if (grid[i][j]==null) {
                    for (int k=i+1; k<rows; k++) {
                        if (grid[k][j]!=null) {
                            grid[i][j]=grid[k][j];
                            grid[k][j]=null;
                            i++;
                        }
                    }
                }
            } /* for i */
        } /* for j */
    }

    /**
     * Συμπληρώνει τις κενές στήλες προς τα αριστερά
     */
    private void fixColumns() {
        /* βρες κενή στήλη */
        for (int j = 0; j < cols-1; j++) {
            boolean isEmpty=true;
            for (int i = 0; i < rows; i++) { 
                /* cols-1 : όχι την τελευταία στήλη */
                if (grid[i][j]!=null) {
                    isEmpty=false;
                    break;
                }
            }
            /* αν βρήκες κενή στήλη, μετατώπισε αριστερά */
            if (isEmpty) {
                /* βρες την πρώτη προς τα δεξιά στήλη */
                int k;
                boolean found = false;
                for (k=j+1; k<cols; k++) {
                    if (grid[0][k]!=null) {
                        found = true;
                        break;
                    }
                }
                
                if (found) {
                    /* μετακίνησε τη στήλη k προς τα αριστερά */
                    for (int m=0; m<rows; m++) {
                        grid[m][j] = grid[m][k];
                        grid[m][k] = null;
                    }
                }
            } /* is empty */
        } /* for j */
    }
    
    
    /**
     * Επιστρέφει true αν στο πλέγμα υπάρχουν δυνατές κινήσεις ή όχι
     * @return , true/false
     */
    public boolean noMoreMoves() {
        /* Ψάξε όλο το πλέγμα
            δες αν υπάρχει κάποιο special
            για κάθε SimpleBrick δες αν υπάρχει γειτονικό με το ίδιο χρώμα
        */
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                Brick b = grid[i][j];
                if (b!=null) {
                    if (   (b instanceof BombBrick )
                        || (b instanceof ColorBombBrick)
                        || (b instanceof NewLineBrick)
                        || (b instanceof JokerBrick)
                        || (b instanceof SuffleBrick) )
                        return false;
                    if (b instanceof SimpleBrick) {
                        int x=i;
                        int y=j;
                        int x1 = min(x+1,rows);
                        int x2 = max(x-1,0);
                        int y1 = max(y-1,0);
                        int y2 = min(y+1,cols);                        
                        BrickColor c = b.getColor();
                        if (y!=y1 && grid[x][y1]!=null && grid[x][y1].getColor()==c)
                            return false;       
                        if (y!=y2 && grid[x][y2]!=null && grid[x][y2].getColor()==c)
                            return false;
                        if (x1!=x && grid[x1][y]!=null && grid[x1][y].getColor()==c)
                            return false;
                        if (x2!=x && grid[x2][y]!=null && grid[x2][y].getColor()==c)
                            return false;
                    }
                } /* b!= null */
            } /* for j */
        } /* for i */
        return true;        
    }
    
    
    /**
     * συναρτηση toString για το Board
     * @return string
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("00");
        String s="  ";
        for (int k=0; k<cols; k++) 
            s+=" "+df.format(k);
        s+="\n";
        for (int i=rows-1; i>=0; i--) {
            s+=df.format(i);
            for (int j=0; j<cols; j++) {
                s+=" ";
                if (grid[i][j]==null)
                    s+="  ";
                else 
                    s+=grid[i][j];
            }
            s+="\n";
        }
        //for (int k=0; k<3*cols; k++) s+="-";
        return s;
    }
}
