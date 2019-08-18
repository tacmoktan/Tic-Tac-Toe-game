package tictactoe;

/**
 *
 * @author Tashi
 */
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//class import
import tictactoe.TicTacToeFrame;

public class TicTacToe {

    public static void main(String[] args) {

        int row = 3;
        int col = 3;
        String matrix[][] = new String[row][col];
        //initialize matrix
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = ".";
            }
        }

        JFrame f = new JFrame("TicTacToe Game by Tashi");
        f.setLayout(new GridLayout(4, 3));

        JLabel resultLabel = new JLabel("Result:", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Gill Sans MT", Font.BOLD, 20));

        JLabel resultDisplay = new JLabel("", SwingConstants.CENTER);
        resultDisplay.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));

        //creating boxes 
        TicTacToeFrame button;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                button = new TicTacToeFrame(i, j, matrix, resultDisplay, f);
                //styling buttons
                button.setBackground(Color.decode("#000000"));
                button.setForeground(Color.decode("#ffffff"));
                button.setFont(new Font("Gill Sans MT", Font.PLAIN, 25));
                button.setFocusPainted(false);          //removes focus(bordered line) from symbols
                
                f.add(button);
            }
        }

        //adding JLabel components to the frame after 9 boxes
        f.add(resultLabel);
        f.add(resultDisplay);

        f.setVisible(true);

    }
}
