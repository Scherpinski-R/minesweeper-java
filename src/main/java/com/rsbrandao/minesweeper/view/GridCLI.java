package com.rsbrandao.minesweeper.view;

import com.rsbrandao.minesweeper.exception.ExplosionException;
import com.rsbrandao.minesweeper.exception.QuitException;
import com.rsbrandao.minesweeper.model.Grid;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class GridCLI {
    public GridCLI(Grid grid) {
        this.grid = grid;
        playGame();
    }

    private void playGame() {
        try {
            boolean leave = false;
            while (!leave) {
                loop();
                String answer = prompt("Keep playing? (Y/n): ");
                if ("n".equalsIgnoreCase(answer))
                    leave = true;
                else grid.restart();
            }
            System.out.println("Bye, Bye!");

        } catch (QuitException e) {
            System.out.println("Bye, Bye!");
        } finally {
            in.close();
        }
    }

    private void loop() {
        try {
            while (!grid.goalCompleted()) {
                System.out.println(grid);
                String answer = prompt("Type the coordinates (x,y): ");
                Iterator<Integer> xy = Arrays.stream(answer.split(",")).map(e -> Integer.parseInt(e.trim())).iterator();

                answer = prompt("1 - Open | 2 - (Un)Flag: ");

                if ("1".equals(answer)) {
                    grid.open(xy.next(), xy.next());
                } else if ("2".equals(answer)) {
                    grid.changeFlag(xy.next(), xy.next());
                }
            }

            System.out.println(grid);
            System.out.println("You won!");
        } catch (ExplosionException e) {
            System.out.println(grid);
            System.out.println("You Lost!");
        }
    }
    private String prompt(String text) {
        System.out.print(text);
        String answer = in.nextLine();

        if ("quit".equalsIgnoreCase(answer)) {
            throw new QuitException();
        }

        return answer;
    }

    private final Grid grid;
    private Scanner in = new Scanner(System.in);
}
