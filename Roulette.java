//
import java.util.Arrays;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
public class Roulette {
    public static void main(String[] args) throws IOException {
        DrawingPanel panel = new DrawingPanel(1280, 720);
        MouseAction mouseAction = new MouseAction();
        panel.addMouseListener(mouseAction);
        Graphics g = panel.getGraphics();
        Scanner console = new Scanner(System.in);
        boolean gameover = false;

        //old User/data?
        User player = new User();
        int[][] bet1 = new int[3][12];
        int[] bet2 = new int[3];
        int[] bet3 = new int[6];

        //public File(String var1, String var2) {

        File f1 = new File("C:\\Users\\Family\\Desktop\\Roulette", "RouletteSave.txt");
        f1.createNewFile();
        System.out.println(f1.getAbsolutePath());
        f1.setReadable(true); f1.setWritable(true);

        /*

        CSV

        BetData (money), turns



        */

        
        //test bet data
        /*
        for (int i = 0; i < bet1.length; i++) {
            for (int j = 0; j < bet1[i].length; j++) {
                bet1[i][j] = (int)(Math.random()*2000);
            }
        }
        for (int i = 0; i < bet2.length; i++) {
            bet2[i] = (int)(Math.random()*2000);
        }
        for (int i = 0; i < bet3.length; i++) {
            bet3[i] = (int)(Math.random()*2000);
        }
        */

        //ideas:
        //saveData?
        //plot turnData?/export
        //images public void drawImageFull(Graphics2D g2, BufferedImage image) {



         ////////////////////////////////////////////////////////////////////////////

        //title + intro
        g.setColor(new Color(255, 100, 100));
        g.fillOval(160, 0, 960, 360);
        g.setColor(new Color(0, 0, 0));

        g.setFont(new Font("", 1, 100));
        g.drawString("Roulette", 450, 200);
        g.setFont(new Font("", 1, 30));
        g.drawString("Click to Start...", 550, 500);
        g.setFont(new Font("", 1, 10));

        boolean skipIntro = false;
        while (!skipIntro) {
            System.out.println("placeholder");
            skipIntro = mouseAction.isButtonPressed();
            if (skipIntro) {
                break;
            }
        }
        clear(g);

        //game
        int targetNum; boolean rolled = false;
        int betAdder = 1; //how much the player wants to bet; 1, 5, 10, 50, 100, 500, 1000, 5000, 10000
        clear(g);
        genGrid(g, bet1, bet2, bet3, player);
        int mouseX = 0; int mouseY = 0;
        while (!gameover) {
            skipIntro = false;
            while (!skipIntro) {
                skipIntro = mouseAction.isButtonPressed();
                mouseX = mouseAction.getMouseX();
                mouseY = mouseAction.getMouseY();
                System.out.println(""+Arrays.asList(player.getTurnData())); //toString, toList
            }
            skipIntro = false;
            if ((mouseX <= 600 && mouseY < 150)) { //place bets on grid1;
                bet1[mouseY/50][mouseX/50] += betAdder;
            }
            else if (((mouseX <= 600 && mouseY < 200) && mouseY >= 150)) { //place bets on sp1;
                bet2[mouseX/200] += betAdder;
            }
            else if (((mouseX <= 600 && mouseY < 250) && mouseY >= 200)) { //place bets on sp2;
                bet3[mouseX/100] += betAdder;
            }
            else if ((mouseX >= 850 && mouseY > 600) && mouseX <= 1050) { //clear
                for (int i = 0; i < bet1.length; i++) {
                    for (int j = 0; j < bet1[i].length; j++) {
                        bet1[i][j] = 0;
                    }
                }
                for (int i = 0; i < 3; i++) {
                    bet2[i] = 0;
                }
                for (int i = 0; i < 6; i++) {
                    bet3[i] = 0;
                }
            }
            else if ((mouseX >= 850 && mouseY > 300) && (mouseX <= 1050 && mouseY <= 400)) { //roll
                rolled = true;
            }
            clear(g);
            if (rolled) {
                while(player.getTurns() != (player.getTurnData()).size()-2) {
                    player.addTurn();
                }
                targetNum = ((int)(Math.random()*36+1));
                g.setColor(new Color(255, 255, 255));
                g.fillRect(150, 300, 80, 10);
                g.setColor(new Color(0, 0, 0));
                g.drawString("You rolled a: "+targetNum+"", 150, 310);
                rolled = false;

                int[] red = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
                boolean redContains = false;
                for (int x: red) {
                    if (x == targetNum) {
                        redContains = true;
                    }
                }

                for (int i = 0; i < bet1.length; i++) {
                    for (int j = 0; j < bet1[i].length; j++) {
                        if (bet1[i][j] != 0) {
                            if (targetNum == (3*(i+1)-j)) {
                                player.addMoney(bet1[i][j]);
                            }
                            else {
                                player.addMoney(-1 * bet1[i][j]);
                            }
                        }
                        bet1[i][j] = 0;
                    }
                }
                for (int i = 0; i < 3; i++) {
                    if (bet2[i] != 0) {
                        if (targetNum <= 12) {
                            player.addMoney(bet2[0]);
                        }
                        else {
                            player.addMoney(-1 * bet2[0]);
                        }
                        if (targetNum >= 13 && targetNum <= 24) {
                            player.addMoney(bet2[1]);
                        }
                        else {
                            player.addMoney(-1 * bet2[1]);
                        }
                        if (targetNum >= 25 && targetNum <= 36) {
                            player.addMoney(bet2[2]);
                        }
                        else {
                            player.addMoney(-1 * bet2[2]);
                        }
                    }
                    bet2[i] = 0;
                }
                for (int i = 0; i < 6; i++) {
                    
                    if (bet3[i] != 0) {
                        if (i == 0) {
                            // 1-18
                            if (targetNum < 19) {
                                player.addMoney(bet3[i]);
                            }
                            else {
                                player.addMoney(-1 * bet3[i]);
                            }
                        }
                        if (i == 1) {
                            // even
                            if (targetNum%2 == 0) {
                                player.addMoney(bet3[i]);
                            }
                            else {
                                player.addMoney(-1 * bet3[i]);
                            }
                        }
                        if (i == 2) {
                            // red
                            if (redContains) {
                                player.addMoney(bet3[i]);
                            }
                            else {
                                player.addMoney(-1 * bet3[i]);
                            }
                        }
                        if (i == 3) {
                            // black
                            if (!redContains) {
                                player.addMoney(bet3[i]);
                            }
                            else {
                                player.addMoney(-1 * bet3[i]);
                            }
                        }
                        if (i == 4) {
                            // odd
                            if (targetNum%2 == 1) {
                                player.addMoney(bet3[i]);
                            }
                            else {
                                player.addMoney(-1 * bet3[i]);
                            }
                        }
                        if (i == 5) {
                            // 19-36
                            if (targetNum > 18) {
                                player.addMoney(bet3[i]);
                            }
                            else {
                                player.addMoney(-1 * bet3[i]);
                            }
                        }
                        bet3[i] = 0;
                    }
                }
            }
            mouseX = 1280; mouseY = 720;
            genGrid(g, bet1, bet2, bet3, player);
            //////////
            ArrayList<Integer> temp = player.getTurnData();
            if (temp.size() > 1) {
                if (temp.get(temp.size()-1) != player.getMoney()) {
                    player.addTurnData(player.getMoney());
                }
            }
            else {
                player.addTurnData(player.getMoney());
            }
        }

        //end
        console.close();
    }

