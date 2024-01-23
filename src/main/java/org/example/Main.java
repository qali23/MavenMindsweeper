package org.example;
import java.util.Random;

/*Potential Improvements
- Make Buttons an attribute of each cell: However, I cannot find a way
  to identify the cell that the button is part of when it is pressed
  which is why I used a dictionary

- All cells have a 'num_bombs_next_to' attribute, empty cells just
  have 0 in it - could be easier to work with

- Visuals: changing font colours for bombs etc....

_ Responsiveness, the more repetitive code, such as update grid can
  make clicks less responsive, avoid adding to update grid function.

- Rule list for new players

- User-selected difficulty

- Restart feature
 */

public class Main {
    public static void init_Empty_Grid(Cell[][] grid, int grid_size){
        //Initialise grid of empty cells
        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                grid[i][j] = new Empty(i,j);
            }
        }
    }

    public static void init_Bombs_into_grid(Cell[][] grid, int num_bombs, int grid_size){
        Random rand = new Random();
        //Initialise Bombs into grid
        int x,y;
        for (int i =0 ; i<num_bombs; i++){
            x = rand.nextInt(grid_size);
            y = rand.nextInt(grid_size);
            if(grid[x][y] instanceof Bomb){
                i--;
                continue;
            }
            else{
                grid[x][y] = new Bomb(x,y);
            }

            //Update Adjacent cells of bomb, increase the number of bombs they are next to
            int x_non_min = (x>0)?1:0;
            int x_not_max = (x<grid_size-1)?1:0;
            int y_not_min = (y>0)?1:0;
            int y_not_max = (y<grid_size-1)?1:0;

            for (int a=x-x_non_min ; a<=x+x_not_max ; a++){
                for (int b=y-y_not_min; b<=y+y_not_max ; b++){
                    if (grid[a][b] instanceof Next_to_bomb){
                        Next_to_bomb N = (Next_to_bomb) grid[a][b];
                        N.increment_num_bombs_next_to();
                        grid[a][b] = N;
                    }
                    else if (grid[a][b] instanceof Empty) {
                        grid[a][b] = new Next_to_bomb(a,b);
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        int grid_size = 15;
        int num_bombs = 10;

        Cell[][] grid = new Cell[grid_size][grid_size];

        init_Empty_Grid(grid, grid_size);

        init_Bombs_into_grid(grid, num_bombs, grid_size);

        GUI m = new GUI(grid,grid_size);
    }
}