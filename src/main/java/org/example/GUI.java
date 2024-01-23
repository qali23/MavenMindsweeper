package org.example;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Dictionary;
import java.util.Hashtable;

public class GUI extends Frame{

    Panel top = new Panel();//For top of GUI
    Panel p = new Panel();//Centre of GUI
    private Dictionary<Button, Cell> button_cell = new Hashtable<>();//To connect each button to a unique cell

    //Globally accessible variables
    int grid_size;
    Cell[][] grid;
    Button[][] button_grid;
    Label label1 = new Label("WELCOMEEEEEEEEEEEEEEEEEEEEEEEEEE TO MINDSWEEPER!");

    //Aesthetics
    Color red = new Color(200, 0, 0);
    Color green = new Color(0, 200, 0);
    Color blue = new Color(0,200,200);
    Color yellow = new Color(200,200,0);
    Color dark_grey = new Color(64, 64,64);
    Color white = new Color(255, 255, 255);


    //How many turns
    int counter = 0;


    public static void print_grid(Cell[][] grid, int grid_size){
        //Prints the grid to console for debugging: is the GUI the same as it...?
        for (int i =0; i<grid_size; i++){
            String line = "";
            for (int j =0; j<grid_size; j++){
                if(grid[i][j].c == 'O'){
                    line+= 'O';
                }
                else if (grid[i][j].state_hidden){
                    line+= "X";
                }
                else if(grid[i][j] instanceof Next_to_bomb){
                    line+= ((Next_to_bomb) grid[i][j]).num_bombs_next_to;
                }else{
                    line += grid[i][j].c;
                }
                line += " ";
            }
            System.out.println(line);
        }
        System.out.println("\n");
    }


    public void make_Buttons(){
        //Iterate through grid to make a button for each cell
        for (int i =0; i<grid_size;i++){
            for (int j=0; j<grid_size; j++){
                String label_for_button = "";
                if (grid[i][j].state_hidden){
                    label_for_button = "X";
                }
                else if(grid[i][j] instanceof Next_to_bomb){
                    label_for_button += ((Next_to_bomb) grid[i][j]).num_bombs_next_to;
                }
                else{
                    label_for_button += grid[i][j].c;
                }
                button_grid[i][j] = new Button(label_for_button);
                button_grid[i][j].setBackground(white);
                this.button_cell.put(button_grid[i][j], grid[i][j]);
                p.add(button_grid[i][j]);
                button_grid[i][j].addMouseListener(mouseAdapter);
            }
        }
    }


    public void update_button(int i, int j){
        if (!grid[i][j].state_hidden) {
            if (grid[i][j] instanceof Empty) {
                button_grid[i][j].setLabel("" + grid[i][j].c);
                button_grid[i][j].setEnabled(false);
            }
            else if (grid[i][j] instanceof Next_to_bomb) {
                button_grid[i][j].setLabel(""+((Next_to_bomb) grid[i][j]).num_bombs_next_to);
                button_grid[i][j].setEnabled(false);
            }
            button_grid[i][j].setBackground((grid[i][j] instanceof Next_to_bomb)? yellow:(grid[i][j] instanceof Empty)? blue: red);
        }
        else{
            if (grid[i][j].c == 'O'){
                button_grid[i][j].setLabel("O");
                button_grid[i][j].setBackground(dark_grey);
            }
            else{
                button_grid[i][j].setLabel("X");
                button_grid[i][j].setBackground(white);
            }
        }
    }


    public void victory() throws InterruptedException {
        for(int i=0; i<grid_size;i++){
            for (int j=0; j<grid_size; j++){
                button_grid[i][j].setEnabled(false);
                button_grid[i][j].setBackground((grid[i][j] instanceof Bomb)? dark_grey: (grid[i][j] instanceof Next_to_bomb)? yellow: blue);
            }
        }
        //Victory message
        label1.setText("MOST IMPRESSIVE, YOU HAVE ACQUIRED victory, BUT you ArE not A jedi YeT.");
        label1.setBackground(green);
        Thread.sleep(7000);
        label1.setText("Total Turns: "+ counter);
    }