    public static void genGrid(Graphics draw, int[][] bet1, int[] bet2, int[] bet3, User player) {
        //1st
        int[] red = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 12; i++) {
                int selected = (3*(i+1)-j); boolean redContains = false;
                for (int x: red) {
                    if (x == selected) {
                        redContains = true;
                    }
                }
                if (redContains) {
                    draw.setColor(new Color(225, 0, 0));
                }
                draw.fillRect(50*(i), 50*(j), 50, 50);
                draw.setColor(new Color(255, 255, 255));
                draw.drawString(""+(3*(i+1)-j), 50*i+18, 50*j+30);
                if (bet1[j][i] != 0) {
                    draw.drawString(""+bet1[j][i], 5+50*i, 50*j +45);
                }
                draw.setColor(new Color(0, 0, 0));
            }
            //2nd
            draw.drawString(""+(j+1)+"st 12", 80+200*j, 180);
            draw.drawRect(0, 150, 200*(j+1), 50);
            if (bet2[j] != 0) {
                draw.setColor(new Color(255, 255, 255));
                draw.drawString(""+bet2[j], 200*j, 200);
                draw.setColor(new Color(0, 0, 0));
            }
        }
        //3rd
        for (int i = 0; i < 6; i++) {
            if (i == 2) {
                draw.setColor(new Color(255, 0, 0));
                draw.fillRect(200, 200, 100, 50);
            }
            else if (i == 3) {
                draw.setColor(new Color(0, 0, 0));
                draw.fillRect(300, 200, 100, 50);
            }
            else {
                draw.drawRect(0, 200, 100*(i+1), 50);
            }
        }
        draw.drawString("1-18", 35, 230); draw.drawString("Even", 135, 230);
        draw.setColor(new Color(255, 255, 255));
        draw.drawString("Red", 235, 230); draw.drawString("Black", 335, 230);
        draw.setColor(new Color(0, 0, 0));
        draw.drawString("Odd", 435, 230); draw.drawString("19-36", 535, 230);

        draw.setColor(new Color(255, 255, 255));
        for (int i = 0; i < 6; i++) {
            if (bet3[i] != 0) {
                draw.drawString(""+bet3[i], 100*i, 250);
            }
        }

        draw.setColor(new Color(255, 255, 255));
        draw.fillRect(850, 300, 200, 100); //roll
        draw.fillRect(850, 610, 200, 100); //clear
        draw.fillRect(0, 620, 200, 100); //save
        draw.fillRect(650, 15, 150, 100); //turns
        draw.fillRect(1100, 15, 150, 100); //money
        draw.drawOval(700, 100, 500, 500); //circle to roll big
        draw.drawOval(750, 150, 400, 400); //circle to roll small
        draw.setColor(new Color(0, 0, 0));
        draw.setFont(new Font("", 1, 75)); //g.setFont(new Font("", 1, 30));
        draw.drawString("Roll", 875, 375);
        draw.drawString("Clear", 855, 685);
        draw.drawString("Save", 15, 700); //(1280, 720);
        draw.setFont(new Font("", 1, 30));
        draw.drawString("Turns:", 675, 50);
        draw.drawString(""+player.getTurns(), 675, 100);
        draw.drawString("Money:", 1125, 50);
        draw.drawString(""+player.getMoney(), 1125, 100);
        draw.setFont(new Font("", 1, 10));

        // public boolean drawImage(Image img, int x, int y, int width,
        //          int height, ImageObserver observer) {
        //      return g2.drawImage(img, x, y, width, height, observer);
        //  };

    //     /**
    //   * Loads an image from the given file on disk and returns it
    //   * as an Image object.
    //   * @param filename name/path of the file to load
    //   * @return loaded image object
    //   * @throws NullPointerException if filename is null
    //   * @throws RuntimeException if the given file is not found
    //   */
    //  public Image loadImage(String filename) {

        // draw.drawImage(null, 0, 0, null)
    }

    public static void clear(Graphics draw) {
        draw.setColor(new Color(0, 175, 0)); //255, 255, 255// 0, 175, 0
        draw.fillRect(0, 0, 1280, 720);
        draw.setColor(new Color(0, 0, 0));
        draw.setFont(new Font("", 1, 10));
    }
}