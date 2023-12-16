package com.rsbrandao.minesweeper.model;

import com.rsbrandao.minesweeper.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Square {
    Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    boolean addNeighbour(Square s) {
        int distanceCol = Math.abs(this.col - s.getCol());
        int distanceRow = Math.abs(this.row - s.getRow());

        if (distanceRow > 1 || distanceCol > 1) {
            return false;
        }

        neighbours.add(s);
        return true;
    }

    public boolean hasBomb() {
        return bomb;
    }
    void setBomb() {
        bomb = true;
    }

    public boolean isFlagged() {
        return flag;
    }

    void changeFlag() {
        if (!uncovered) {
            flag = !flag;
        }
    }

    public boolean isUncovered() {
        return uncovered;
    }

    void setUncovered() {
        this.uncovered = true;
    }

    public boolean open() {
        if (!flag && !uncovered) {
            uncovered = true;

            if (this.bomb) {
                throw new ExplosionException();
            } else if (isNeighbourhoodSafe()) {
                neighbours.forEach(Square::open);
            }

            return true;
        }
        return false;
    }

    private boolean isNeighbourhoodSafe() {
        return neighbours.stream().noneMatch(n -> n.bomb);
    }

    boolean goalCompleted() {
        boolean isProtected = bomb && flag;
        boolean isDiscovered = !bomb && uncovered;

        return isDiscovered || isProtected;
    }

    long bombsInNeighbourhood() {
        return neighbours.stream().filter(n -> n.bomb).count();
    }

    void restart() {
        uncovered = false;
        bomb = false;
        flag = false;
    }

    public String toString() {
        if(flag) {
            return "x";
        } else if (uncovered && bomb) {
            return "*";
        } else if (uncovered && bombsInNeighbourhood() > 0) {
            return Long.toString(bombsInNeighbourhood());
        } else if (uncovered) {
            return "_";
        } else {
            return "?";
        }
    }

    private final List<Square> neighbours = new ArrayList<>();
    private final int row;
    private final int col;
    private boolean bomb;
    private boolean uncovered;
    private boolean flag;
}
