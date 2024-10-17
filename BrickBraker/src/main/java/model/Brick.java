
package model;

/**
 * Αφηρημένη κλάση για ένα τούβλο 
 */
public abstract class Brick {
    protected BrickColor color; // το χρώμα του τούβλου
    
    /** 
     * Επιστρέφει το χρώμα του brick
     * @return 
     */
    public BrickColor getColor() {
        return color;
    }    
}
