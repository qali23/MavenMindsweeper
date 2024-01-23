package org.example;

//Cell abstract class, each cell will be a type of cell
abstract public class Cell {
    public int row;//X position
    public int column;//Y position
    public char c;//Identifier - label
    public boolean state_hidden = true;//Is this cell hidden from the user

    public Cell(int x , int y){
        this.row = x;
        this.column = y;
    }





}
