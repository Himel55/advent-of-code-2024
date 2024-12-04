package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CeresSearch {
    List<List<Character>> grid = new ArrayList<>();

    void parse() throws IOException {
        var lines = Files.lines(Paths.get("input.txt"));
        lines.forEach(line -> {
            List<Character> row = new ArrayList<>();
            line.chars().forEach(c -> row.add((char) c));
            grid.add(row);
        });
        lines.close();
    }

    int xmas_count() {
        int total = 0;

        // horizontal pass forward
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size() - 3; j++) {
                if (grid.get(i).get(j) == 'X'
                 && grid.get(i).get(j+1) == 'M'
                 && grid.get(i).get(j+2) == 'A'
                 && grid.get(i).get(j+3) == 'S') {
                    total += 1;
                 }
            }
        }

        // horizontal pass backward
        for (int i = 0; i < grid.size(); i++) {
            for (int j = grid.get(0).size() - 1; j >= 3; j--) {
                if (grid.get(i).get(j) == 'X'
                 && grid.get(i).get(j-1) == 'M'
                 && grid.get(i).get(j-2) == 'A'
                 && grid.get(i).get(j-3) == 'S') {
                    total += 1;
                 }
            }
        }

        // vertical pass down
        for (int i = 0; i < grid.size() - 3; i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (grid.get(i).get(j) == 'X'
                 && grid.get(i+1).get(j) == 'M'
                 && grid.get(i+2).get(j) == 'A'
                 && grid.get(i+3).get(j) == 'S') {
                    total += 1;
                 }
            }
        }

        // vertical pass up
        for (int i = grid.size() - 1; i >= 3; i--) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (grid.get(i).get(j) == 'X'
                 && grid.get(i-1).get(j) == 'M'
                 && grid.get(i-2).get(j) == 'A'
                 && grid.get(i-3).get(j) == 'S') {
                    total += 1;
                 }
            }
        }

        // diagonal pass forward down
        for (int i = 0; i < grid.size() - 3; i++) {
            for (int j = 0; j < grid.get(0).size() - 3; j++) {
                if (grid.get(i).get(j) == 'X'
                 && grid.get(i+1).get(j+1) == 'M'
                 && grid.get(i+2).get(j+2) == 'A'
                 && grid.get(i+3).get(j+3) == 'S') {
                    total += 1;
                 }
            }
        }

        // diagonal pass backward down
        for (int i = 0; i < grid.size() - 3; i++) {
            for (int j = grid.get(0).size() - 1; j >= 3; j--) {
                if (grid.get(i).get(j) == 'X'
                    && grid.get(i+1).get(j-1) == 'M'
                    && grid.get(i+2).get(j-2) == 'A'
                    && grid.get(i+3).get(j-3) == 'S') {
                    total += 1;
                    }
            }
        }
        
        // diagonal pass forward up
        for (int i = grid.size() - 1; i >= 3; i--) {
            for (int j = 0; j < grid.get(0).size() - 3; j++) {
                if (grid.get(i).get(j) == 'X'
                 && grid.get(i-1).get(j+1) == 'M'
                 && grid.get(i-2).get(j+2) == 'A'
                 && grid.get(i-3).get(j+3) == 'S') {
                    total += 1;
                 }
            }
        }

        // diagonal pass backward up
        for (int i = grid.size() - 1; i >= 3; i--) {
            for (int j = grid.get(0).size() - 1; j >= 3; j--) {
                if (grid.get(i).get(j) == 'X'
                 && grid.get(i-1).get(j-1) == 'M'
                 && grid.get(i-2).get(j-2) == 'A'
                 && grid.get(i-3).get(j-3) == 'S') {
                    total += 1;
                 }
            }
        }

        return total;
    }

    int x_mas_count() {
        int total = 0;

        for (int i = 0; i < grid.size() - 2; i++) {
            for (int j = 0; j < grid.get(0).size() - 2; j++) {
                if (grid.get(i+1).get(j+1) != 'A') continue;
                if (!((grid.get(i).get(j) == 'M' && grid.get(i+2).get(j+2) == 'S')
                    || (grid.get(i).get(j) == 'S' && grid.get(i+2).get(j+2) == 'M'))) continue;
                if (!((grid.get(i+2).get(j) == 'M' && grid.get(i).get(j+2) == 'S')
                    || (grid.get(i+2).get(j) == 'S' && grid.get(i).get(j+2) == 'M'))) continue;
                total += 1;
            }
        }

        return total;
    }

    public static void main (String[] args) throws IOException {
        var solver = new CeresSearch();
        solver.parse();

        var xmas_count = solver.xmas_count();
        System.out.println("XMAS count: " + xmas_count);

        var x_mas_count = solver.x_mas_count();
        System.out.println("X-MAS count: " + x_mas_count);

    }
}
