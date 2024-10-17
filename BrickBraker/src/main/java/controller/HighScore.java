

package controller;

/**
 * Κλάση για χειρισμό των highscores από τον Firebase
 */
public class HighScore {
    private String name;    /* όνομα παίκτη */
    private int score;      /* score παίκτη */

    /**
     * Δημιουργεί ένα highscore 
     * @param name  , το όνομα του παίκτη
     * @param score , το σκορ του παίκτη
     */
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Επιστρέφει το όνομα του παίκτη
     * @return , το όνομα του παίκτη
     */
    public String getName() {
        return name;
    }

    /**
     * Επιστρέφει το σκορ του παίκτη
     * @return , το σκορ του παίκτη
     */
    public int getScore() {
        return score;
    }


}