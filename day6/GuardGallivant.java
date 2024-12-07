package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GuardGallivant {

    private enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private class Coordinate {
        int x;
        int y;
        Direction dir;

        Coordinate(int x, int y, Direction dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        Coordinate(Coordinate o) {
            this.x = o.x;
            this.y = o.y;
            this.dir = o.dir;
        }

        public boolean equals(Object o) {
            var compare = (Coordinate) o;
            return (compare.x == x) && (compare.y == y) && (compare.dir == dir);
        }

        public String toString() {
            return "(" + x + ", " + y + ")" + " - " + dir;
        }
    }

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

    Coordinate start_position() throws Exception {
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(j).get(i) == '^') {
                    return new Coordinate(i, j, Direction.UP);
                }
            }
        }
        throw new Exception("start ^ not found!");
    }

    boolean in_bounds(Coordinate pos) {
        return (pos.y >= 0 && pos.y < grid.size()
                && pos.x >= 0 && pos.x < grid.get(0).size());
    }

    int x_count() throws Exception {
        var pos = start_position();
        var start = new Coordinate(pos.x, pos.y, pos.dir);
        while(in_bounds(pos)) {
            grid.get(pos.y).set(pos.x, 'X');
            switch (pos.dir) {
                case Direction.UP:
                {
                    var next_pos = new Coordinate(pos.x, pos.y - 1, Direction.UP);
                    if (in_bounds(next_pos)) {
                        if (grid.get(next_pos.y).get(next_pos.x) == '#') {
                            pos.dir = Direction.RIGHT;
                            break;
                        }
                    }
                    pos.y -= 1;
                    break;
                }
                case Direction.RIGHT:
                {
                    var next_pos = new Coordinate(pos.x + 1, pos.y, Direction.RIGHT);
                    if (in_bounds(next_pos)) {
                        if (grid.get(next_pos.y).get(next_pos.x) == '#') {
                            pos.dir = Direction.DOWN;
                            break;
                        }
                    }
                    pos.x += 1;
                    break;
                }
                case Direction.DOWN:
                {
                    var next_pos = new Coordinate(pos.x, pos.y + 1, Direction.DOWN);
                    if (in_bounds(next_pos)) {
                        if (grid.get(next_pos.y).get(next_pos.x) == '#') {
                            pos.dir = Direction.LEFT;
                            break;
                        }
                    }
                    pos.y += 1;
                    break;
                }
                case Direction.LEFT:
                {
                    var next_pos = new Coordinate(pos.x - 1, pos.y, Direction.LEFT);
                    if (in_bounds(next_pos)) {
                        if (grid.get(next_pos.y).get(next_pos.x) == '#') {
                            pos.dir = Direction.UP;
                            break;
                        }
                    }
                    pos.x -= 1;
                    break;
                }
                default:
                    break;
            }
        }

        int count = 0;
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j) == 'X') {
                    grid.get(i).set(j, '.');
                    count += 1;
                }
            }
        }

        grid.get(start.y).set(start.x, '^');
        return count;
    }

    // solution is too slow but works!
    int position_options() throws Exception {
        int options = 0;
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j) != '.') continue;
                grid.get(i).set(j, '#');
                var visited = new ArrayList<Coordinate>();
                var pos = start_position();
                while(in_bounds(pos)) {
                    if (visited.contains(pos)) {
                        options += 1;
                        break;
                    }
                    visited.add(new Coordinate(pos));
                    switch (pos.dir) {
                        case Direction.UP:
                        {
                            var next_pos = new Coordinate(pos.x, pos.y - 1, Direction.UP);
                            if (in_bounds(next_pos)) {
                                if (grid.get(next_pos.y).get(next_pos.x) == '#') {
                                    pos.dir = Direction.RIGHT;
                                    break;
                                }
                            }
                            pos.y -= 1;
                            break;
                        }
                        case Direction.RIGHT:
                        {
                            var next_pos = new Coordinate(pos.x + 1, pos.y, Direction.RIGHT);
                            if (in_bounds(next_pos)) {
                                if (grid.get(next_pos.y).get(next_pos.x) == '#') {
                                    pos.dir = Direction.DOWN;
                                    break;
                                }
                            }
                            pos.x += 1;
                            break;
                        }
                        case Direction.DOWN:
                        {
                            var next_pos = new Coordinate(pos.x, pos.y + 1, Direction.DOWN);
                            if (in_bounds(next_pos)) {
                                if (grid.get(next_pos.y).get(next_pos.x) == '#') {
                                    pos.dir = Direction.LEFT;
                                    break;
                                }
                            }
                            pos.y += 1;
                            break;
                        }
                        case Direction.LEFT:
                        {
                            var next_pos = new Coordinate(pos.x - 1, pos.y, Direction.LEFT);
                            if (in_bounds(next_pos)) {
                                if (grid.get(next_pos.y).get(next_pos.x) == '#') {
                                    pos.dir = Direction.UP;
                                    break;
                                }
                            }
                            pos.x -= 1;
                            break;
                        }
                        default:
                            break;
                    }
                }
                grid.get(i).set(j, '.');
            }
        }
        return options;
    }

    public static void main (String[] args) throws Exception {
        var solver = new GuardGallivant();
        solver.parse();

        var x_count = solver.x_count();
        System.out.println("x_count total: " + x_count);

        var position_options = solver.position_options();
        System.out.println("obstruction position options: " + position_options);

    }

}