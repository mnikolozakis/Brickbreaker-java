
package model;

/**
 * Περιγράφει τα τούβλα που μπορούν να πατηθούν
 */
public interface ActionBrick {
    /**
     * Η ενέργεια που κάνει το τούβλο στη θέση x,y.
     * Επιστρέφεται ο αριθμός των τούβλων που αφαιρέθηκαν.
     * @param x, γραμμή 
     * @param y, στήλη
     * @param b, πλέγμα
     * @return, τούβλα που αφαιρέθηκαν από το action
     */
    abstract public int action(int x, int y, Board b);
}
