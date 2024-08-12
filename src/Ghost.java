import org.w3c.dom.Node;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
public class Ghost {
    private NewPanels currentPanel;
    private int pattern, dir, lastContain=0;
    boolean carzyBlue=false;

    Ghost(NewPanels currentPanel, int dir, int pattern){
     this.currentPanel=currentPanel;
     this.dir=dir;
     this.pattern=pattern;
     this.lastContain=this.currentPanel.getContain();
     this.currentPanel.setContain(this.pattern);
    }

    public void moveDown(){
        this.currentPanel.setContain(this.lastContain);
        setCurrentPanel(this.currentPanel.getDownPanel());
        this.lastContain=currentPanel.getContain();
        this.currentPanel.setContain(this.pattern);
        setDir(3);
    }
    public void moveRight(){
        this.currentPanel.setContain(this.lastContain);
        setCurrentPanel(this.currentPanel.getNextPanel());
        this.lastContain=currentPanel.getContain();
        this.currentPanel.setContain(this.pattern);
        setDir(2);
    }
    public void moveUp(){
        this.currentPanel.setContain(this.lastContain);
        setCurrentPanel(this.currentPanel.getUpPanel());
        this.lastContain=currentPanel.getContain();
        this.currentPanel.setContain(this.pattern);
        setDir(1);
    }
    public void moveLeft(){
        this.currentPanel.setContain(this.lastContain);
        setCurrentPanel(this.currentPanel.getBeforePanel());
        this.lastContain=currentPanel.getContain();
        this.currentPanel.setContain(this.pattern);
        setDir(0);
    }
    public boolean moves(Location loc){
        Random rand=new Random();
        int temp, direction;
        boolean isPacNear=false;
        if(this.currentPanel.isUp(loc)){
            moveUp();
            isPacNear=true;
            System.out.println("#1 supposed to lose");
        }
        if(this.currentPanel.isDown(loc)){
            moveDown();
            isPacNear=true;
            System.out.println("#2 supposed to lose");
        }
        if(this.currentPanel.isBefore(loc)){
            moveLeft();
            isPacNear=true;
            System.out.println("#3 supposed to lose");
        }
        if(this.currentPanel.isNext(loc)){
            moveRight();
            isPacNear=true;
            System.out.println("#4 supposed to lose");
        }
        if(isPacNear){
            return true;
        }
        else {
            switch (this.pattern) {
                case 4: {
                    if(canMove()) {
                        temp = rand.nextInt(4);
                        if (temp == 0)//תבדוק אם אפשר לזוז שמאלה
                        {
                            if (this.currentPanel.canBefore() > 1) {
                                moveLeft();
                            } else {
                                moves(loc);
                            }
                        }
                        if (temp == 1)//תבדוק אם אפשר לזוז למעלה
                        {
                            if (this.currentPanel.canUp() > 1) {
                                moveUp();
                            } else {
                                moves(loc);
                            }
                        }
                        if (temp == 2)//תבדוק אם אפשר לזוז ימינה
                        {
                            if (this.currentPanel.canNext() > 1) {
                                moveRight();
                            } else {
                                moves(loc);
                            }
                        }
                        if (temp == 3)//תבדוק אם אפשר לזוז למטה
                        {
                            if (this.currentPanel.canDown() > 1) {
                                moveDown();
                            } else {
                                moves(loc);
                            }
                        }
                    }
                    break;
                } //זז רנדומלי, הרוח הכתומה - אמור לעבוד
                case 5: {
                    if(canMove()) {
                        direction = this.currentPanel.getLoc().relative(loc);
                        if (direction == 1) {
                            if (currentPanel.canNext() > 1) {
                                moveRight();
                            } else {
                                temp = rand.nextInt(2);
                                if (temp == 0) {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else
                                        moves(loc);
                                }
                                if (temp == 1) {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else
                                        moves(loc);
                                }
                            }
                        } //פקמן נמצא מימין, אם אי אפשר לזוז ימינה, יזוז למעלה/למטה
                        if (direction == 2) {
                            temp = rand.nextInt(2);
                            if ((currentPanel.canDown() > 1) || (currentPanel.canNext() > 1)) {
                                if (temp == 0) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else {
                                        moveDown();
                                    }
                                } else {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else {
                                        moveRight();
                                    }
                                }
                            } else {
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else {
                                        if (currentPanel.canUp() > 1) {
                                            moveUp();
                                        } else
                                            moves(loc);
                                    }
                                } else {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else {
                                        if (currentPanel.canBefore() > 1) {
                                            moveLeft();
                                        } else
                                            moves(loc);
                                    }
                                }
                            }
                        } //פקמן נמצא בכיוון יחסי של ימין-למטה, יזוז לאחד מהשניים, אם לא ניתן לשניהם, יזוז למעלה/שמאלה
                        if (direction == 3) {
                            if (currentPanel.canDown() > 1) {
                                moveDown();
                            } else {
                                temp = rand.nextInt(2);
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else
                                        moves(loc);
                                }
                                if (temp == 1) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else
                                        moves(loc);
                                }
                            }
                        } //פקמן נמצא למטה, אם אי אפשר לזוז למטה, יזוז ימינה/שמאלה
                        if (direction == 4) {
                            temp = rand.nextInt(2);
                            if ((currentPanel.canDown() > 1) || (currentPanel.canBefore() > 1)) {
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else {
                                        moveDown();
                                    }
                                } else {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else {
                                        moveLeft();
                                    }
                                }
                            } else {
                                if (temp == 0) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else {
                                        if (currentPanel.canUp() > 1) {
                                            moveUp();
                                        } else {
                                            moves(loc);
                                        }
                                    }
                                } else {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else {
                                        if (currentPanel.canNext() > 1) {
                                            moveRight();
                                        } else
                                            moves(loc);
                                    }
                                }
                            }
                        } //פקמן נמצא בכיוון יחסי של שמאל-למטה, יזוז לאחד מהשניים, אם לא ניתן לשניהם, יזוז למעלה/ימינה
                        if (direction == 5) {
                            if (currentPanel.canBefore() > 1) {
                                moveLeft();
                            } else {
                                temp = rand.nextInt(2);
                                if (temp == 0) {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else
                                        moves(loc);
                                }
                                if (temp == 1) {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else
                                        moves(loc);
                                }
                            }
                        } //פקמן נמצא משמאל, אם אי אפשר לזוז שמאלה, יזוז למעלה/למטה
                        if (direction == 6) {
                            temp = rand.nextInt(2);
                            if ((currentPanel.canUp() > 1) || (currentPanel.canBefore() > 1)) {
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else {
                                        moveUp();
                                    }
                                } else {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else {
                                        moveLeft();
                                    }
                                }
                            } else {
                                if (temp == 0) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else {
                                        if (currentPanel.canDown() > 1) {
                                            moveDown();
                                        } else
                                            moves(loc);
                                    }
                                } else {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else {
                                        if (currentPanel.canNext() > 1) {
                                            moveRight();
                                        } else
                                            moves(loc);
                                    }
                                }
                            }
                        } //פקמן נמצא בכיוון יחסי של שמאל-למעלה, יזוז לאחד מהשניים, אם לא ניתן לשניהם, יזוז למטה/ימינה
                        if (direction == 7) {
                            if (currentPanel.canUp() > 1) {
                                moveUp();
                            } else {
                                temp = rand.nextInt(2);
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else
                                        moves(loc);
                                }
                                if (temp == 1) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else
                                        moves(loc);
                                }
                            }
                        } //פקמן נמצא מעל, אם אי אפשר לזוז למעלה, יזוז ימינה/שמאלה
                        if (direction == 8) {
                            temp = rand.nextInt(2);
                            if ((currentPanel.canUp() > 1) || (currentPanel.canNext() > 1)) {
                                if (temp == 0) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else {
                                        moveUp();
                                    }
                                } else {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else {
                                        moveRight();
                                    }
                                }
                            } else {
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else {
                                        if (currentPanel.canDown() > 1) {
                                            moveDown();
                                        } else
                                            moves(loc);
                                    }
                                } else {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else {
                                        if (currentPanel.canBefore() > 1) {
                                            moveLeft();
                                        } else
                                            moves(loc);
                                    }
                                }
                            }
                        } //פקמן נמצא בכיוון יחסי של ימין-למעלה, יזוז לאחד מהשניים, אם לא ניתן לשניהם, יזוז למטה/שמאלה
                    }
                    break;
                } //זז לפי המיקום הכללי לכיוון, הרוח הורודה - אמור לעבוד
                case 6: {
                    if(canMove()) {
                        direction = this.currentPanel.getLoc().relative(loc);
                        if (direction == 1) {
                            if (currentPanel.canBefore() > 1) {
                                moveLeft();
                            } else {
                                temp = rand.nextInt(2);
                                if (temp == 0) {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else
                                        moves(loc);
                                }
                                if (temp == 1) {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else
                                        moves(loc);
                                }
                            }
                        } //פקמן נמצא מימין, אם אי אפשר לזוז שמאלה, יזוז למעלה/למטה
                        if (direction == 2) {
                            temp = rand.nextInt(2);
                            if ((currentPanel.canUp() > 1) || (currentPanel.canBefore() > 1)) {
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else {
                                        moveUp();
                                    }
                                } else {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else {
                                        moveLeft();
                                    }
                                }
                            } else {
                                if (temp == 0) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else {
                                        if (currentPanel.canDown() > 1) {
                                            moveDown();
                                        } else
                                            moves(loc);
                                    }
                                } else {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else {
                                        if (currentPanel.canNext() > 1) {
                                            moveRight();
                                        } else
                                            moves(loc);
                                    }
                                }
                            }
                        } //פקמן נמצא בכיוון יחסי של ימין-למטה, יזוז לאחד מהשניים האחרים, אם לא ניתן לברוח, יזוז לאחד הכיוונים
                        if (direction == 3) {
                            if (currentPanel.canUp() > 1) {
                                moveUp();
                            } else {
                                temp = rand.nextInt(2);
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else
                                        moves(loc);
                                }
                                if (temp == 1) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else
                                        moves(loc);
                                }
                            }
                        } //פקמן נמצא למטה, אם אי אפשר לזוז למעלה, יזוז ימינה/שמאלה
                        if (direction == 4) {
                            temp = rand.nextInt(2);
                            if ((currentPanel.canUp() > 1) || (currentPanel.canNext() > 1)) {
                                if (temp == 0) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else {
                                        moveUp();
                                    }
                                } else {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else {
                                        moveRight();
                                    }
                                }
                            } else {
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else {
                                        if (currentPanel.canDown() > 1) {
                                            moveDown();
                                        } else
                                            moves(loc);
                                    }
                                } else {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else {
                                        if (currentPanel.canBefore() > 1) {
                                            moveLeft();
                                        } else
                                            moves(loc);
                                    }
                                }
                            }
                        } //פקמן נמצא בכיוון יחסי של שמאל-למטה, יזוז לאחד מהשניים האחרים, אם לא ניתן לברוח, יזוז לאחד הכיוונים
                        if (direction == 5) {
                            if (currentPanel.canNext() > 1) {
                                moveRight();
                            } else {
                                temp = rand.nextInt(2);
                                if (temp == 0) {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else
                                        moves(loc);
                                }
                                if (temp == 1) {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else
                                        moves(loc);
                                }
                            }
                        } //פקמן נמצא משמאל, אם אי אפשר לזוז ימינה, יזוז למעלה/למטה
                        if (direction == 6) {
                            temp = rand.nextInt(2);
                            if ((currentPanel.canDown() > 1) || (currentPanel.canNext() > 1)) {
                                if (temp == 0) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else {
                                        moveDown();
                                    }
                                } else {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else {
                                        moveRight();
                                    }
                                }
                            } else {
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else {
                                        if (currentPanel.canUp() > 1) {
                                            moveUp();
                                        } else
                                            moves(loc);
                                    }
                                } else {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else {
                                        if (currentPanel.canBefore() > 1) {
                                            moveLeft();
                                        } else
                                            moves(loc);
                                    }
                                }
                            }
                        } //פקמן נמצא בכיוון יחסי של שמאל-למעלה, יזוז לאחד מהשניים האחרים, אם לא ניתן לברוח, יזוז לאחד הכיוונים
                        if (direction == 7) {
                            if (currentPanel.canDown() > 1) {
                                moveDown();
                            } else {
                                temp = rand.nextInt(2);
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else
                                        moves(loc);
                                }
                                if (temp == 1) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else
                                        moves(loc);
                                }
                            }
                        } //פקמן נמצא מעל, אם אי אפשר לזוז למטה, יזוז ימינה/שמאלה
                        if (direction == 8) {
                            temp = rand.nextInt(2);
                            if ((currentPanel.canDown() > 1) || (currentPanel.canBefore() > 1)) {
                                if (temp == 0) {
                                    if (currentPanel.canBefore() > 1) {
                                        moveLeft();
                                    } else {
                                        moveDown();
                                    }
                                } else {
                                    if (currentPanel.canDown() > 1) {
                                        moveDown();
                                    } else {
                                        moveLeft();
                                    }
                                }
                            } else {
                                if (temp == 0) {
                                    if (currentPanel.canNext() > 1) {
                                        moveRight();
                                    } else {
                                        if (currentPanel.canUp() > 1) {
                                            moveUp();
                                        } else {
                                            moves(loc);
                                        }
                                    }
                                } else {
                                    if (currentPanel.canUp() > 1) {
                                        moveUp();
                                    } else {
                                        if (currentPanel.canNext() > 1) {
                                            moveRight();
                                        } else
                                            moves(loc);
                                    }
                                }
                            }
                        } //פקמן נמצא בכיוון יחסי של ימין-למעלה, יזוז לאחד מהשניים האחרים, אם לא ניתן לברוח, יזוז לאחד הכיוונים
                    }
                    break;
                }//זז לפי המיקום הכללי הרחק, הרוח התכלת - אמור לעבוד
                case 7: {
                    if(canMove()) {
                        int[] arr=new int[4];
                        if(this.getCurrentPanel().canDown()>1)
                            arr[0]=bestWay(this.getCurrentPanel().getDownPanel(), loc, 0);
                        else
                            arr[0]=9999;
                        if(this.getCurrentPanel().canUp()>1)
                            arr[1]=bestWay(this.getCurrentPanel().getUpPanel(), loc, 0);
                        else
                            arr[1]=9999;
                        if(this.getCurrentPanel().canNext()>1)
                            arr[2]=bestWay(this.getCurrentPanel().getNextPanel(), loc, 0);
                        else
                            arr[2]=9999;
                        if(this.getCurrentPanel().canBefore()>1)
                            arr[3]=bestWay(this.getCurrentPanel().getBeforePanel(), loc, 0);
                        else
                            arr[3]=9999;
                        int num=0;
                        for(int i=1;i<4;i++){
                            if(arr[num]>arr[i])
                                num=i;
                        }
                        switch(num) {
                            case 0: {
                                moveDown();
                                break;
                            }
                            case 1: {
                                moveUp();
                                break;
                            }
                            case 2: {
                                moveRight();
                                break;
                            }
                            case 3: {
                                moveLeft();
                                break;
                            }
                        }
                    }
                    break;
                } //מחפש את הדרך הכי מהירה לפקמן וזז לפי זה, הרוח האדומה - אמור לעבוד
            }
        }
        return false;
    }
    public int bestWay(NewPanels thisPanel, Location loc, int count) {
        if((thisPanel.canBefore()<=1)&&(thisPanel.canNext()<=1)&&(thisPanel.canUp()<=1)&&(thisPanel.canDown()<=1))
            return 999; //stuck and cant move
        if((thisPanel.isBefore(loc))||(thisPanel.isDown(loc))||(thisPanel.isNext(loc))||(thisPanel.isUp(loc)))
            return -999; //packman is reachable from here
        int[] arr={0, 0, 0, 0};
        thisPanel.setCanGhostStep(false);
        int num;

        if(thisPanel.canDown()>1) {
            num = bestWay(thisPanel.getDownPanel(), loc, count+1);
            if(num==-999){
                arr[0]=1;
            }
            else {
                if (num<999) {
                    arr[0] = num+1;
                } else {
                    arr[0] = 999;
                }
            }
        }
        else {
            arr[0] = 9999;
        }

        if(thisPanel.canUp()>1) {
            num = bestWay(thisPanel.getUpPanel(), loc, count+1);
            if(num==-999){
                arr[1]=1;
            }
            else {
                if (num<999) {
                    arr[1] = num+1;
                } else {
                    arr[1] = 999;
                }
            }
        }
        else{
            arr[1]=9999;}

        if(thisPanel.canNext()>1) {
            num = bestWay(thisPanel.getNextPanel(), loc, count+1);
            if(num==-999){
                arr[2]=1;
            }
            else {
                if (num<999) {
                    arr[2] = num+1;
                } else {
                    arr[2] = 999;
                }
            }
        }
        else {
            arr[2] = 9999;
        }

        if(thisPanel.canBefore()>1) {
            num = bestWay(thisPanel.getBeforePanel(), loc, count+1);
            if(num==-999){
                arr[3]=1;
            }
            else {
                if (num<999) {
                    arr[3] = num+1;
                } else {
                    arr[3] = 999;
                }
            }
        }
        else {
            arr[3] = 9999;
        }

        thisPanel.setCanGhostStep(true);
        if((arr[0]>=999)&&(arr[1]>=999)&&(arr[2]>=999)&&(arr[3]>=999)){
            return 999;
            //if there is no way to reach packman
        }
        else {
            //there is a way to reach packman. find the fastest way
            int minindex=0;
            for (int i = 1; i < 4; i++) {
                if (arr[minindex] > arr[i])
                    minindex = i;
            }
            return arr[minindex];
            //minindex must be between 0-3 include
        }
    }
    public boolean canMove(){
        if(this.currentPanel.canDown()>1)
            return true;
        if(this.currentPanel.canNext()>1)
            return true;
        if(this.currentPanel.canUp()>1)
            return true;
        if(this.currentPanel.canBefore()>1)
            return true;
        return false;
    }

    public void setCurrentPanel(NewPanels currentPanel) {
        this.currentPanel = currentPanel;
    }
    public int getDir() {
        return dir;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }
    public void setCarzyBlue(boolean carzyBlue) {
        this.carzyBlue = carzyBlue;
    }
    public int getPattern() {
        return pattern;
    }
    public boolean isCarzyBlue() {
        return carzyBlue;
    }
    public void setLastContain(int lastContain) {
        this.lastContain = lastContain;
    }
    public int getLastContain() {
        return lastContain;
    }
    public NewPanels getCurrentPanel() {
        return currentPanel;
    }
}
