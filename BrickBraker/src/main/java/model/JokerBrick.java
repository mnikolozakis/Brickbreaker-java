
package model;

/**
 * Ένα JokerBrick
 * Δεν δύναται να πατηθεί απευθείας. Συσχετίζεται αυτομάτως κάθε φορά με το χρώμα που βολεύει ώστε να
 * διαγραφούν τα μέγιστα δυνατά τουβλάκια αναλόγως του χρώματος του Brick που πατήθηκε.
 */
public class JokerBrick extends Brick {
    
    /**
     * Δημιουργεί ένα JokerBrick
     */    
    public JokerBrick() {
        this.color = BrickColor.nocolor;
    }
    
    public String toString() {
        return "*J";
    }  
    
    /**
     * Εκτελεί την απαλοιφή των τούβλων στο πλέγμα στη θέση x,y
     * @param x , γραμμή 
     * @param y , στήλη
     * @param b , πλέγμα
     */

    public void action(int x, int y, Board b) {
        return;
    }    
}
