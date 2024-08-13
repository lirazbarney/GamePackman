import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;

public class PacmanMap {
    public static final int rows = 31, cols = 28;
    public static JFrame frame = new JFrame();
    public static JPanel pmap = new JPanel();
    public static Color[][] colorMapGrid = new Color[rows][cols];
    public static NewPanels[][] panelGrid = new NewPanels[rows][cols];
    public static int[][] modes = new int[rows][cols];
    public static PackmanFigure pacguy;
    public static BufferedImage pacImg, ballImg, bigBallImg, ghostImg;
    public static JLabel pic;
    public static Component[] componentList;
    public static final int startRow = 22, startCol = 14;
    public static int ballsCount = 0, steps = 0;
    public static Ghost redG, orangeG, cyanG, pinkG;
    public static JButton upb, leftb, rightb, downb;
    public static JPanel panelbuttons;
    public static boolean isGot = false;
    public static int keyPressed, lastkey;
    public static int cyanTimer=333, redTimer=333, orangeTimer=333, pinkTimer=333;
    public  static boolean isGameOn = false;
    public static boolean hasWon = false;

    PacmanMap() {
        frame.setLayout(new GridLayout(1, 2));
        pmap.setBorder(BorderFactory.createTitledBorder(""));
        pmap.setLayout(new GridLayout(rows, cols));
        String line = "";
        for (int x = 0; x < rows; x++) {
            line = "";
            for (int y = 0; y < cols; y++) {
                line += "(" + x + "," + y + "):[" + modes[x][y] + "] ";
                panelGrid[x][y] = new NewPanels(x, y, modes[x][y]);
                pmap.add(panelGrid[x][y].getPanel());
            }
            System.out.println(line);
        }
        NewPanels up, next, before, down;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (x == 0)
                    up = null;
                else
                    up = panelGrid[x - 1][y];
                if (x == rows - 1)
                    down = null;
                else
                    down = panelGrid[x + 1][y];
                if (y == 0)
                    before = null;
                else
                    before = panelGrid[x][y - 1];
                if (y == cols - 1)
                    next = null;
                else
                    next = panelGrid[x][y + 1];
                panelGrid[x][y].setPanels(up, down, next, before);
            }
        }

        pacguy = new PackmanFigure(panelGrid[startRow][startCol]);
        pacguy.getCurrentPanel().setContain(3);
        redG = new Ghost(panelGrid[10][14], 0, 7);
        panelbuttons = new JPanel();
        framePaint();
        frame.setVisible(true);
        frame.add(pmap);
        frame.setResizable(true);
        pmap.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        processKeys();
        frame.pack();
        isGameOn = true;
        Timer timer = new Timer();
        long delay = 0;
        long period = 16; // about 60 FPS
        timer.scheduleAtFixedRate(new TimerTask() { //the main game timer, about every 60 fps the game will be updating
            @Override
            public void run() {
                if (isGameOn) {
                    if (steps > 0) {
                        if (redG != null) {
                            redG.moves(pacguy.getCurrentPanel().getLoc());
                        }
                        if (pinkG != null) {
                            pinkG.moves(pacguy.getCurrentPanel().getLoc());
                        } else {
                            if ((panelGrid[10][14].isEmpty()) && (redG != null)) {
                                pinkG = new Ghost(panelGrid[10][14], 0, 5);
                            }
                        }
                        if (cyanG != null) {
                            cyanG.moves(pacguy.getCurrentPanel().getLoc());
                        } else {
                            if ((panelGrid[10][14].isEmpty()) && (pinkG != null)) {
                                cyanG = new Ghost(panelGrid[10][14], 0, 6);
                            }
                        }
                        if (orangeG != null) {
                            orangeG.moves(pacguy.getCurrentPanel().getLoc());
                        } else {
                            if ((panelGrid[10][14].isEmpty()) && (cyanG != null)) {
                                orangeG = new Ghost(panelGrid[10][14], 0, 4);
                            }
                        }
                        framePaint(); // re-updating the screen
                    }
                } else {
                    timer.cancel(); // Stop the timer if the game is over
                    if (hasWon) {
                        JOptionPane.showMessageDialog(frame, "you won! :)");
                    } else {
                        JOptionPane.showMessageDialog(frame, "you lost! :(");
                    }
                }
            }
        }, delay, period);
    }

    public static void framePaint() { //this function update the screen
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                componentList = panelGrid[i][j].getPanel().getComponents();
                for (int z = 0; z < componentList.length; z++)
                    panelGrid[i][j].getPanel().remove(componentList[z]);
                switch (panelGrid[i][j].getContain()) {
                    case 1:
                        try {
                            ballImg = ImageIO.read(new File("assets/ball3.png"));
                            pic = new JLabel(new ImageIcon(ballImg));
                            panelGrid[i][j].getPanel().add(pic);
                        } catch (IOException e) {
                        }
                        break;
                    case 2:
                        try {
                            bigBallImg = ImageIO.read(new File(("assets/bigball3.png")));
                            pic = new JLabel(new ImageIcon(bigBallImg));
                            panelGrid[i][j].getPanel().add(pic);
                        } catch (IOException e) {
                        }
                        break;
                    case 3:
                        switch (pacguy.getDir()) {
                            case 0:
                                try {
                                    pacImg = ImageIO.read(new File("assets/leftPac.png"));
                                    pic = new JLabel(new ImageIcon(pacImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 1:
                                try {
                                    pacImg = ImageIO.read(new File("assets/upPac.png"));
                                    pic = new JLabel(new ImageIcon(pacImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 2:
                                try {
                                    pacImg = ImageIO.read(new File("assets/rightPac.png"));
                                    pic = new JLabel(new ImageIcon(pacImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 3:
                                try {
                                    pacImg = ImageIO.read(new File("assets/downPac.png"));
                                    pic = new JLabel(new ImageIcon(pacImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                        }
                        break;
                    case 4:
                        switch (orangeG.getDir()) {
                            case 0:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/leftOrange.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 1:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/upOrange.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 2:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/rightOrange.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 3:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/downOrange.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                        }
                        break;
                    case 5:
                        switch (pinkG.getDir()) {
                            case 0:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/leftPink.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 1:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/upPink.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 2:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/rightPink.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 3:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/downPink.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                        }
                        break;
                    case 6:
                        switch (cyanG.getDir()) {
                            case 0:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/leftCyan.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 1:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/upCyan.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 2:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/rightCyan.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 3:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/downCyan.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                        }
                        break;
                    case 7:
                        switch (redG.getDir()) {
                            case 0:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/leftRed.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 1:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/upRed.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 2:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/rightRed.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 3:
                                try {
                                    ghostImg = ImageIO.read(new File("assets/downRed.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                        }
                        break;
                }
            }
        }
        frame.repaint();
        frame.validate();
    }

    public static void MapPainting() { //this function load the map to its default/starting setting
        //הפונקציה הזאת צובעת את המפה בהתאם למה שמוחלט מראש - ניתן לשנות את העיצוב של המפה דרך הפונקציה הזאת
        for (int i = 1; i < (cols - 1); i++)
            for (int j = 1; j < (rows - 1); j++)
                modes[j][i] = 1;
        for (int i = 0; i < cols; i++) {
            modes[0][i] = -1;
            modes[rows - 1][i] = -1;
        }
        for (int i = 0; i < rows; i++) {
            modes[i][0] = -1;
            modes[i][cols - 1] = -1;
        }
        for (int i = 11; i < 16; i++) {
            for (int j = 10; j < 18; j++) {
                modes[i][j] = 8;
            }
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 2; j < 4; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 5; j < 7; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 11; j < 14; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 15; j < 17; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 21; j < 23; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 27; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
        }
        for (int i = 7; i < 9; i++) {
            for (int j = 2; j < 7; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 8; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 11; j < 19; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 20; j < 26; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 27; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
        }
        for (int i = 10; i < 12; i++) {
            for (int j = 1; j < 4; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 5; j < 7; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 8; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 17; j < 19; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 20; j < 22; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 23; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
        }
        for (int i = 13; i < 14; i++) {
            for (int j = 2; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 17; j < 22; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 23; j < 26; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 27; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
        }
        for (int i = 2; i < 4; i++) {
            for (int j = 7; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 24; j < 27; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
        }
        for (int i = 1; i < 3; i++) {
            for (int j = 18; j < 20; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
        }
        for (int i = 4; i < 6; i++) {
            for (int j = 17; j < 21; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
        }
        for (int i = 5; i < 7; i++) {
            for (int j = 8; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 24; j < 26; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 27; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
        }
        for (int i = 9; i < 10; i++) {
            for (int j = 5; j < 7; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 20; j < 22; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
        }
        for (int i = 12; i < 13; i++) {
            for (int j = 8; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 17; j < 19; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
            for (int j = 27; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
            }
        }

        for (int i = 8; i < 19; i++) {
            for (int j = 7; j < 21; j++) {
                if (modes[i][j] == 1)
                    modes[i][j] = 0;
            }
        }
        for (int i = 15; i < 23; i++) {
            for (int j = 1; j < 4; j++) {
                if (modes[i][j] == 1)
                    modes[i][j] = 0;
                if (modes[i][cols - j - 1] == 1)
                    modes[i][cols - j - 1] = 0;
            }
        }

        modes[startRow][startCol] = 3;

        for (int i = 4; i < 5; i++) {
            for (int j = 5; j < 6; j++) {
                modes[i][j] = 2;
                modes[i][cols - j - 1] = 2;
                modes[rows - i - 1][j] = 2;
                modes[rows - i - 1][cols - j - 1] = 2;
            }
        }
        for (int i = 11; i < 16; i++) {
            modes[i][10] = -1;
            modes[i][cols - 11] = -1;
        }
        for (int i = 10; i < 17; i++)
            modes[15][i] = -1;
        for (int i = 10; i < 17; i++)
            modes[11][i] = -1;
        modes[11][14] = 8;
    }

    public static void moveRight() {
        if ((pacguy.getCurrentPanel().canNext() == 1) || (pacguy.getCurrentPanel().canNext() == 3)) {
            pacguy.setDir(2);
            pacguy.getCurrentPanel().setContain(0);
            pacguy.setCurrentPanel(pacguy.getCurrentPanel().getNextPanel());
            System.out.println("You moved right! " + pacguy.toString());
        } else
            System.out.println("You didn't move! " + pacguy.toString());
    }

    public static void moveDown() {
        if ((pacguy.getCurrentPanel().canDown() == 1) || (pacguy.getCurrentPanel().canDown() == 3)) {
            pacguy.setDir(3);
            pacguy.getCurrentPanel().setContain(0);
            pacguy.setCurrentPanel(pacguy.getCurrentPanel().getDownPanel());
            System.out.println("You moved down! " + pacguy.toString());
        } else
            System.out.println("You didn't move! " + pacguy.toString());
    }

    public static void moveUp() {
        if ((pacguy.getCurrentPanel().canUp() == 1) || (pacguy.getCurrentPanel().canUp() == 3)) {
            pacguy.setDir(1);
            pacguy.getCurrentPanel().setContain(0);
            pacguy.setCurrentPanel(pacguy.getCurrentPanel().getUpPanel());
            System.out.println("You moved up! " + pacguy.toString());
        } else
            System.out.println("You didn't move! " + pacguy.toString());
    }

    public static void moveLeft() {
        if ((pacguy.getCurrentPanel().canBefore() == 1) || (pacguy.getCurrentPanel().canBefore() == 3)) {
            pacguy.setDir(0);
            pacguy.getCurrentPanel().setContain(0);
            pacguy.setCurrentPanel(pacguy.getCurrentPanel().getBeforePanel());
            System.out.println("You moved left! " + pacguy.toString());
        } else
            System.out.println("You didn't move! " + pacguy.toString());
    }

    public static void handle() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (panelGrid[i][j].getContain() == 1)
                    count++;
            }
        }
        ballsCount = count;
        steps++;
        if (steps == 1) {
            lastkey = keyPressed;
            /*Thread tr = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        redG.moves(pacguy.getCurrentPanel().getLoc());
                        try {
                            Thread.sleep(redTimer);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            });
            tr.start();
            Thread tp = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (pinkG == null) {
                            if ((panelGrid[10][14].isEmpty()) && (redG != null)) {
                                pinkG = new Ghost(panelGrid[10][14], 0, 5);
                            }
                        } else {
                            pinkG.moves(pacguy.getCurrentPanel().getLoc());
                        }
                        try {
                            Thread.sleep(pinkTimer);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            });
            tp.start();
            Thread to = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        if (orangeG == null) {
                            if ((panelGrid[10][14].isEmpty()) && (pinkG != null)) {
                                orangeG = new Ghost(panelGrid[10][14], 0, 4);
                            }
                        } else {
                            orangeG.moves(pacguy.getCurrentPanel().getLoc());
                        }
                        try {
                            Thread.sleep(orangeTimer);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            });
            to.start();
            Thread tc = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        if (cyanG == null) {
                            if ((panelGrid[10][14].isEmpty()) && (orangeG != null)) {
                                cyanG = new Ghost(panelGrid[10][14], 0, 6);
                            }
                        } else {
                            cyanG.moves(pacguy.getCurrentPanel().getLoc());
                        }
                        try {
                            Thread.sleep(cyanTimer);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            });
            tc.start(); */
        }
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (lastkey == keyPressed) {
                    switch (keyPressed) {
                        case 37:
                            moveLeft();
                            break;
                        case 38:
                            moveUp();
                            break;
                        case 39:
                            moveRight();
                            break;
                        case 40:
                            moveDown();
                            break;
                    }
                    if (pacguy.getCurrentPanel().getContain() == 1)
                        ballsCount--;
                    pacguy.getCurrentPanel().setContain(3);
                    if (ballsCount == 0) {
                        isGameOn = false;
                        hasWon = true;
                    }
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
//                    framePaint();
                }
            }
        });
        if (keyPressed == lastkey)
            t2.start();
        else {
            lastkey = keyPressed;
            t2.stop();
        }
        framePaint();
    }

    private void processKeys () {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
                new KeyEventDispatcher() {
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            keyPressed = e.getKeyCode();

                            pacguy.getCurrentPanel().setContain(0);
                            boolean isMoved = false;

                            handle();
                        }
                        return false;
                    }
                });
    }

    public static void main (String[] args) {
        isGameOn = true;
        MapPainting();
        new PacmanMap();
        System.out.println("start position: " + pacguy.toString());
    }
}