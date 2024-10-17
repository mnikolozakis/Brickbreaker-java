
package model;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.Random;

/**
 * Ένα απλό τούβλο με χρώμα που μπορεί όταν πατηθεί
 * απαλοίφονται διαδοχικά γειτονικά (neighboring) οριζοντίως 
 * ή και καθέτως (cross-style) ομοιόχρωμων  Bricks
 */
public class SimpleBrick extends Brick implements ActionBrick {
        
    /**
     * Δημουργεί ένα SimpleBrick με συγκεκριμένο χρώμα
     * @param c , το επιλεγμένο χρώμα
     */
    public SimpleBrick(BrickColor c) {
        color = c;
    }
    
    /**
     * Δημιουργεί ένα SimpleBrick με ένα τυχαίο χρώμα
     * @param r , όριο αριθμού χρώματος (max 9)
     */
    public SimpleBrick(int r) {
        if (r>=9) r=9;
        Random rand = new Random();
        int x = rand.nextInt(r);    /* ένα από τα r χρώματα, έως 9 */
        color = BrickColor.values()[x];
    }


    /**
     * Συνάρτηση toString
     * @return 
     */
    @Override
    public String toString() {
        return color.toString();
    }
    
    /**
     * Αφαιρεί τα διαδοχικά γετνιάζοντα, οριζοντίως και καθέτως τούβλα, με το x,y
     * @param x , γραμμή 
     * @param y , στήλη
     * @param b , πλέγμα
     * @return, τούβλα που αφαιρέθηκαν 
     */
    public int action(int x, int y, Board b) {
        Brick[][] g = b.getGrid();
        BrickColor c = g[x][y].getColor();
        Brick tmp = g[x][y];
        int points = actionRecursive(x, y, c, b);
        /* αν ήταν το μοναδικό, επανέφερέ το γιατί έχει αφαιρεθεί */
        if (points<=1) {
            g[x][y] = tmp;
            points--;
        }
        System.out.println("SimpleBrick points="+points);
        return points;
    }
    
    /**
     * Εκτελεί ΑΝΑΔΡΟΜΙΚΑ την απαλοιφή των τούβλων στο πλέγμα στη θέση x,y
     * @param x , γραμμή 
     * @param y , στήλη
     * @param c , χρώμα απαλοιφής
     * @param b , πλέγμα
     */
    private int  actionRecursive(int x, int y, BrickColor c, Board b) {
        Brick[][] g = b.getGrid();
        int x1 = min(x+1, b.rows);
        int x2 = max(x-1,0);
        int y1 = max(y-1,0);
        int y2 = min(y+1,b.cols);
        int points;
        
        /* το συγκεκριμένο τούβλο πάντα αφαιρείται */
        g[x][y]=null;
        points = 1;

        if (g[x1][y]!=null && (g[x1][y].getColor()==c || g[x1][y] instanceof JokerBrick))
            points += actionRecursive(x1, y, c, b);
        
        if (g[x][y1]!=null && (g[x][y1].getColor() == c || g[x][y1] instanceof JokerBrick)) 
            points += actionRecursive(x, y1, c, b);
        if (g[x][y2]!=null && (g[x][y2].getColor() == c || g[x][y2] instanceof JokerBrick)) 
            points += actionRecursive(x, y2, c, b);
            
        if (g[x2][y]!=null && (g[x2][y].getColor() == c || g[x2][y] instanceof JokerBrick))
            points += actionRecursive(x2, y, c, b);
        
        return points;
    }
        
    
}
