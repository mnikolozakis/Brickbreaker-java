
package model;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Ένα BombBrick 
 * Πατώντας το διαγράφονται όλα τα κατά μια θέση γειτονικά σε αυτό τουβλάκια.
 */
public class BombBrick extends Brick implements ActionBrick {

    /**
     * Δημιουργεί ένα BombBrick
     */
    public BombBrick() {
        this.color = BrickColor.nocolor;
    }
    
    public String toString() {
        return "*B";
    }
    
    /**
     * Αφαιρεί από το πλέγμα τα 9 γειτονικά τούβλα του x,y
     * @param x , γραμμή 
     * @param y , στήλη
     * @param b , πλέγμα
     * @return, τούβλα που αφαιρέθηκαν 
     */
    @Override
    public int action(int x, int y, Board b) {
        Brick[][] g = b.getGrid();
        BrickColor c = g[x][y].getColor();
        int x1 = min(x+1, b.rows);
        int x2 = max(x-1,0);
        int y1 = max(y-1,0);
        int y2 = min(y+1,b.cols);
        int points;
        
        /* το συγκεκριμένο τούβλο πάντα αφαιρείται */
        g[x][y]=null;
        points = 1;

        if (g[x1][y1]!=null) {
            g[x1][y1]=null;
            points++;
        }
        if (g[x1][y]!=null) {
            g[x1][y]=null;
            points++;
        }
        if (g[x1][y2]!=null) {
            g[x1][y2]=null;
            points++;
        }
        
        
        if (g[x][y1]!=null) {
            g[x][y1]=null;
            points++;
        }
        if (g[x][y2]!=null) {
            g[x][y2]=null;
            points++;
        }
            
        
        if (g[x2][y1]!=null) {
            g[x2][y1]=null;
            points++;
        }
        if (g[x2][y]!=null) {
            g[x2][y]=null;
            points++;
        }
        if (g[x2][y2]!=null) {
            g[x2][y2]=null;
            points++;        
        }
        
        System.out.println("BombBrick points="+points);
        return points;
    }    
}
