import java.util.Scanner;

public class ConwayGame{
    private GridSpace[][] prevGrid;
    private GridSpace[][] grid;
    private GridSpace[][] nextGrid;
    private final int GRID_SIZE = 39;

    public ConwayGame(){
        prevGrid = new GridSpace[GRID_SIZE][GRID_SIZE];
        grid = new GridSpace[GRID_SIZE][GRID_SIZE];
        nextGrid = new GridSpace[GRID_SIZE][GRID_SIZE];
        resetGrid(prevGrid);
        resetGrid(grid);
        resetGrid(nextGrid);
    }

    public void flipGridSpace(int row, int col){
        grid[row][col].flipSpace();
    }
    
    public void resetGrid(GridSpace[][] curGrid){
        for(int row = 0; row < GRID_SIZE; row++){
            for(int col = 0; col < GRID_SIZE; col++){
                curGrid[row][col] = new GridSpace();
            }
        } 
    }

    public void setGrid(GridSpace[][] curGrid, GridSpace[][] newGrid){
        for(int row = 0; row < GRID_SIZE; row++){
            for(int col = 0; col < GRID_SIZE; col++){
                curGrid[row][col] = newGrid[row][col];
            }
        } 
    }

    public boolean compareGrid(GridSpace[][] grid1, GridSpace[][] grid2){
        boolean returnBool = true;

        for(int row = 0; row < grid1.length; row++){
            for(int col = 0; col < grid1.length; col++){
                if(grid1[row][col].isFilled() != grid2[row][col].isFilled()){
                    returnBool = false;
                    break;
                }
            }
        }

        return returnBool;
    }

    public void displayGrid(){
        System.out.print("\u000C"); // Clears the screen
        for(GridSpace[] row : grid){
            for(GridSpace space : row){
                System.out.print(space + " ");
            }
            System.out.print("\n");
        }
    }

    public void flipGrid(){
        for(GridSpace[] row : grid){
            for(GridSpace space : row){
                space.flipSpace();
            }
        }
    }

    public void flipGridRand(){
        for(GridSpace[] row : grid){
            for(GridSpace space : row){
                double randNum = Math.random();
                if(randNum > 0.8){
                    space.flipSpace();
                }
            }
        }
    }

    public void flipGridLinesHori(){
        int rowNum = 0;
        for(GridSpace[] row : grid){
            for(GridSpace space : row){
                if(rowNum % 2 != 0){
                    space.flipSpace();
                }    
            }
            rowNum++;
        }
    }

    public void flipGridLinesVert(){
        int num = 0;
        for(GridSpace[] row : grid){
            for(GridSpace space : row){
                if(num % 2 != 0){
                    space.flipSpace();
                }    
                num++;
            }
            num = 0;
        }
    }

    public void flipGridCheckers(){
        int num = 0;
        for(GridSpace[] row : grid){
            for(GridSpace space : row){
                if(num % 2 != 0){
                    space.flipSpace();
                }
                num++;
            }
        }
    }

    public int checkNeighbors(int row, int col){
        GridSpace top;
        GridSpace topLeft;
        GridSpace left;
        GridSpace bottomLeft;
        GridSpace bottom; 
        GridSpace bottomRight;
        GridSpace right;
        GridSpace topRight;
        // Check if there's a row above
        boolean isAbove = false;
        if(row > 0){
            isAbove = true;
        }
        // Check if there's a col to the left
        boolean isLeft = false;
        if(col > 0){
            isLeft = true;
        }
        // Check if there's a row below
        boolean isBelow = false;
        if(row < GRID_SIZE - 1){
            isBelow = true;
        }
        //Check if there's a col to the right
        boolean isRight = false;
        if(col < GRID_SIZE - 1){
            isRight = true;
        }

        if(isAbove){
            top = grid[row - 1][col];
            if(isLeft){
                topLeft = grid[row - 1][col - 1];
            }
            else {
                topLeft = null;
            }
            if(isRight){
                topRight = grid[row - 1][col + 1];
            }
            else {
                topRight = null;
            }
        }
        else {
            top = null;
            topLeft = null;
            topRight = null;
        }

        if(isLeft){
            left = grid[row][col - 1];
            if(isAbove){
                topLeft = grid[row - 1][col - 1];
            }
            else {
                topLeft = null;
            }
            if(isBelow){
                bottomLeft = grid[row + 1][col - 1];
            }
        }
        else {
            left = null;
            topLeft = null;
            bottomLeft = null;
        }

        if(isBelow){
            bottom = grid[row + 1][col];
            if(isLeft){
                bottomLeft = grid[row + 1][col - 1];
            }
            else {
                bottomLeft = null;
            }
            if(isRight){
                bottomRight = grid[row + 1][col + 1];
            }
            else {
                bottomRight = null;
            }
        }
        else {
            bottom = null;
            bottomLeft = null;
            bottomRight = null;
        }

        if(isRight){
            right = grid[row][col + 1];
            if(isAbove){
                topRight = grid[row - 1][col + 1];
            }
            else {
                topRight = null;
            }
            if(isBelow){
                bottomRight = grid[row + 1][col + 1];
            }
            else {
                bottomRight = null;
            }
        }
        else {
            right = null;
            topRight = null;
            bottomRight = null;
        }
        GridSpace[] gridArr = {topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight}; 
        Boolean[] boolArr = new Boolean[gridArr.length];
        for(int i = 0; i < gridArr.length; i++){
            if(gridArr[i] == null){
                boolArr[i] = null;
            }
            else {
                boolArr[i] = gridArr[i].isFilled();
            }
        }

        int totalTrue = 0;
        for(Boolean bool : boolArr){
            if(bool != null){
                if(bool == true){
                    totalTrue++;
                }
            }
        }
        return totalTrue;
    }

