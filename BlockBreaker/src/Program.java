import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

public class Program extends JFrame {
    GameBoard board;
    public Program() {

        board = new GameBoard();

        this.setLayout(new GridLayout(1, 1));
        add(board);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(false);
        show();

        board.start();
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        board.processKeyEvent(e);
    }

    public static void main(String[] args) {
        Program program = new Program();
    }

}
