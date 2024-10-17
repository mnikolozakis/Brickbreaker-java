package model;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Ένα ColorBombBrick
 * Όταν πατιέται διαγράφονται μόνο τα ομοιόχρωμα και κατά μια θέση γειτνιάζοντα με αυτό τουβλάκια.
 */
public class ColorBombBrick extends SimpleBrick { //implements ActionBrick {

    /**
     * Δημουργεί ένα ColorBombBrick με συγκεκριμένο χρώμα
     * @param c , το επιλεγμένο χρώμα
     */
    public ColorBombBrick(BrickColor c) {
        super(c);
    }
    
    /**
     * Δημιουργεί ένα ColorBombBrick με ένα τυχαίο χρώμα
     * @param r , όριο αριθμού χρώματος (max 9)
     */
    public ColorBombBrick(int r) {
        super(r);
        color = BrickColor.red;
    }
    
        
    public String toString() {
        return "*C";
    }  
    
    /**
     * Αφαιρεί από το πλέγμα τα 9 γειτονικά τούβλα, του ΙΔΙΟΥ χρώματος (ή Joker), με του x,y
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

        if (g[x1][y1]!=null && (g[x1][y1].getColor()==c || g[x1][y1] instanceof JokerBrick)) {
            g[x1][y1]=null;
            points++;
        }
        if (g[x1][y]!=null && (g[x1][y].getColor()==c || g[x1][y] instanceof JokerBrick)) {
            g[x1][y]=null;
            points++;
        }
        if (g[x1][y2]!=null && (g[x1][y2].getColor()==c || g[x1][y2] instanceof JokerBrick)) {
            g[x1][y2]=null;
            points++;
        }
        
        
        if (g[x][y1]!=null && (g[x][y1].getColor() == c || g[x][y1] instanceof JokerBrick))  {
            g[x][y1]=null;
            points++;
        }
        if (g[x][y2]!=null && (g[x][y2].getColor() == c || g[x][y2] instanceof JokerBrick)) {
            g[x][y2]=null;
            points++;
        }
            
        
        if (g[x2][y1]!=null && (g[x2][y1].getColor() == c || g[x2][y1] instanceof JokerBrick)) {
            g[x2][y1]=null;
            points++;
        }
        if (g[x2][y]!=null && (g[x2][y].getColor() == c || g[x2][y] instanceof JokerBrick)) {
            g[x2][y]=null;
            points++;
        }
        if (g[x2][y2]!=null && (g[x2][y2].getColor() == c || g[x2][y2] instanceof JokerBrick)) {
            g[x2][y2]=null;
            points++;        
        }
        
        System.out.println("ColorBombBrick points="+points);
        return points;
    }    
   
}
