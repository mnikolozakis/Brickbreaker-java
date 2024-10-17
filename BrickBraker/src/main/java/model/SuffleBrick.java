package model;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Random;

/**
 * Ένα SuffleBrick
 * Σε περίπτωση που πατηθεί μπορεί το χρώμα με το οποίο είναι την εκάστοτε φορά συσχετισμένο να αλλάξει τυχαία
 * ώστε να εξυπηρετήσει τις κατά περίπτωση ανάγκες του παίχτη. 
 * Η εναλλαγή δύναται να γίνει αποκλειστικά και μόνο μέχρι μια φορά.
 */
public class SuffleBrick extends SimpleBrick { //implements ActionBrick {
    
    /**
     * Δημουργεί ένα SuffleBrick με συγκεκριμένο χρώμα
     * @param c , το επιλεγμένο χρώμα
     */
    public SuffleBrick(BrickColor c) {
        super(c);
    }
    
    /**
     * Δημιουργεί ένα SuffleBrick με ένα τυχαίο χρώμα
     * @param r , όριο αριθμού χρώματος (max 9)
     */
    public SuffleBrick(int r) {
        super(r);
    }
    
    @Override
    public String toString() {
        return "*S";
    }    
    
    /**
     * Μετατρέπει το SuffleBrick στη θέση x,y , σε SimpleBrick ΔΙΑΦΟΡΕΤΙΚΟΥ χρώματος
     * @param x , γραμμή 
     * @param y , στήλη
     * @param b , πλέγμα
     * @return, τούβλα που αφαιρέθηκαν 
     */
    @Override
    public int action(int x, int y, Board b) {
        BrickColor c;
        Random rand = new Random();
        
        do {
            int r = rand.nextInt(b.getColors());    
            c = BrickColor.values()[r];
        } while (c==this.color);
        
        b.getGrid()[x][y] = new SimpleBrick(c);
        
        return 0;
    }    
}