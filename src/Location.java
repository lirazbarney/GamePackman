public class Location {
    private int x, y;

    Location (int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX () { return this.x; }
    public int getY () {
        return this.y;
    }
    @Override
    public String toString() {
        return ("(" + x + ", " + y + ")");
    }
    public boolean compareLocations (Location loc) {
        return ((loc.x == this.x) && (loc.y == this.y));
    }
    public int relative (Location loc) {
        /*
        0 - same location
        1 -
        2 -
        3 -
        4 -
        5 -
        6 -
        7 -
        8 -
         */
        if (compareLocations(loc))
            return 0;
        if ((this.x == loc.x) && (this.y < loc.getY()))
            return 1;//המקום שקיבלתי הוא בדיוק מימין
        if ((this.x < loc.getX()) && (this.y < loc.getY()))
            return 2;//המיקום שקיבלתי הוא במיקום יחסי של למטה-ימינה
        if ((this.x < loc.getX()) && (this.y == loc.getY()))
            return 3;//המיקום שקיבלתי הוא בדיוק למטה
        if ((this.x < loc.getX()) && (this.y > loc.getY()))
            return 4;//המיקום שקיבלתי הוא במיקום יחסי של למטה-שמאל
        if ((this.x == loc.getX()) && (this.y > loc.getY()))
            return 5;//המיקום שקיבלתי הוא בדיוק משמאל
        if ((this.x > loc.getX()) && (this.y > loc.getY()))
            return 6;//המיקום שקיבלתי הוא במיקום יחסי של למעלה-שמאל
        if ((this.x > loc.getX()) && (this.y == loc.getY()))
            return 7;//המיקום שקיבלתי הוא בדיוק למעלה
        return 8;//המיקום שקיבלתי הוא במיקום יחסי למעלה-ימין
    }
    public boolean isInRadius(Location loc, int radius) {
        return (Math.abs(this.x - loc.getX()) <= radius) && (Math.abs(this.y - loc.getY()) <= radius);
    }
}