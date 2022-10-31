
/*
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                                Breakout game
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    There are boxes on the top of the screen that need to be taken out by a couple of balls that travels across. You control a paddle at the bottom of the board that you can bounce
    the balls with. When the ball hits a box it either removes it or lowers the health of the block.
    The different blocks are:
        -Red: Standard
        -Green: 1 more health
    This version of breakout launches a ball every 5 seconds until the supply of balls is out. You have a supply of 5 balls
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
*/

import javax.naming.Name;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math;
import java.awt.Font;
import java.util.Scanner;

public class Game {
    // Constants
    private static final int NUMBER_OF_ROWS = 10;
    private static final int NUMBER_OF_COLS = 5;
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 800;
    private static final int BLOCK_WIDTH = WINDOW_WIDTH / NUMBER_OF_COLS;
    private static final int BLOCK_HEIGHT = 20;
    private static final int LEVELMAX =5;
    /*------------
         Arrays
    ------------*/
    // Objects in game
    private final ArrayList<ColoredBox> collectionofboxes;
    private final ArrayList<Ball> collectionofballs;
    // High-score data
    private final ArrayList<Integer> highscores;
    private final ArrayList<Integer> highscores_unsorted;
    private final ArrayList<String> Names;
    private String initialer;
    // Player
    private final Player player;
    //Variables
    int score;
    int level;
    int timer;
    int sectimer;
    int ballsleft;
    //Booleans
    boolean lost;
    boolean won;
    boolean running=true;

    /*
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                                                   Start of game and restart
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   */
    public Game(GameBoard board) {
        player = new Player(WINDOW_WIDTH / 2);
        highscores = new ArrayList<>();
        highscores_unsorted = new ArrayList<>();
        collectionofboxes = new ArrayList<>();
        collectionofballs = new ArrayList<>();
        Names = new ArrayList<>();
        board.getHeight();
        initialer = "";
        StartGame();

    }
    public void StartGame(){
        Label();
        timer=0;
        player.setWidth(player.getWidth() + 50*(level-1));
        player.setSpeed(player.getSpeed() - 2*(level-1));
        MakeBall();
        MakeBoxes();
        score=0;
        level=1;
        sectimer=5;
        ballsleft=5;
        lost=false;
        won=false;
        running=true;
    }
    public void Label(){
        String tmp = JOptionPane.showInputDialog(null, "Skriv dina initialer");
        if(tmp.length() < 4) {
            initialer = tmp;
        } else{
            while(tmp.length()>=4){
                tmp = JOptionPane.showInputDialog(null,"That is not valid");
                if(tmp.length()<4){
                    initialer = tmp;
                }

            }
        }


    }
    /*
   ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                                            Functions used at start and update
   ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    */
    //Add a new ball
    public void MakeBall() {
        collectionofballs.add(new Ball(player.getX() + player.getWidth() / 2, 500, 20, 20));
    }

    //Make boxes
    public void MakeBoxes() {
        //Creating the row of boxes
        for (int j = 0; j < NUMBER_OF_ROWS - 1; j++) {
            //Creating the colums of boxes
            for (int i = 0; i < NUMBER_OF_COLS - 1; i++) {
                //Randomises whether the box is green or red
                double k = Math.random();
                if (k > 0.9) {
                    // Adds a green boc

                    collectionofboxes.add(new GreenBox(i * BLOCK_WIDTH + NUMBER_OF_ROWS * i + NUMBER_OF_ROWS, j * BLOCK_HEIGHT + NUMBER_OF_COLS * j + 10, BLOCK_WIDTH, BLOCK_HEIGHT));
                } else if(k>0.1){
                    // Adds a red box
                    collectionofboxes.add(new RedBox(i * BLOCK_WIDTH + NUMBER_OF_ROWS * i + NUMBER_OF_ROWS, j * BLOCK_HEIGHT + NUMBER_OF_COLS * j + 10, BLOCK_WIDTH, BLOCK_HEIGHT));
                    //Theres a chance that no boxes will form, because it will be a more interesting form
                }
            }
        }
    }

