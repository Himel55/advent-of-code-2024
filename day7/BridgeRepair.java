package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BridgeRepair {

    private class CalibrationEquation {
        List<Integer> values = new ArrayList<>();
        long test_value = 0;
    }

    List<CalibrationEquation> equations =  new ArrayList<>();

    void parse() throws IOException {
        var lines = Files.lines(Paths.get("input.txt"));
        lines.forEach(line -> {
            var parts = line.split(":");
            var equation = new CalibrationEquation();
            equation.test_value = Long.parseLong(parts[0]);
            Arrays.stream(parts[1].trim().split(" ")).forEach( 
                v -> equation.values.add(Integer.parseInt(v)));
            equations.add(equation);
        });
        lines.close();
    }

    long total_calibration() {
        long result = 0;
        for (var equation : equations) {
            for (int i = 0; i < Math.pow(2, equation.values.size() - 1); i++) {
                long attempt_total = equation.values.getFirst();
                for (int j = 1; j < equation.values.size(); j++) {
                    if ((i & (1 << (j - 1))) == 0) {
                        attempt_total += equation.values.get(j);
                    } else {
                        attempt_total *= equation.values.get(j);
                    }
                }
                if (attempt_total == equation.test_value) {
                    result += equation.test_value;
                    break;
                }
            }
        }
        return result;
    }

    // solution is too slow but works!
    long total_calibration_extra_op() {
        long result = 0;
        for (var equation : equations) {
            for (int i = 0; i < Math.pow(3, equation.values.size() - 1); i++) {
                long attempt_total = equation.values.getFirst();
                int combination = i;
                for (int j = 1; j < equation.values.size(); j++) {
                    int op = combination % 3;
                    var value = equation.values.get(j);
                    if (op == 0) {
                        attempt_total += value;
                    } else if (op == 1) {
                        attempt_total *= value;
                    } else {
                        attempt_total = Long.parseLong(Long.toString(attempt_total) + Long.toString(value));
                    }
                    combination /= 3;
                }
                if (attempt_total == equation.test_value) {
                    result += equation.test_value;
                    break;
                }
            }
        }
        return result;
    }

    public static void main (String[] args) throws IOException {
        var solver = new BridgeRepair();
        solver.parse();

        var total_calibration = solver.total_calibration();
        System.out.println("Total Calibration Results: " + total_calibration);

        var total_calibration_extra_op = solver.total_calibration_extra_op();
        System.out.println("Total Calibration Results (Extra Op): " + total_calibration_extra_op);
    }
}
