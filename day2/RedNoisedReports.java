package day2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedNoisedReports {

    private class Report {
        private List<Integer> levels = new ArrayList<>();

        void add_level(Integer value) {
            levels.add(value);
        }

        boolean is_safe() {
            boolean is_increasing = (levels.get(1) > levels.get(0)) ? true : false;
            for (int i = 0; i < levels.size() - 1; i++) {
                var first = levels.get(i);
                var second = levels.get(i + 1);
                var diff =  (is_increasing) ? second - first : first - second;
                if (diff < 1 || diff > 3) return false;
            }
            return true;
        }

        boolean is_safe_tolerate() {
            for (int i = 0; i <= levels.size(); i++) {
                var mod_levels = new ArrayList<>(levels);
                if (i != 0) mod_levels.remove(i - 1);

                boolean is_increasing = (mod_levels.get(1) > mod_levels.get(0)) ? true : false;
                for (int j = 0; j < mod_levels.size() - 1; j++) {
                    var first = mod_levels.get(j);
                    var second = mod_levels.get(j + 1);
                    var diff =  (is_increasing) ? second - first : first - second;
                    if (diff < 1 || diff > 3) break;
                    if (j == mod_levels.size() - 2) return true;
                }
            }
            return false;
        }
    }

    List<Report> reports = new ArrayList<>();

    void parse() throws IOException {
        var lines = Files.lines(Paths.get("input.txt"));
        lines.forEach(line -> {
            var report = new Report();
            Arrays.stream(line.split(" ")).forEach(level -> {
                report.add_level(Integer.parseInt(level.trim()));
            });
            reports.add(report);
        });
        lines.close();
    }

    int safe_total() {
        return (int) reports.stream().filter(report -> report.is_safe()).count();
    }

    int tolerate_safe_total() {
        return (int) reports.stream().filter(report -> report.is_safe_tolerate()).count();
    }

    public static void main (String[] args) throws IOException {
        var solver = new RedNoisedReports();
        solver.parse();

        var safe_total = solver.safe_total();
        System.out.println("Safe Total: " + safe_total);

        var tolerate_safe_total = solver.tolerate_safe_total();
        System.out.println("Tolerate Safe Total: " + tolerate_safe_total);
    }
}
