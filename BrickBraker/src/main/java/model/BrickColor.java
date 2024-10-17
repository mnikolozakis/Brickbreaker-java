package model;
/**
 * Τα χρώματα που μπορεί να πάρει ένα τούβλο SimpleBrick
 */
public enum BrickColor {
    red,    // 0
    blue,   // 1
    pink,   /* 2 */
    cyan,   /* 3 */
    yellow, /* 4 */
    green,  /* 5 */ 
    magenta,/* 6 */
    orange, /* 7 */
    black,  /* 9 */
    nocolor;  /* 10 */
    
    /* σύνολο 9 χρώματα */
    
    /**
     * string αναπαράσταση του χρώματος
     * @return string
     */
    @Override
    public String toString() {
        switch (this) {
            case blue: return "BL";
            case pink: return "PK";
            case cyan: return "CN";
            case yellow: return "YL";
            case green: return "GN";
            case magenta: return "MG";
            case orange: return "OR";
            case black: return "BK";
            case red: return "RD";
            case nocolor: return "--";
            default: return null;
        }
    }
}
