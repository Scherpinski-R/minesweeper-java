package com.rsbrandao.minesweeper;

import com.rsbrandao.minesweeper.model.Grid;
import com.rsbrandao.minesweeper.view.GridCLI;

public class App {

    public static void main(String[] args) {
        Grid myGrid = new Grid(6,6,6);
        new GridCLI(myGrid);
    }
}
