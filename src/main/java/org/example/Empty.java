package org.example;

//Empty cell, neither mine nor next to a mine
public class Empty extends Cell{
    public Empty(int x, int y){
        super(x,y);
        super.c = 'E';
    }
}