     /*
    ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                                            Complete Update procedure
    ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

    //Changes to the code
    public void update(Keyboard keyboard) {
        //Updating the paddle
        player.update(keyboard);
        //Updating the balls
        for (Ball ball : collectionofballs) {
            ball.update(keyboard);
        }
        //Updating the boxes
        for (ColoredBox coloredbox : collectionofboxes) {
            coloredbox.update(keyboard);
        }
        //Timer procedures
        timer++;
        //Lowers the displayed time every second
        if (timer % 40 == 0) {
            sectimer--;
        }
        //Every 5 seconds makes a new ball if they are left
        if (timer == 200 && running) {
            //Makes a new ball at the paddle
            MakeBall();
            //Resets the displayed timer
            sectimer = 5;
            //Lowers the amount of balls left
            ballsleft -= 1;
            //Resets the timer if their are balls left
            if (ballsleft != 0) {
                timer = 0;
            }
        }
        // Collision procedures
        if (running) {
            collision(keyboard);
            //If the game has ended
        } else{
            exit(keyboard);
        }
    }
    //If collision happens
    public void collision(Keyboard keyboard) {
        for (Ball ball : collectionofballs) {
            //Creates a rectangle around the ball for collision detection
            Rectangle BallRect = new Rectangle(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
            for (ColoredBox coloredBox : collectionofboxes) {
                //Creates lines around each box for collision detection
                Rectangle RightBoxRect = new Rectangle(coloredBox.getX(), coloredBox.getY(), 1, coloredBox.getHeight());
                Rectangle LeftBoxRect = new Rectangle(coloredBox.getX() - coloredBox.getWidth(), coloredBox.getY(), 1, coloredBox.getHeight());
                Rectangle UpperBoxRect = new Rectangle(coloredBox.getX(), coloredBox.getY(), coloredBox.getWidth(), 1);
                Rectangle DownBoxRect = new Rectangle(coloredBox.getX(), coloredBox.getY() + coloredBox.getHeight(), coloredBox.getWidth(), 1);
                //Figure out if a collision happens
                if (BallRect.intersects(UpperBoxRect) || BallRect.intersects(DownBoxRect)) {
                    //Bounces the ball
                    ball.setVelY(-ball.getVelY());
                    //Lowers the health of the block and adds score
                    CollisionEffect(coloredBox);
                }
                if (BallRect.intersects(RightBoxRect) || BallRect.intersects(LeftBoxRect)) {
                    //Bounces the ball
                    ball.setVelX(-ball.getVelX());
                    //Lowers the health of the block and adds score
                    CollisionEffect(coloredBox);
                }
            }
            //Creates a rectangle around the paddle for collision detection
            Rectangle Paddle = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
            //Figure out if a collision happens
            if (BallRect.intersects(Paddle)) {
                //Bounces the ball
                ball.setVelY(-ball.getVelY());
            }
        }
        //Removes a box if its out of health

        collectionofboxes.removeIf(coloredBox -> coloredBox.getHealth() <= 0);
        //Deletes a ball if it goes bellow the window
        collectionofballs.removeIf(ball -> ball.getY() >= WINDOW_HEIGHT);
        //Figures out if your balls are all gone
        if (collectionofballs.size() == 0) {
            lost = true;
            running=false;
            ending();
        }
        //Figures out if all of the boxes are gone
        if (collectionofboxes.size() == 0) {
            //Changes level
            MakeBoxes();
            level++;
            //What happens if you win
            if(level>=LEVELMAX){
                won = true;
                running=false;
                ending();
            }
            //Changes the width and speed of the paddle
            player.setWidth(player.getWidth() - 50);
            player.setSpeed(player.getSpeed() + 2);

        }
    }
    public void highscoreadd(){
        highscores_unsorted.add(score);
        highscores.add(score);
        Names.add(initialer);
    }
    public void ending(){
        highscoreadd();
        highscores.sort(Collections.reverseOrder());
        if(won){
            for (int i = 0; i < collectionofballs.size(); i++) {
                collectionofballs.remove(i);
            }
        } else{
            for (int i = 0; i < collectionofboxes.size(); i++) {
                collectionofboxes.remove(i);
            }
        }
    }

    //Lowers health on a box and adds score
    public void CollisionEffect(ColoredBox Box) {
        Box.setHealth(Box.getHealth() - 1);
        if (Box.getColor() == Color.green) {
            score += 2;
        } else {
            score += 1;
        }
    }

    //Exiting procedure
    public void exit(Keyboard keyboard) {
        if(keyboard.isKeyDown(Key.Enter)){
            StartGame();
        }
        if(keyboard.isKeyDown(Key.Escape)){
            System.exit(0);
        }
    }
    /*
    ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                                                        Complete Draw procedure
    ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

    //Writing procedure of the side text
    public void write(String text, String num, int pos, Graphics2D graphics) {
        pos--;
        int textpos = pos * 60;
        //Draws box with info
        graphics.drawString(text, 700, 20 + textpos);
        graphics.drawString(num, 700, 50 + textpos);
        graphics.drawRect(690, textpos, 110, 60);
    }

    //Writes end screen
    public void EndWrite(String state, Graphics2D graphics) {
        //Gets a string of the integers
        String ScoreString = Integer.toString(score);
        String LevelString = Integer.toString(level);
        //Sets font of the end screen
        graphics.setFont(new Font("Serif", Font.BOLD, 50));
        //Drawing procedure
        graphics.drawString(state, 200, 100);
        graphics.drawString("To restart click 'ENTER'",200,200);
        graphics.drawString("To shut off click 'ESCAPE'",200,300);
        graphics.drawString("Score = " + ScoreString, 200, 400);
        graphics.drawString("Level = " + LevelString, 200, 500);
        graphics.setFont(new Font("Serif", Font.BOLD, 15));
        int SpaceLeft=9;
        if(highscores.size()<=10){
            SpaceLeft-=highscores.size();
        } else{
            SpaceLeft=0;
        }
        if(highscores.size()>=1){
            graphics.drawString("Highscores:",20,20);
            graphics.drawRect(0, 0, 110, 600);
            for (int i = 0; i < 9-SpaceLeft; i++) {
                String Score = Integer.toString(highscores.get(i));
                graphics.drawString(Score, 70, 60+30 * i);
            }
            final ArrayList<Integer> takenJ;
            final ArrayList<Integer> takenI;
            takenJ = new ArrayList<>();
            takenI = new ArrayList<>();
            for (int i = 0; i < highscores.size(); i++) {
                for (int j = 0; j < highscores.size(); j++) {
                    if(!takenI.contains(i)&&!takenJ.contains(j)){
                        if(highscores.get(i)==highscores_unsorted.get(j)){
                            graphics.drawString(Names.get(j)+":",20,60+30*i);
                            takenJ.add(j);
                            takenI.add(i);

                        }
                    }

                }
            }
            //Latest runs
            int size=highscores_unsorted.size();
            graphics.drawRect(450,330,300,300);
            int NumberOfScores=3;
            if(highscores.size()<3){
                NumberOfScores=highscores.size();
            }
            graphics.drawString("Latest runs:",480,350);
            for (int i = 0; i < NumberOfScores; i++) {
                graphics.drawString(Names.get(size-i-1)+":",480,380+i*30);
                ScoreString=Integer.toString(highscores_unsorted.get(size-i-1));
                graphics.drawString(ScoreString,530,380+i*30);
            }
        }
    }
    //Drawing of the screen
    public void draw(Graphics2D graphics) {
        //Gets a string of the integers
        String ScoreString = Integer.toString(score);
        String LevelString = Integer.toString(level);
        String SecTimerString = Integer.toString(sectimer);
        String BallsLeftString = Integer.toString(ballsleft);
        //Background
        graphics.setColor(Color.darkGray);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            //namedraw(graphics);
            //If you lose
        if(lost){
            graphics.setColor(Color.white);
            EndWrite("You lose!", graphics);
            //If you win
        } else if (won) {
            //If you have a perfect win (no balls lost)
            if (collectionofballs.size() == 5) {
                Color gold = new Color(255, 223, 0);
                graphics.setColor(gold);
                EndWrite("Perfect Game!", graphics);
                //If you don't have a perfect win
            } else {
                graphics.setColor(Color.white);
                EndWrite("You win!", graphics);
            }
            //If you are still playing
        }else {
            //Writes the aside
            graphics.setColor(Color.white);
            graphics.setFont(new Font("Serif", Font.BOLD, 15));
            write("Score = ", ScoreString, 1, graphics);
            write("Level = ", LevelString, 2, graphics);
            write("Balls = ", BallsLeftString, 3, graphics);
            //Writes the counting part of the aside
            if (ballsleft > 0) {
                graphics.setColor(Color.red);
                write("New: ", SecTimerString, 4, graphics);
            }
            //Draws the balls
            for (Ball ball : collectionofballs) {
                ball.draw(graphics);
            }
            //Draws the boxes
            for (ColoredBox coloredbox : collectionofboxes) {
                coloredbox.draw(graphics);
            }
            //Draws the player
            player.draw(graphics);
        }
    }
}
/*
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                                               Creation info
    Axel Flyckt
    2021-03-02
 */