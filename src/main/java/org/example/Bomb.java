package org.example;

//Bomb cell with the mine in it
public class Bomb extends Cell{
    public Bomb(int x, int y){
        super(x,y);
        super.c = 'B';
    }
}
