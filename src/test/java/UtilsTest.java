//package org.example;
import org.example.Bomb;
import org.example.Cell;
import org.example.Empty;
import org.junit.jupiter.api.Test;

import static org.example.GUI.cell_revealer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    public void testBombInit(){
        Bomb b = new Bomb(1,2);
        assertEquals(b.row, 1, "THE X COORDINATE IS NOT WORKING");
    }

    @Test
    public void testAssertEqualsGrid(){
        int grid_size = 10;
        Cell[][] grid = new Cell[grid_size][grid_size];
        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                grid[i][j] = new Empty(i,j);
            }
        }

        Cell c = new Bomb(1,1);
        Cell d = new Empty(1,1);
        assertNotEquals(c,d,"Assert not equals cannot distinguish between different types of cells");

        Cell f = new Empty(1,1);
        d.state_hidden = false;
        assertNotEquals(d.state_hidden,f.state_hidden, "Assert not equals can't distinguish between cell attributes even when specifically selected.");

        Cell[][] grid2 = new Cell[grid_size][grid_size];
        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                grid2[i][j] = new Empty(i,j);
                grid2[i][j].state_hidden = false;
            }
        }
        assertNotEquals(grid2, grid, "Assert Equals cannot distinguish between Grids1");

        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                grid2[i][j].state_hidden = true;
            }
        }
        assertEquals(grid2, grid, "Assert Equals cannot compare Grids");

        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                grid2[i][j].c = 'a';
            }
        }
        assertNotEquals(grid2, grid, "Assert Equals cannot distinguish between Grids2");

        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                grid2[i][j].c = 'E';
            }
        }
        assertEquals(grid2, grid, "Assert Equals cannot compare Grids");

    }

    @Test
    public void testCellRevealerEmpty(){
        int grid_size = 10;
        Cell[][] grid = new Cell[grid_size][grid_size];
        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                grid[i][j] = new Empty(i,j);
            }
        }

        Cell[][] grid2 = grid;
        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                grid2[i][j].state_hidden = true;
            }
        }
        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                cell_revealer(grid, i , j, grid_size);
                assertEquals(grid2, grid, "Cell "+ i + ","+ j+ " does not reveal entire grid.");
            }
        }

    }
    @Test
    public void testCellRevealerAllBombs(){
        int grid_size = 10;
        Cell[][] grid = new Cell[grid_size][grid_size];
        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                grid[i][j] = new Bomb(i,j);
            }
        }

        Cell[][] grid2 = grid;

        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                cell_revealer(grid, i , j, grid_size);
                assertEquals(grid2, grid, "Cell "+ i + ","+ j+ " does not reveal entire grid.");
            }
        }
    }
}
