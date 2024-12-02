package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorianHysteria {

    List<Integer> left_list = new ArrayList<>();
    List<Integer> right_list = new ArrayList<>();
    Map<Integer, Integer> frequency = new HashMap<>();

    void parse() throws IOException {
        var lines = Files.lines(Paths.get("input.txt"));
        lines.forEach(line -> {
            var parts = line.split("   ");
            left_list.add(Integer.parseInt(parts[0].trim()));
            right_list.add(Integer.parseInt(parts[1].trim()));
        });
        lines.close();
    }

    void sort() {
        left_list.sort(null);
        right_list.sort(null);
    }

    int calculate_distance() {
        int distance = 0;
        for (int i = 0; i < left_list.size();  i++) {
            distance += Math.abs(right_list.get(i) - left_list.get(i));
        }
        return distance;
    }

    void count_frequency() {
        for (int num : right_list) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }
    }

    int calculate_similarity() {
        int similarity = 0;
        for (int num : left_list) {
            similarity += num * frequency.getOrDefault(num, 0);
        }
        return similarity;
    }

    public static void main (String[] args) throws IOException {
        var solver = new HistorianHysteria();
        solver.parse();
        solver.sort();

        var distance = solver.calculate_distance();
        System.out.println("Total distance: " + distance);

        solver.count_frequency();
        var similarity = solver.calculate_similarity();
        System.out.println("Similarity score: " + similarity);
    }
}