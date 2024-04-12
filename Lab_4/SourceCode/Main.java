package Lab_4.SourceCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        String re1 = "(a|b)(c|d)E+G?";
        String re2 = "P(Q|R|S)T(UV|W|X)*Z+";
        String re3 = "1(0|1)*2(3|4)^5 36";

        System.out.println("====1====");
        for (int j = 0; j < 5; j++) {
            char firstChar = (char) ('a' + new Random().nextInt(2));
            char secondChar = (char) ('c' + new Random().nextInt(2));
            int numE = new Random().nextInt(5) + 1;
            boolean presenceG = new Random().nextBoolean();
            StringBuilder generatedString = new StringBuilder();
            List<String> sequence = new ArrayList<>();
            generateCombinations(re1, generatedString, sequence);
            if (presenceG && generatedString.indexOf("G") == -1) {
                generatedString.append("G");
                sequence.add("G");
            }
            System.out.println(generatedString);
            System.out.println("Processing Sequence:");
            for (String step : sequence) {
                if (!step.isEmpty()) {
                    System.out.println("- " + step);
                }
            }
            System.out.println();
        }

        System.out.println("====2====");
        for (int j = 0; j < 5; j++) {
            char secondChar = (char) ('Q' + new Random().nextInt(3));
            String optionalChars = "";
            for (int k = 0; k < 5; k++) {
                optionalChars += randomChoice("UV", "W", "X");
            }
            int numZ = new Random().nextInt(5) + 1;
            StringBuilder generatedString = new StringBuilder();
            generatedString.append("P").append(secondChar).append("T").append(optionalChars);
            generatedString.append("Z".repeat(Math.max(0, numZ)));
            System.out.println(generatedString);
            System.out.println("Processing Sequence:");
            System.out.println("- P");
            System.out.println("- " + secondChar);
            System.out.println("- T");
            System.out.println("- " + optionalChars);
            System.out.println("- Z".repeat(Math.max(0, numZ)));
            System.out.println();
        }

        System.out.println("====3====");
        for (int j = 0; j < 5; j++) {
            String num01 = new Random().nextBoolean() ? "" : String.valueOf(new Random().nextInt(2));
            StringBuilder num34 = new StringBuilder();
            for (int k = 0; k < 5; k++) {
                num34.append(new Random().nextInt(2) + 3);
            }
            StringBuilder generatedString = new StringBuilder("1" + num01 + "2" + num34 + "36");
            System.out.println(generatedString);
            System.out.println("Processing Sequence:");
            System.out.println("- 1");
            System.out.println("- " + num01);
            System.out.println("- 2");
            System.out.println("- " + num34);
            System.out.println("- 36");
            System.out.println();
        }
    }

    public static void generateCombinations(String regex, StringBuilder result, List<String> sequence) {
        for (int i = 0; i < regex.length(); i++) {
            StringBuilder currentBuilder = new StringBuilder();
            char ch = regex.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                if (i + 1 < regex.length() && regex.charAt(i + 1) == '^') {
                    int power = Math.min(Character.getNumericValue(regex.charAt(i + 2)), 5);
                    currentBuilder.append(String.valueOf(ch).repeat(power));
                    sequence.add(String.valueOf(ch).repeat(power));
                    i += 2;
                } else if (i + 1 < regex.length() && regex.charAt(i + 1) == '*') {
                    int repetitions = new Random().nextInt(6); // Random repetition up to 5 times
                    currentBuilder.append(String.valueOf(ch).repeat(repetitions));
                    sequence.add(String.valueOf(ch).repeat(repetitions));
                    i += 1;
                } else if (i + 1 < regex.length() && regex.charAt(i + 1) == '+') {
                    int repetitions = new Random().nextInt(5) + 1; // Random repetition from 1 to 5 times
                    currentBuilder.append(String.valueOf(ch).repeat(repetitions));
                    sequence.add(String.valueOf(ch).repeat(repetitions));
                    i += 1;
                } else if (i + 1 < regex.length() && regex.charAt(i + 1) == '?') {
                    currentBuilder.append(new Random().nextBoolean() ? ch : "");
                    sequence.add(currentBuilder.toString());
                    i += 1;
                }
            }

            if (ch == '(') {
                StringBuilder chars = new StringBuilder();
                char nextCh = regex.charAt(i + 1);
                while (nextCh != ')') {
                    if (nextCh != '|') {
                        chars.append(nextCh);
                    }
                    nextCh = regex.charAt(++i);
                }

                if (i + 1 < regex.length() && regex.charAt(i + 1) == '^') {
                    int power = Math.min(Character.getNumericValue(regex.charAt(i + 2)), 5); // Limit the repetition to 5 times
                    for (int j = 0; j < power; j++) {
                        currentBuilder.append(chars.charAt(new Random().nextInt(chars.length())));
                    }
                    sequence.add(currentBuilder.toString());
                    i += 2;
                } else {
                    char selectedChar = chars.charAt(new Random().nextInt(chars.length()));
                    currentBuilder.append(selectedChar);
                    sequence.add(String.valueOf(selectedChar));
                }
            }

            result.append(currentBuilder);
        }
    }

    public static String randomChoice(String... choices) {
        return choices[new Random().nextInt(choices.length)];
    }
}
