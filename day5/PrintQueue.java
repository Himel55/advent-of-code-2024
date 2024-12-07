package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PrintQueue {

    private class OrderRule {
        int value_precede;
        int value_succeed;

        OrderRule(int vp, int vs) {
            value_precede = vp;
            value_succeed = vs;
        }

        public String toString() {
            return value_precede + "|" + value_succeed;
        }

        public boolean equals(Object o) {
            var compare = (OrderRule) o;
            return (compare.value_precede == value_precede) && (compare.value_succeed == value_succeed);
        }
    }

    List<OrderRule> rules = new ArrayList<>();
    List<List<Integer>> update_sets = new ArrayList<>();

    void parse() throws IOException {
        var lines = Files.readAllLines(Paths.get("input.txt"));
        var parse_order_rules =  true;
        for (var line : lines) {
            if (line.length() != 0 && parse_order_rules){
                var parts = line.split("\\|");
                var order_rule = new OrderRule((int)Integer.parseInt(parts[0].trim()), (int) Integer.parseInt(parts[1].trim()));
                rules.add(order_rule);
            } else if (line.length() != 0 && !parse_order_rules) {
                var parts = line.split(",");
                var update_set = new ArrayList<Integer>();
                for (var part : parts) {
                    update_set.add(Integer.parseInt(part.trim()));
                }
                update_sets.add(update_set);
            } else {
                parse_order_rules = false;
            }
        }
    }

    int correct_middle_total() {
        int total = 0;
        for (var set : update_sets) {
            var is_incorrect = false;
            for (int i = 0; i < set.size() - 1; i++) {
                for (int j = i + 1; j < set.size(); j++) {
                    var rule = new OrderRule(set.get(j), set.get(i));
                    if (rules.contains(rule)) {
                        is_incorrect =  true;
                        break;
                    }
                }
                if (is_incorrect) break;
            }
            if (!is_incorrect) total += set.get((set.size() - 1) / 2);
        }
        return total;
    }

    int incorrect_middle_total() {
        int total = 0;
        for (var set : update_sets) {
            var was_incorrect = false;
            for (int i = 0; i < set.size() - 1; i++) {
                for (int j = i + 1; j < set.size(); j++) {
                    var rule = new OrderRule(set.get(j), set.get(i));
                    if (rules.contains(rule)) {
                        was_incorrect =  true;
                        var temp = set.get(i);
                        set.set(i, set.get(j));
                        set.set(j, temp);
                    }
                }
            }
            if (was_incorrect) total += set.get((set.size() - 1) / 2);
        }
        return total;
    }

    public static void main (String[] args) throws IOException {
        var solver = new PrintQueue();
        solver.parse();

        var correct_middle_total = solver.correct_middle_total();
        System.out.println("correct middle page total: " + correct_middle_total);

        var incorrect_middle_total = solver.incorrect_middle_total();
        System.out.println("incorrect middle page total: " + incorrect_middle_total);

    }
}
