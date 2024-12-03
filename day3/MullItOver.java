package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class MullItOver {

    String input;

    void parse() throws IOException {
        input = Files.readString(Paths.get("input.txt"));
    }

    int multiply_total() {
        int total = 0;
        var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        var matches = pattern.matcher(input);

        while(matches.find()) {
            total += Integer.parseInt(matches.group(1)) *  Integer.parseInt(matches.group(2));
        }

        return total;
    }

    int multiply_total_do_dont() {
        int total = 0;
        var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|(don't\\(\\))|(do\\(\\))");
        var matches = pattern.matcher(input);
        boolean state_do = true;

        while(matches.find()) {
            if (state_do && matches.group(1) != null) {
                total += Integer.parseInt(matches.group(1)) *  Integer.parseInt(matches.group(2));
            } else if (matches.group(3) != null) {
                state_do = false;
            } else if (matches.group(4) != null) {
                state_do = true;
            }
        }

        return total;
    }

    public static void main (String[] args) throws IOException {
        var solver = new MullItOver();
        solver.parse();

        var multiply_total = solver.multiply_total();
        System.out.println("Multiply Total: " + multiply_total);

        var multiply_total_do_dont = solver.multiply_total_do_dont();
        System.out.println("Multiply Total (Do\\Don'ts): " + multiply_total_do_dont);
    }
}