    public void update_grid(Cell[][] grid, Button[][] button_grid) throws InterruptedException {
        //Updates the GUI
        counter++;
        label1.setText("Number of turns: " + counter);
        int count = 0;
        for (int i= 0; i< grid_size; i++){
            for (int j = 0; j< grid_size; j++){
                update_button(i,j);

                if (grid[i][j] instanceof Bomb){
                    count--;
                }

                if (grid[i][j].state_hidden){
                    count++;
                }
            }
        }

        if (count == 0){//When number of hidden cells = number of bombs
            victory();
        }
    }


    public static void cell_revealer(Cell[][] grid, int x, int y, int grid_size){
        //Selecting which cells must be revealed when the user selects a cell
        Cell xy = grid[x][y];

        if (xy instanceof Empty &&
                xy.state_hidden){
            grid[x][y].state_hidden = false;

            //Check adjacent cells
            int x_not_min = (x>0)?1:0;
            int x_not_max = (x<grid_size-1)?1:0;
            int y_not_min = (y>0)?1:0;
            int y_not_max = (y<grid_size-1)?1:0;

            for (int a= x - x_not_min ; a<= x + x_not_max ; a++){
                for (int b= y - y_not_min; b<= y + y_not_max ; b++){
                    if(a!=x || b != y) {
                        cell_revealer(grid, a, b, grid_size);
                    }
                }
            }
        }
        else {
            grid[x][y].state_hidden = false;
        }
    }


    public GUI(Cell[][] grid, int grid_size){
        //Constructor - initialises GUI
        this.grid = grid;
        this.grid_size  = grid_size;
        this.button_grid = new Button[grid_size][grid_size];

        //Set the layout of GUI
        setLayout(new BorderLayout());
        top.setLayout(new FlowLayout());
        p.setLayout(new GridLayout(grid_size,grid_size));

        add(top,BorderLayout.NORTH);
        add(p, BorderLayout.CENTER);
        top.add(label1);

        make_Buttons();

        setTitle("MINESWEEPERRRRRRRRRRRRRRRRRRRRR");
        setSize(950,750);
        top.setSize(300,100);
        p.setSize(600,600);
        setLocation(300,50);
        setVisible(true);
    }


    public static void main(String[] args){
    }


    public void right_Click(Cell clickedCell, Button clickedButton){
        if (clickedCell.c == 'O') {
            clickedCell.c = (clickedCell instanceof Next_to_bomb)? 'N': (clickedCell instanceof Empty)? 'E': 'B';
        } else {
            button_cell.get(clickedButton).c = 'O';
        }
    }


    public void defeat(){
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                grid[i][j].state_hidden = false;//Reveal all cells
                button_grid[i][j].setEnabled(false);//All buttons are disabled
                grid[i][j].c = (grid[i][j] instanceof Next_to_bomb)? 'N':(grid[i][j] instanceof Empty)? 'E': 'B';//Revert all buttons to their original label
                if (grid[i][j] instanceof Empty || grid[i][j] instanceof Bomb) {//Revert label of button
                    button_grid[i][j].setLabel("" + grid[i][j].c);

                } else if (grid[i][j] instanceof Next_to_bomb) {
                    button_grid[i][j].setLabel("" + ((Next_to_bomb) grid[i][j]).num_bombs_next_to);
                }
            }
        }
        label1.setText("HAHAHAHAHAH YOU LOST!");//Display losing message at top
        label1.setBackground(red);
    }


    public void left_Click(Cell clickedCell){
        if (clickedCell instanceof Bomb) {
            defeat();
        } else {
            cell_revealer(grid, clickedCell.row, clickedCell.column, grid_size);
        }
    }


    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        //When a mouse event occurs - a button clicked
        public void mouseClicked(MouseEvent e) {
            //When a button is clicked this function will bee activated----------------------
            Button clickedButton = (Button) e.getSource();//Which button has been clicked
            Cell clickedCell = button_cell.get(clickedButton);//Which Cell was associated with that button - using dictionary

            if (e.getButton() == MouseEvent.BUTTON3) {
                right_Click(clickedCell,clickedButton);
            }
            else if (e.getButton() == MouseEvent.BUTTON1 && clickedCell.c != 'O') {//Left click and button is not flagged
                left_Click(clickedCell);
            }
            try {
                update_grid(grid,button_grid);//Update grid that is displayed in GUI
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    };
}
