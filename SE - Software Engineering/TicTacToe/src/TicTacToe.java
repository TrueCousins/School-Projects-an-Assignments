import java.util.*;

public class TicTacToe {
    
    private char [][] board;
    private char currentPlayerMark;
    
    // Default contructor, creates board
    public TicTacToe()
    {
        board = new char[3][3];
        currentPlayerMark = 'X';
        initializeBoard();
        
    }
    
    // Creates a 3x3 array for TicTacToe
    public void initializeBoard()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                board[i][j] = '-';
            }
        }
    }
    
    public void printBoard()
    {
        int x = 0;
        
        for(int i = 0; i < 3; i++)
        {
            System.out.print("| ");
            
            for(int j = 0; j < 3; j++)
            {
                System.out.print(board[i][j] + " | ");
                //x++;
            }
       
            System.out.println();
           
        }
    }
    
    // Checks if the entire board is full
    public boolean isBoardFull()
    {
        boolean isFull = true;
        
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(board[i][j] == '-')
                {
                    isFull = false;
                }
            }
        }
        
        return isFull;
    }
    
    public boolean checkBox(int x, int y)
    {
        return board[x][y] == '-';
    }
    
    // Checks row and column of the board
    private boolean checkRowCol(char x, char y, char z)
    {
        return((x != '-' && (x == y) && (y == z)));
    }
    
    public boolean checkForWin()
    {
        return (checkRow() ||checkColumn() || checkDiagonal());
    }
    
    // Checks for 3 in a row for a winner
    private boolean checkRow()
    {
        for(int i = 0; i < 3; i++)
        {
            if(checkRowCol(board[i][0], board[i][1], board[i][2]) == true)
            {
                return true;
            }
        }
        return false;
    }
    
    // Checks for 3 in a colunmn for a winner
    private boolean checkColumn()
    {
        for(int i = 0 ; i < 3; i++)
        {
            if(checkRowCol(board[0][i], board[1][i], board[2][i]) == true)
            {
                return true;
            }
        }
        return false;
    }
    
    // Checks for a match in the diagonals
    private boolean checkDiagonal()
    {
        return((checkRowCol(board[0][0], board[1][1], board[2][2]) == true) || (checkRowCol(board[0][2], board [1][1], board[2][0]) == true));
    }
    
    // Changes player marks
    public void changePlayer()
    {
        if(currentPlayerMark == 'X')
        {
            currentPlayerMark = 'O';
        }
        else
            currentPlayerMark = 'X';
    }
    
    // Place mark on board
    public boolean placeMark(int row, int col)
    {
        if((row >= 0) && (row < 3))
        {
            if((col >= 0) && (col < 3))
            {
                if(board[row][col] == '-')
                {
                    board[row][col] = currentPlayerMark;
                    return true;
                }
            }
        }
        return false;
    }
           
    
    public static void main(String[] args) {
        
        
        TicTacToe game = new TicTacToe();
        Scanner pInput = new Scanner(System.in);
       
        // Used to store players' input
        int pRow, pCol;   
        
        do
        {
            System.out.println("Player " + game.currentPlayerMark + ", Enter row number(1, 2, or 3) and column number (1, 2, or 3): ");
            pRow = pInput.nextInt() -  1;
            pCol = pInput.nextInt() - 1;
            
            // Input validation
            while((pRow < 0 || pRow >= 3) || (pCol < 0 || pCol >= 3) || game.checkBox(pRow, pCol) == false)
            {
                System.out.println("This is invalid. Please enter a row number from 1 to 3 and a column number from 1 to 3: ");
                pRow = pInput.nextInt() - 1;
                pCol = pInput.nextInt() - 1;
            }
            
            
            game.placeMark(pRow, pCol);   // Places the players mark down on the board
            game.printBoard();            // Prints the board
           
            // If a player wins end the game, else change players
            if((game.checkForWin() == true) && (game.currentPlayerMark == 'X'))
            {
                System.out.println("Player X wins!");
                break;
            }
            else if((game.checkForWin() == true) && (game.currentPlayerMark == 'O'))
            {
                System.out.println("Player O wins!");
                break;
            }
            else
            {
                game.changePlayer();
            }
            
            
        }while(game.isBoardFull() == false);
        
        if(game.isBoardFull() == true && game.checkForWin() == false)
        {
            System.out.println("Tie Game!");
        }
        
        /*
        do
        {
            System.out.println("Player 1(X), Enter row number(1, 2, or 3) and column number (1, 2, or 3): ");
            pRow = pInput.nextInt() -  1;
            pCol = pInput.nextInt() - 1;
            
            // Input validation
            while((pRow < 0 || pRow >= 3) || (pCol < 0 || pCol >= 3))
            {
                System.out.println("This is an invalid number. Please enter a row number from 1 to 3 and a column number from 1 to 3: ");
                pRow = pInput.nextInt() - 1;
                pCol = pInput.nextInt() - 1;
            }
            
            while(game.checkBox(pRow,pCol) == false)
            {
                System.out.println("This box is full. Please try again: ");
                pRow = pInput.nextInt() - 1;
                pCol = pInput.nextInt() - 1;
            }
            
            game.placeMark(pRow, pCol);   // Places the players mark down on the board
            game.printBoard();              // Prints the board
            game.changePlayer();            // Changes mark to 'O' for Player 2's turn
            
            // Break if player 1 has won
            if(game.checkForWin() == true)
            {
                System.out.println("Player 1 wins!");
                break;
            }
            // break if no more marks can be placed
            if(game.isBoardFull() == true)
            {
                break;
            }

            //-------------------------------------------------------------------------------------------------
            
            // Player 2's turn
            System.out.println("Player 2(O), Enter row number(1, 2, or 3) and column number (1, 2, or 3): ");
            pRow = pInput.nextInt() - 1;
            pCol = pInput.nextInt() - 1;
            
            // Input validation
            while((pRow < 0 || pRow >= 3) || (pCol < 0 || pCol >= 3))
            {
                System.out.println("This is an invalid number. Please enter a row number from 1 to 3 and a column number from 1 to 3: ");
                pRow = pInput.nextInt() - 1;
                pCol = pInput.nextInt() - 1;
            }
            
            while(game.checkBox(pRow,pCol) == false)
            {
                System.out.println("This box is full. Please try again: ");
                pRow = pInput.nextInt() - 1;
                pCol = pInput.nextInt() - 1;
            }
        
        
            game.placeMark(pRow, pCol);
            game.printBoard();
            game.changePlayer();

            if(game.checkForWin() == true)
            {
                System.out.println("Player 2 wins!");
                break;
            }
            
            }while(game.isBoardFull() == false);

            System.out.println();
            if(game.isBoardFull() == true && game.checkForWin() == false)
            {
                System.out.println("Tie Game!");
            }
                */
    }
    
}
