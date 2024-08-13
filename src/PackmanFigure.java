import java.awt.*;
import java.awt.event.KeyEvent;

public class PackmanFigure {
    public int dir = 2;
    /*
    0 - left
    1 - up
    2 - right
    3 - down
    */ //dirs explaining
    public NewPanels currentPanel;
    public int crazyMode = 0;

    PackmanFigure (NewPanels currentPanel) {
        this.currentPanel = currentPanel;
    }
    public void setDir (int dir) {
        this.dir = dir;
    }
    public int getDir () {
        return this.dir;
    }
    public NewPanels getCurrentPanel () { return this.currentPanel; }
    public void setCurrentPanel (NewPanels currentPanel) {
        this.currentPanel = currentPanel;
    }
    @Override
    public String toString() {
        if (this.dir == 0)
            return ("direction is left, currentPanel=" + this.currentPanel.toString());
        if (this.dir == 1)
            return ("direction is up, currentPanel=" + this.currentPanel.toString());
        if( this.dir == 2)
            return ("direction is right, currentPanel=" + this.currentPanel.toString());
        return ("direction is down, currentPanel=" + this.currentPanel.toString());
    }
}

