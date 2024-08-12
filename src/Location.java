public class Location {
    private int x, y;

    Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setNewLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return this.x; }

    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return ("(" + x + ", " + y + ")");
    }

    public boolean compareLocations(Location loc) {
        return ((loc.x == this.x) && (loc.y == this.y));
    }

    public int relative(Location loc) {
        if (compareLocations(loc))
            return 0;//אותו מקום
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

    public boolean isNext(Location loc) {
        if (this.y != loc.getY())
            return false;
        return loc.getX() - 1 == this.x;
    }

    public boolean isBefore(Location loc) {
        if(this.y!=loc.getY())
            return false;
        return this.x-1==loc.getX();
    }
}