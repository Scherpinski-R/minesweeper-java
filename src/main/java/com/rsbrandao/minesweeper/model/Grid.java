package com.rsbrandao.minesweeper.model;

import com.rsbrandao.minesweeper.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {

    public Grid(int rows, int cols, int nBombs) {
        this.rows = rows;
        this.cols = cols;
        this.nBombs = nBombs;

        generateSquares();
        associateNeighbours();
        generateBombs();
    }

    public void open(int row, int col) {
        try {
            squares.get(row * cols + col).open();
        } catch (ExplosionException e) {
            squares.forEach(Square::setUncovered);
            throw e;
        }
    }

    public void changeFlag(int row, int col) {
        squares.get( row*cols + col ).changeFlag();
    }
    public boolean goalCompleted() {
        return squares.stream().allMatch(Square::goalCompleted);
    }

    public void restart() {
        squares.forEach(Square::restart);
        generateBombs();
    }

    private void generateBombs() {
        for (int createdBombs=0; createdBombs<nBombs; createdBombs++) {

            int sortedPosition;
            Random rand = new Random();
            do {
                sortedPosition = rand.nextInt(squares.size());
            }while ( squares.get(sortedPosition).hasBomb() );

            squares.get(sortedPosition).setBomb();
        }
    }

    private void associateNeighbours() {
        for (Square s1: squares)
            for (Square s2: squares)
                s1.addNeighbour(s2);
    }


    private void generateSquares() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                squares.add( new Square(r, c) );
            }
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("   ");
        for(int c=0; c<cols; c++)
            builder.append(" ").append(c).append(" ");

        builder.append("\n");

        for (int r = 0; r<rows; r++) {
            builder.append(" ").append(r).append(" ");
            for (int c = 0; c<cols; c++) {
                builder.append(" ").append(squares.get( c * rows + r )).append(" ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    private final int rows;
    private final int cols;

    private final int nBombs;
    private final List<Square> squares = new ArrayList<>();
}
