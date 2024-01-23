package org.example;

//Next to bomb is a cell that is next to at least 1 bomb
public class Next_to_bomb extends Cell{
    public int num_bombs_next_to = 1;//How many bombs it is next to
    public Next_to_bomb(int x, int y){
        super(x,y);
        super.c = 'N';
    }

    public void increment_num_bombs_next_to(){
        this.num_bombs_next_to++;//Increment the number of bombs
    }
}
