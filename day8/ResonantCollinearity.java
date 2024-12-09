package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ResonantCollinearity {

    private class Coordinate {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Coordinate(Coordinate o) {
            this.x = o.x;
            this.y = o.y;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    List<List<Character>> grid = new ArrayList<>();
    HashMap<Character, List<Coordinate>> antennas = new HashMap<>();

    void parse() throws IOException {
        var lines = Files.lines(Paths.get("input.txt"));
        lines.forEach(line -> {
            List<Character> row = new ArrayList<>();
            line.chars().forEach(c -> row.add((char) c));
            grid.add(row);
        });
        lines.close();
    }

    void find_antennas() {
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                var character = grid.get(i).get(j);
                if (character != '.') {
                    var list = antennas.getOrDefault(character, new ArrayList<>());
                    list.add(new Coordinate(j, grid.size() - 1 - i));
                    antennas.put(character, list);
                }
            }
        }
    }

    int unique_antinode_locations() {
        char [][] locations = new char [grid.size()][grid.getFirst().size()];
        for (var row : locations) Arrays.fill(row, '.');

        for (var frequency : antennas.values()) {
            for (int i = 0; i < frequency.size() - 1; i++) {
                for (int j = i + 1; j < frequency.size(); j++) {
                    var p1 = frequency.get(i).x < frequency.get(j).x ? frequency.get(i) : frequency.get(j);
                    var p2 = frequency.get(i).x >= frequency.get(j).x ? frequency.get(i) : frequency.get(j);
                    if (p1.x == p2.x) {
                        // handle vertical line
                        var y_diff = Math.abs(p1.y - p2.y);
                        var high_loc = new Coordinate(p1.x, (p1.y > p2.y) ? (p1.y + y_diff) : (p2.y + y_diff));
                        if ((high_loc.y >= 0) && (high_loc.y < locations.length)) locations[high_loc.y][high_loc.x] = '#';

                        var low_loc = new Coordinate(p1.x, (p1.y < p2.y) ? (p1.y - y_diff) : (p2.y - y_diff));
                        if ((low_loc.y >= 0) && (low_loc.y < locations.length)) locations[low_loc.y][low_loc.x] = '#';
                    } else {
                        var x_diff = p2.x - p1.x;
                        double m = (p2.y - p1.y) / (double) x_diff;
                        var c = p1.y - m * p1.x;

                        var x_high = p2.x + x_diff;
                        var high_loc = new Coordinate(x_high, (int)Math.floor(m * x_high + c));

                        if ((high_loc.x >= 0) && (high_loc.x < locations[0].length)
                            && (high_loc.y >= 0) && (high_loc.y < locations.length)) {
                            locations[high_loc.y][high_loc.x] = '#';
                        }

                        var x_low = p1.x - x_diff;
                        var low_loc = new Coordinate(x_low, (int)Math.floor(m * x_low + c));

                        if ((low_loc.x >= 0) && (low_loc.x < locations[0].length)
                            && (low_loc.y >= 0) && (low_loc.y < locations.length)) {
                            locations[low_loc.y][low_loc.x] = '#';
                        }
                    }
                }
            }
        }

        int total = 0;
        for (var row : locations) {
            for (var col : row) {
                if (col == '#') total += 1;
            }
        }
        return total;
    }

    int resonant_antinode_locations() {
        char [][] locations = new char [grid.size()][grid.getFirst().size()];
        for (var row : locations) Arrays.fill(row, '.');

        for (var frequency : antennas.values()) {
            for (int i = 0; i < frequency.size() - 1; i++) {
                for (int j = i + 1; j < frequency.size(); j++) {
                    var p1 = new Coordinate(frequency.get(i).x < frequency.get(j).x ? frequency.get(i) : frequency.get(j));
                    var p2 = new Coordinate (frequency.get(i).x >= frequency.get(j).x ? frequency.get(i) : frequency.get(j));
                    var x_diff = p2.x - p1.x;
                    var y_diff = p2.y - p1.y;
                    while(p2.x < locations[0].length && p2.y < locations.length && p2.y >= 0) {
                        locations[p2.y][p2.x] = '#';
                        p2.x += x_diff;
                        p2.y += y_diff;
                    }

                    while(p1.x >= 0 && p1.y < locations.length && p1.y >= 0) {
                        locations[p1.y][p1.x] = '#';
                        p1.x -= x_diff;
                        p1.y -= y_diff;
                    }
                }
            }
        }

        int total = 0;
        for (var row : locations) {
            for (var col : row) {
                if (col == '#') total += 1;
            }
        }
        return total;
    }

    public static void main (String[] args) throws Exception {
        var solver = new ResonantCollinearity();
        solver.parse();
        solver.find_antennas();

        var unique_antinode_locations = solver.unique_antinode_locations();
        System.out.println("unique antinode locations total: " + unique_antinode_locations);

        var resonant_antinode_locations = solver.resonant_antinode_locations();
        System.out.println("resonant antinode locations total: " + resonant_antinode_locations);

    }
}
