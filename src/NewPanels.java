import javax.swing.*;
import java.awt.*;

public class NewPanels {
    private Location loc;
    private boolean canBeSteppedOn=false;
    private boolean canGhostStep=false;
    private int contain;
    private NewPanels upPanel, nextPanel, beforePanel, downPanel;
    private JPanel panel;
    NewPanels (int x, int y, int mode) {
        this.loc = new Location(x, y);
        this.panel = new JPanel();
        this.contain = mode;
        {
        /*
        -1 is wall
        0 is empty
        1 is ball
        2 is big ball
        3 is pac-man
        4 is orange
        5 is pink
        6 is cyan
        7 is red
        8 is the ghost "house"
         */
            if ((mode >= 0) && (mode <= 7)) {
                this.panel.setBackground(Color.GRAY);
                this.canBeSteppedOn = true;
                this.canGhostStep = true;
            }
            if ((mode >= 4) && (mode <= 7))
                this.canGhostStep = false;
            if (mode == -1)
                this.panel.setBackground(Color.BLACK);
            if (mode == 8)
                this.panel.setBackground(Color.YELLOW);
        } //coloring the background of the map
    }
    public Location getLoc () { return loc; }
    public boolean isEmpty () { return (this.contain == 0); }
    public void setCanGhostStep (boolean canGhostStep) { this.canGhostStep = canGhostStep; }
    public int getContain () { return this.contain; }
    public boolean isCanBeSteppedOn () { return this.canBeSteppedOn; }
    public boolean isCanGhostStep () { return this.canGhostStep; }
    public JPanel getPanel () { return this.panel; }
    public NewPanels getBeforePanel () { return beforePanel; }
    public NewPanels getDownPanel () { return downPanel; }
    public NewPanels getNextPanel () { return nextPanel; }
    public NewPanels getUpPanel () { return upPanel; }
    @Override
    public String toString () { return (loc.toString()+": can be stepped? " + this.canBeSteppedOn + "; can ghost step? " + this.canGhostStep); }
    public void setContain (int mode) {
        /*
                   contain         | canBeSteppedOn | canGhostStep
            -1 is wall             |        X       |      X
            0 is empty             |        V       |      V
            1 is ball              |        V       |      V
            2 is big ball          |        V       |      V
            3 is pac-man           |        V       |      V
            4 is orange            |        V       |      X
            5 is pink              |        V       |      X
            6 is cyan              |        V       |      X
            7 is red               |        V       |      X
            8 is the ghost "house" |        X       |      x
         */ //who can step table
        this.contain=mode;
        if ((this.contain >= 0 ) && (this.contain <= 7)) {
            this.canBeSteppedOn = true;
            this.canGhostStep = true;
        }
        if ((this.contain >= 4) && (this.contain <= 7))
            this.canGhostStep = false;
    }
    public void setPanels (NewPanels up, NewPanels down, NewPanels next, NewPanels before){
        this.upPanel=up;
        this.beforePanel=before;
        this.nextPanel=next;
        this.downPanel=down;
    }

    /*
    0 - no one can steeped on
    1 - only pac-man can step
    2 - only the ghosts can step
    3 - everybody can step
     */

    public int canDown () {
        int temp = 0;
        if (this.downPanel != null) {
            if (this.downPanel.isCanBeSteppedOn())
                temp++;
            if (this.downPanel.isCanGhostStep())
                temp += 2;
        }
        return temp;
    }
    public int canUp(){
        int temp = 0;
        if (this.upPanel != null) {
            if (this.upPanel.isCanBeSteppedOn())
                temp++;
            if (this.upPanel.isCanGhostStep())
                temp += 2;
        }
        return temp;
    }
    public int canNext () {
        int temp = 0;
        if (this.nextPanel != null) {
            if (this.nextPanel.isCanBeSteppedOn())
                temp++;
            if (this.nextPanel.isCanGhostStep())
                temp += 2;
        }
        return temp;
    }
    public int canBefore () {
        int temp = 0;
        if (this.beforePanel != null) {
            if (this.beforePanel.isCanBeSteppedOn())
                temp++;
            if (this.beforePanel.isCanGhostStep())
                temp += 2;
        }
        return temp;
    }
    public boolean isUp (Location loc) {
        if (this.loc.getX() == loc.getX())
            return ((this.loc.getY() - loc.getY()) == 1);
        return false;
    }
    public boolean isDown (Location loc) {
        if (this.loc.getX() == loc.getX())
            return ((this.loc.getY() - loc.getY()) == -1);
        return false;
    }
    public boolean isNext (Location loc) {
        if (this.loc.getY() == loc.getY())
            return ((this.loc.getX() - loc.getX()) == -1);
        return false;
    }
    public boolean isBefore (Location loc) {
        if (this.loc.getY() == loc.getY())
            return ((this.loc.getX() - loc.getX()) == 1);
        return false;
    }
}
