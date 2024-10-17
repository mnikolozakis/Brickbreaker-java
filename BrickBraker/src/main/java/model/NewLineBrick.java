
package model;

/**
 * Ένα NewLineBrick
 * Όταν πατηθεί εισάγει στην κορυφογραμμή του πλέγματος μια νέα γραμμή κανονικών Bricks τυχαίου χρώματος. 
 * Σε περίπτωση που το πλέγμα είναι πλήρες δεν δύναται να πατηθεί.
 */
public class NewLineBrick extends Brick implements ActionBrick {
 
    /**
     * Δημιουργεί ένα NewLineBrick
     */    
    public NewLineBrick() {
        this.color = BrickColor.nocolor;
    }
    
    public String toString() {
        return "*N";
    }  
    
    /**
     * Εκτελεί την προσθήκη τούβλων στην πρώτη γραμμή του πλέγματος
     * και "τακτοποιεί" το πλεγμα.
     * Το πλέγμα ΠΡΕΠΕΙ να έχει ήδη μια κενή θέση
     * @param x , γραμμή 
     * @param y , στήλη
     * @param b , πλέγμα
     * @return, τούβλα που αφαιρέθηκαν 
     */
    @Override
    public int action(int x, int y, Board b) {
        int rows = b.getRows();
        int cols = b.getCols();
        Brick[][] g = b.getGrid();

        
        if (!b.isFull()) return 0;
        g[x][y]=null;
        
        /* αν υπάρχει χώρος, πρόσθεσε γραμμή */
        for (int j=0; j<cols; j++) {
            g[rows-1][j] = new SimpleBrick(b.getColors());
        }
        
        return 0;
    }    
}
