/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package view;

import java.awt.Dimension;
import javax.swing.JButton;
import model.Board;
import model.Brick;

/**
 * Ένα JLabel που αντιστοιχεί σε ένα Brick του παιχνιδιού
 * σε συγκεκριμένη θέση στο πλέγμα
 */
public class BrickButton extends JButton {
    private int x;  /* γραμμή */
    private int y;  /* στήλη */
    
    /**
     * Δημιουργεί ένα BrickButton που αντιστοιχεί στο τούβλο στη θέση x,y του πλέγματος 
     * @param x, στήλη
     * @param y, γραμμή
     */
    public BrickButton(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }
    
    
    /**
     * Επιστρέφει τη γραμμή του τούβλου στο πλέγμα
     * @return, η γραμμή
     */
    public int get_x() {
        return x;
    }
    
    /**
     * Επιστρέφει τη στήλη του τούβλου στο πλέγμα
     * @return , η στήλη
     */
    public int get_y() {
        return y;
    }

    

}
