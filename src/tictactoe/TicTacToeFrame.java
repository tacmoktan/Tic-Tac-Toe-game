package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Tashi
 */
class TicTacToeFrame extends JButton {

    static String[][] fmatrix;  //static keeps the updated value stored
    int fx;
    int fy;

    static int counter = 0; //static keeps the counter value updated(incremented) rather than initializing everytime to 0

    //global variables to determine winner
    int H_win = 3;                                      //Horizontal winning possibilties
    int V_win = 3;                                      //Vertical winning possibilities
    int win_marks = 3;                                  //concurrent 3 marks on a row i.e. X or O;
    int[] countHorizontalMarks = new int[H_win];        //to store counted marks for each possibility
    int[] countVerticalMarks = new int[V_win];
    int countRightDiagonalMarks = 0;
    int countLeftDiagonalMarks = 0;
    static int countWinResult = 0;    //on 1, it indicates win by exactly one possibility out of 8.

    public TicTacToeFrame(int r, int c, String[][] matrix, JLabel resultDisplay, JFrame f) {
        this.fmatrix = matrix;
        this.fx = r;        //row index
        this.fy = c;        //col index

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // logic to fill up boxes with symbols on every click
                if (fmatrix[fx][fy] == "X") {
                    setText("X");
                } else if (fmatrix[fx][fy] == "O") {
                    setText("O");
                } else {
                    if (counter % 2 == 0) {
                        fmatrix[fx][fy] = "X";
                    } else {
                        fmatrix[fx][fy] = "O";
                    }
                    setText(fmatrix[fx][fy]);
                    counter++;
                }

                // logic to check results on every click
                String horizontalResult = checkHorizontalMarks(matrix, "X");
                if (horizontalResult.isEmpty()) {
                    horizontalResult = checkHorizontalMarks(matrix, "O");
                }

                String verticalResult = checkVerticalMarks(matrix, "X");
                if (verticalResult.isEmpty()) {
                    verticalResult = checkVerticalMarks(matrix, "O");
                }

                String rightDiagonalResult = checkRightDiagonalMarks(matrix, "X");
                if (rightDiagonalResult.isEmpty()) {
                    rightDiagonalResult = checkRightDiagonalMarks(matrix, "Y");
                }

                String leftDiagonalResult = checkLeftDiagonalMarks(matrix, "X");
                if (leftDiagonalResult.isEmpty()) {
                    leftDiagonalResult = checkLeftDiagonalMarks(matrix, "O");
                }

                String winResult = checkResultEmpty(horizontalResult, verticalResult, rightDiagonalResult, leftDiagonalResult);
                String finalResult = "";

                if (winResult.isEmpty()) {
                    if (counter == 9) {             //all 9 boxes filled but no wins
                        finalResult = "Draw";
                        disposeFrame(5000, f);      //delays 4s and disposes frame
                    }
                } else {
                    finalResult = winResult;
                    countWinResult = 1;
                }

                resultDisplay.setText(finalResult);

                if (countWinResult == 1) {
                    f.setEnabled(false);            //makes whole frame unclickable after win
                    disposeFrame(5000, f);
                }
            }
        });
    }

    public void disposeFrame(int delay, JFrame f) {
        Timer timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });
        timer.start();
    }

    //check horizontal number of marks
    public String checkHorizontalMarks(String[][] matrix, String symbol) {
        String horizontalResult = "";
        for (int i = 0; i < 3; i++) {
            countHorizontalMarks[i] = 0;                //initialize counter for each horizontal win possibility
            for (int j = 0; j < 3; j++) {

                if (i == 0) {
                    if (matrix[i][j].equals(symbol)) {
                        countHorizontalMarks[i] += 1;
                    }
                }

                if (i == 1) {
                    if (matrix[1][j].equals(symbol)) {
                        countHorizontalMarks[i] += 1;
                    }
                }

                if (i == 2) {
                    if (matrix[2][j].equals(symbol)) {
                        countHorizontalMarks[i] += 1;
                    }
                }

            }
            horizontalResult = checkMarksGetResult(countHorizontalMarks[i], symbol, "horizontal strike");
            if (!horizontalResult.isEmpty()) {
                break;
            }
        }
        return horizontalResult;
    }

    //check vertical win possibilities
    public String checkVerticalMarks(String[][] matrix, String symbol) {
        String verticalResult = "";
        for (int i = 0; i < 3; i++) {
            countVerticalMarks[i] = 0;          //initialize counter for each vertical win possibility
            for (int j = 0; j < 3; j++) {

                if (i == 0) {
                    if (matrix[j][i].equals(symbol)) {
                        countVerticalMarks[i] += 1;
                    }
                }

                if (i == 1) {
                    if (matrix[j][i].equals(symbol)) {
                        countVerticalMarks[i] += 1;
                    }
                }

                if (i == 2) {
                    if (matrix[j][i].equals(symbol)) {
                        countVerticalMarks[i] += 1;
                    }
                }

            }
            verticalResult = checkMarksGetResult(countVerticalMarks[i], symbol, "vertical strike");
            if (!verticalResult.isEmpty()) {
                break;
            }
        }
        return verticalResult;
    }

    //check diagonal win possibilities
    //right diagonal
    public String checkRightDiagonalMarks(String[][] matrix, String symbol) {
        countRightDiagonalMarks = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    if (matrix[i][j].equals(symbol)) {
                        countRightDiagonalMarks++;
                    }
                }
            }
        }
        return checkMarksGetResult(countRightDiagonalMarks, symbol, "diagonal strike (right)");
    }

    //left diagonal
    public String checkLeftDiagonalMarks(String[][] matrix, String symbol) {
        countLeftDiagonalMarks = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 2 - i; j >= 0; j--) {

                if (matrix[i][j].equals(symbol)) {
                    countLeftDiagonalMarks++;
                }
                break;
            }
        }
        return checkMarksGetResult(countLeftDiagonalMarks, symbol, "diagonal strike (left)");
    }

    //check Marks Get Result
    public String checkMarksGetResult(int marksCount, String symbol, String winType) {
        String marksResult = "";
        if (marksCount == 3) {
            if (symbol.equals("X")) {
                marksResult = "P1 wins: " + winType;
            } else {
                marksResult = "P2 wins: " + winType;
            }
        }
        return marksResult;
    }

    public String checkResultEmpty(String H_result, String V_result, String RD_result, String LD_result) {
        if (!H_result.isEmpty()) {
            return H_result;
        } else if (!V_result.isEmpty()) {
            return V_result;
        } else if (!RD_result.isEmpty()) {
            return RD_result;
        } else if (!LD_result.isEmpty()) {
            return LD_result;
        } else {
            return "";
        }
    }
}
