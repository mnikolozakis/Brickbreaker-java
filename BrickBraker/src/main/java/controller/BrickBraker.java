
package controller;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Board;
import view.View;


/**
 * * Το παιχνίδι BrickBraker
 */
public class BrickBraker {
    private View view;          /* UI */
    private Board board;
    private int level;          /* επίπεδο παίκτη */
    private int levelRows;      /* γραμμές επιπέδου */
    private int levelCols;      /* στήλες επιπέδου */
    private int levelColors;    /* χρώματα επιπέδου */
    private int levelSpecial;   /* special bricks επιπέδου */
    private int levelPoints;    /* πόντοι για επόμενο επίπεδο */
    
    private int score;          /* πόντοι παίκτη */
    private int nextScore;      /* συνολικοί πόντοι για επόμενο επίπεδο */
        
    /**
     * Δημιουργεί ένα νέο παιχνίδι
     */
    public BrickBraker() {
        level = 1;
        score = 0;
        nextScore = 0;
        updateSettings();
        board = new Board(levelRows, levelCols, levelColors, levelSpecial);
        nextScore += levelPoints;
    }  
       
     /**
      * Ενημερώνει τα χαρακτηριστικά του παιχνιδιού που εξαρτώνται από το επίπεδο
      */   
    private void updateSettings() {
        levelRows = 14 + (level-1) / 2;
        levelCols = 12 + level;
        levelSpecial = levelRows * levelCols * 5 / 100;
        levelColors = 4 + (level-1);
        levelPoints = 80 + (level * 20);
    }

    /**
     * Εκκίνηση του παιχνιδιού
     * Κάνει την απαραίτητη προετοιμασία πριν ξεκινήσει το παιχνιδι.
     */
    public void start() {
        view = new View(this);
        view.setVisible(true);
        view.updateGrid();
        System.out.print(this);        
    }
    
    
    /**
     * Εκτελεί την ενέργεια για το τούβλο στη θέση x,y
     * Ταυτόχρονα υπολογίζει το σκορ, ελέγχει επόμενο επίπεδο και τέλος του παιχνιδιού
     * @param x, η γραμμή
     * @param y, η στήλη
     */
    public void action(int x, int y) {
        System.out.println("action ("+x+","+y+")");
        
        /* κλήση της action στο πλέγμα */
        int actionPoints = board.action(x, y);
        /* προσαρμογή ποντων ανάλογα το πόσα τουβλάκια αφαιρέθηκαν */
        switch (actionPoints) {
            case 1,2,3,4: score += actionPoints; break;
            case 5,6,7,8,9,10,11,12 : score += (int) (1.5 * actionPoints); break;
            default: score += (int) (2 * actionPoints); break;
        }
        System.out.println("+"+actionPoints);
        view.updateGrid();
        
        /* έλεγχος για τέλος παιχνιδιού */
        if (board.noMoreMoves()) {
            if (score>=nextScore) {
                level++;
                updateSettings();
                JOptionPane.showMessageDialog(null, "Επόμενο επίπεδο "+level);
                board = new Board(levelRows, levelCols, levelColors, levelSpecial);
                nextScore += levelPoints;
                view.updateGrid();
            }
            else  {
                // τελος παιχνιδιού
                JOptionPane.showMessageDialog(null, "Δεν υπάρχουν άλλες κινήσεις\nΤο σκορ σου είναι: "+getScore());
                endOfGame();
            }                
        }
        
    }    

    /**
     * Εκτελεί τις ενέργειες μετά το τέλος του παιχνιδιού
     * - Ελέγχει αν έγινε highscore
     * - Εμφανίζει πίνακα με τα highscores
     */
    private void endOfGame() {
        String onoma=null; //όνομα παίκτη

        //έλεγχος highscore στον Firebase
        FirebaseDAO dao = new FirebaseDAO();

        
        ArrayList<HighScore> L;
        L = dao.getTopScores(); // ανάγνωση πινακα hiscores
        int last = L.size();
        if (last>9) last=9;
        if (score>L.get(last-1).getScore()) {
            //JOptionPane.showMessageDialog(null, "Έκανες high score!\n Προηγούμενο:"+highscore+"\n Νέο:"+score);
            // ζήτα όνομα
            while (onoma==null) 
                onoma = JOptionPane.showInputDialog(null, "Έκανες high score!", "Δώσε όνομα:");
            //int highscore = fb.getScore(onoma); //διάβασε προηγούμενο σκορ
            dao.writeScore(onoma, score);//ενημέρωση Firebase 
            L = dao.getTopScores(); // ανάγνωση πινακα hiscores ξανά 
            System.out.println("Έκανες high score!\n Νέο:"+score);
        }                
        dao.writeScore(onoma, score);//ενημέρωση Firebase                


        //εμφάνιση πίνακα
        String table="ΠΙΝΑΚΑΣ HIGH SCORE\n";
        int i=0;
        for (HighScore e : L) {
            i++;
            table += i +". " + e.getName() + ": " + e.getScore() +"\n";
            if (i>=10)
                break;
        }
        JOptionPane.showMessageDialog(null, table);    
        System.exit(0);
    }
    /**
     * Επιστρέφει το πλέγμα 
     * @return το πλέγμα
     */
    public Board getBoard() {
        return board;
    }


    /**
     * Επιστρέφει το σκορ του παίκτη
     * @return το σκορ
     */
    public int getScore() {
        return score;
    }

    /**
     * Επιστρέφει τους πόντους για το επόμενο επίπεγο
     * @return οι πόντοι
     */
    public int getNextScore() {
        return nextScore;
    }

    /**
     * Επιστρέφει το τρέχον επίπεδο
     * @return το επίπεδο
     */
    public int getLevel() {
        return level;
    }
    
    
    
    /**
     * toString για το BrickBraker
     * @return string
     */
    public String toString() {
        return board.toString();
    }    
        
}