    public void setNextGridSpace(int row, int col){
        GridSpace curSpace = grid[row][col];
        int neighborCount = checkNeighbors(row, col);
        if(curSpace.isFilled()){
            if(neighborCount < 2){
                nextGrid[row][col].setSpace(false);
            }
            else if(neighborCount < 4){
                nextGrid[row][col].setSpace(true);
            }
            else {
                nextGrid[row][col].setSpace(false);
            }
        }
        else {
            if(neighborCount == 3){
                nextGrid[row][col].setSpace(true);
            }
        }
    }

    private static void wait(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void wait(double partOfSecond){
        try {
            Thread.sleep((int)(partOfSecond * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args){
        ConwayGame game = new ConwayGame();
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Conway's Game of Life! Would you like to manually input the grid, choose a preset grid, or randomly generate one?");
        System.out.println("Type input, preset, or random:");
        boolean correctInput = false;
        String temp = input.next();
        while(correctInput != true){
            if(temp.equals("input") || temp.equals("preset") || temp.equals("random")){
                correctInput = true;
            }
            else {
                System.out.println("Error: input must be the word input or random");
                System.out.println("Type input, preset, or random:");
                temp = input.next();
            }
        }
        if(temp.equals("input")){
            game.displayGrid();
            System.out.println("Type the coordinates of the square you want to change; the row number(0-38) first and the column number(0-38) after, entering after each one.");
            System.out.println("The square whose coordinates you enter will have its state flipped, so if it is filled in it will become empty and vice versa.");
            System.out.println("When you are ready to begin type -1 into the row and/or column.");
            boolean continueSelecting = true;
            correctInput = false;
            int curRow = 0;
            int curCol = 0;
            while(continueSelecting){
                correctInput = false;
                while(correctInput != true){
                    System.out.println("Type the row number(0-38):");
                    if(input.hasNextInt()){
                        int tempRow = input.nextInt();
                        if(tempRow > -1 && tempRow < 39){
                            correctInput = true;
                            curRow = tempRow;
                        }
                        else if(tempRow == -1){
                            continueSelecting = false;
                            correctInput = true;
                        }
                        else {
                            System.out.println("Error: input must be an integer between 0 and 38(including 0 and 38 specifically)");
                        }
                    }
                    else{
                        System.out.println("Error: input must be a number between 0 and 38(including 0 and 38 specifically)");
                        input.nextLine();
                    }
                }

                correctInput = false;
                while(correctInput != true){
                    System.out.println("Type the column number(0-38):");
                    if(input.hasNextInt()){
                        int tempCol = input.nextInt();
                        if(tempCol > -1 && tempCol < 39){
                            correctInput = true;
                            curCol = tempCol;
                        }
                        else if(tempCol == -1){
                            continueSelecting = false;
                            correctInput = true;
                        }
                        else {
                            System.out.println("Error: input must be an integer between 0 and 38(including 0 and 38 specifically)");
                        }
                    }
                    else{
                        System.out.println("Error: input must be a number between 0 and 38(including 0 and 38 specifically)");
                        input.nextLine();
                    }
                }
                if(continueSelecting){
                   game.flipGridSpace(curRow, curCol); 
                }
                game.displayGrid();
            }
        }
        else if(temp.equals("random")){
            game.flipGridRand();
        }
        else {
            System.out.println("Type a number to choose a preset grid:");
            System.out.println("1: Full grid");
            System.out.println("2: Checkerboard pattern");
            System.out.println("3: Horizontal Lines");
            System.out.println("4: Vertical Lines");
            System.out.println("5: R-Pentomino");
            correctInput = false;
            int selection = 0;
            while(correctInput != true){
                System.out.println("Type your selection(1-5): ");
                if(input.hasNextInt()){
                    int tempSelection = input.nextInt();
                    if(tempSelection > 0 && tempSelection < 6){
                        correctInput = true;
                        selection = tempSelection;
                    }
                    else {
                       System.out.println("Error: input must be an integer between 1 and 5"); 
                    }
                }
                else {
                    System.out.println("Error: input must be an integer between 1 and 5");
                    input.nextLine();
                }
            }
            switch(selection){
                case 1:
                    game.flipGrid();
                    break;
                case 2:
                    game.flipGridCheckers();
                    break;
                case 3:
                    game.flipGridLinesHori();
                    break;
                case 4:
                    game.flipGridLinesVert();
                    break;
                case 5:
                    game.flipGridSpace(18,19);
                    game.flipGridSpace(18,20);
                    game.flipGridSpace(19,18);
                    game.flipGridSpace(19,19);
                    game.flipGridSpace(20,19);
                    break;
            }
        }
        game.displayGrid();
        wait(1);
        boolean isStuck = false;
        int cyclesLasted = 0;
        while(!isStuck){
            for(int row = 0; row < game.GRID_SIZE; row++){
                for(int col = 0; col < game.GRID_SIZE; col++){
                    game.setNextGridSpace(row, col);
                }
            }
            if(game.compareGrid(game.prevGrid, game.nextGrid)){
                isStuck = true;
            }
            game.setGrid(game.prevGrid, game.grid);
            game.setGrid(game.grid, game.nextGrid);
            game.resetGrid(game.nextGrid);
            game.displayGrid();
            cyclesLasted++;
            wait(0.2);
        }
        System.out.println("Reached a stuck or looping state in " + cyclesLasted + " cycles");
    }
}

class GridSpace{
    private boolean isFilled;
    public GridSpace(){
        isFilled = false;
    }

    @Override
    public String toString(){
        if(isFilled){
            return "■";
        }
        return "□";
    }

    public boolean isFilled(){
        return isFilled;
    }

    public void flipSpace(){
        isFilled = !isFilled;
    }

    public void setSpace(boolean input){
        isFilled = input;
    }
}