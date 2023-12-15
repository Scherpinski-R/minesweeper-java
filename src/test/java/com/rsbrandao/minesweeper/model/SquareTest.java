package com.rsbrandao.minesweeper.model;

import com.rsbrandao.minesweeper.exception.ExplosionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SquareTest {
    private Square square;

    @BeforeEach
    void createSquare() {
        square = new Square(3,3);
    }
    @Test
    void testNeighbourLeft() {
        Square neighbour = new Square(3, 2);
        assertTrue(square.addNeighbour(neighbour));
    }
    @Test
    void testNeighbourRight() {
        Square neighbour = new Square(3, 4);
        assertTrue(square.addNeighbour(neighbour));
    }
    @Test
    void testNeighbourAbove() {
        Square neighbour = new Square(2, 3);
        assertTrue(square.addNeighbour(neighbour));
    }
    @Test
    void testNeighbourBelow() {
        Square neighbour = new Square(4, 3);
        assertTrue(square.addNeighbour(neighbour));
    }

    @Test
    void testNeighbourDiagonal() {
        Square neighbour = new Square(4, 4);
        assertTrue(square.addNeighbour(neighbour));
    }

    @Test
    void testNotNeighbourRight() {
        Square neighbour = new Square(3, 5);
        assertFalse(square.addNeighbour(neighbour));
    }

    @Test
    void testFlagDefaultValue() {
        assertFalse(square.isFlagged());
    }
    @Test
    void testChangeFlag() {
        square.changeFlag();
        assertTrue(square.isFlagged());
    }

    @Test
    void testSquareNotFlaggedWithoutBomb() {
        assertTrue(square.open());
    }

    @Test
    void testSquareFlagged() {
        square.changeFlag();
        assertFalse(square.open());
    }

    @Test
    void testSquareFlaggedWithBomb() {
        square.setBomb();
        square.changeFlag();

        assertFalse(square.open());
    }

    @Test
    void testSquareWithBomb() {
        square.setBomb();

        assertThrows(ExplosionException.class, ()-> square.open());
    }

    @Test
    void testOpenNeighbours() {
        var n11 = new Square(1, 1);

        var n22 = new Square(2, 2);
        n22.addNeighbour(n11);

        square.addNeighbour(n22);
        square.open();

        assertTrue( n11.isUncovered() && n22.isUncovered() );

    }
}

