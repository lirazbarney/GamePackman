import javax.swing.*;
import java.awt.*;

public class NewPanels {
    private Location loc;
    private boolean canBeSteppedOn=false;
    private boolean canGhostStep=false;
    private int contain;
    /*
    1- אי אפשר לדרוך על המשבצת
    0 משבצת ריקה
    1 יש על המשבצת כדור קטן
    2 יש על המשבצת כדור גדול
    3 יש על המשבצת את פקמן
    4 יש על המשבצת את הרוח הכתומה
    5 יש על המשבצת את הרוח הורודה
    6 יש על המשבצת את הרוח התכולה
    7 יש על המשבצת את הרוח האדומה
    8 הריבוע של הרוחות באמצע
    */
    private NewPanels upPanel, nextPanel, beforePanel, downPanel;
    private JPanel panel;
    NewPanels(int x, int y, int mode) {
        this.loc=new Location(x, y);
        this.panel=new JPanel();
        this.contain=mode;
        if((mode>=0)&&(mode<=7)) {
            this.panel.setBackground(Color.GRAY);
            this.canBeSteppedOn=true;
            this.canGhostStep=true;
        }
        if((mode>=4)&&(mode<=7))
            this.canGhostStep=false;
        if(mode==-1)
            this.panel.setBackground(Color.BLACK);
        if(mode==8)
            this.panel.setBackground(Color.YELLOW);
    }
    public Location getLoc() {
        return loc;
    }
    public boolean isEmpty() {return this.contain==0;}
    public void setCanGhostStep(boolean canGhostStep) { this.canGhostStep=canGhostStep; }
    public int getContain(){
        return this.contain;
    }
    public void setContain(int mode){
        this.contain=mode;
        if((this.contain>=0)&&(this.contain<=7)) {
            this.canBeSteppedOn = true;
            this.canGhostStep = true;
        }
        if((this.contain>=4)&&(this.contain<=7))
            this.canGhostStep=false;
    }
    public void setPanels(NewPanels up, NewPanels down, NewPanels next, NewPanels before){
        this.upPanel=up;
        this.beforePanel=before;
        this.nextPanel=next;
        this.downPanel=down;
    }
    /*
    0 - אי אפשר לדרוך בכלל
    1 - רק פקמן יכול לדרוך
    2 - רק רוחות יכולות לדרוך
    3 - כולם יכולים לדרוך
     */
    public int canDown(){
        int temp=0;
        if(this.downPanel!=null) {
            if(this.downPanel.isCanBeSteppedOn())
                temp++;
            if(this.downPanel.isCanGhostStep())
                temp+=2;
        }
        return temp;
    }
    public int canUp(){
        int temp=0;
        if(this.upPanel!=null) {
            if(this.upPanel.isCanBeSteppedOn())
                temp++;
            if(this.upPanel.isCanGhostStep())
                temp+=2;
        }
        return temp;
    }
    public int canNext(){
        int temp=0;
        if(this.nextPanel!=null) {
            if(this.nextPanel.isCanBeSteppedOn())
                temp++;
            if(this.nextPanel.isCanGhostStep())
                temp+=2;
        }
        return temp;
    }
    public int canBefore(){
        int temp=0;
        if(this.beforePanel!=null) {
            if(this.beforePanel.isCanBeSteppedOn())
                temp++;
            if(this.beforePanel.isCanGhostStep())
                temp+=2;
        }
        return temp;
    }
    public boolean isCanBeSteppedOn(){
        return this.canBeSteppedOn;
    }
    public boolean isCanGhostStep() {
        return this.canGhostStep;
    }
    public JPanel getPanel(){
        return this.panel;
    }
    public NewPanels getBeforePanel() {
        return beforePanel;
    }
    public NewPanels getDownPanel() {
        return downPanel;
    }
    public NewPanels getNextPanel() {
        return nextPanel;
    }
    public NewPanels getUpPanel() {
        return upPanel;
    }
    @Override
    public String toString(){
        return (loc.toString()+": can be stpped? "+this.canBeSteppedOn+"; can ghost step? "+this.canGhostStep);
    }
    public boolean isUp(Location loc){
        if(this.loc.getX()==loc.getX())
            return this.loc.getY()-loc.getY()==1;
        return false;
    }
    public boolean isDown(Location loc){
        if(this.loc.getX()==loc.getX())
            return this.loc.getY()-loc.getY()==-1;
        return false;
    }
    public boolean isNext(Location loc){
        if(this.loc.getY()==loc.getY())
            return this.loc.getX()-loc.getX()==-1;
        return false;
    }
    public boolean isBefore(Location loc){
        if(this.loc.getY()==loc.getY())
            return this.loc.getX()-loc.getX()==1;
        return false;
    }
}
