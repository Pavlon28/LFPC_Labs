package Lab_4.SourceCode;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RegularExpressionGenerator {

    public static String generateCombinations(String regex) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < regex.length(); i++) {
            char ch = regex.charAt(i);
            StringBuilder currentBuilder = new StringBuilder();

            if (Character.isLetterOrDigit(ch)) {
                if (i + 1 < regex.length() && regex.charAt(i + 1) == '^') {
                    int power = Math.min(Character.getNumericValue(regex.charAt(i + 2)), 5);
                    currentBuilder.append(String.valueOf(ch).repeat(power));
                    i += 2;
                } else if (i + 1 < regex.length() && regex.charAt(i + 1) == '*') {
                    int times = random.nextInt(6); // Random repetition up to 5 times
                    currentBuilder.append(String.valueOf(ch).repeat(times));
                    i++;
                } else if (i + 1 < regex.length() && regex.charAt(i + 1) == '+') {
                    int times = random.nextInt(5) + 1; // Random repetition from 1 to 5 times
                    currentBuilder.append(String.valueOf(ch).repeat(times));
                    i++;
                } else if (i + 1 < regex.length() && regex.charAt(i + 1) == '?') {
                    if (random.nextBoolean()) {
                        currentBuilder.append(ch);
                    }
                    i++;
                } else {
                    currentBuilder.append(ch);
                }
            }

            if (ch == '(') {
                Set<Character> chars = new HashSet<>();
                char nextCh = regex.charAt(i + 1);
                while (nextCh != ')') {
                    if (nextCh != '|') {
                        chars.add(nextCh);
                    }
                    nextCh = regex.charAt(++i);
                }

                if (i + 1 < regex.length() && regex.charAt(i + 1) == '^') {
                    int power = Math.min(Character.getNumericValue(regex.charAt(i + 2)), 5);
                    for (int j = 0; j < power; j++) {
                        char randomChar = (char) (new Random().nextInt(chars.size()) + 'A');
                        currentBuilder.append(randomChar);
                    }
                    i += 2;
                } else {
                    int randomIndex = random.nextInt(chars.size());
                    char randomChar = (char) (randomIndex + 'A');
                    currentBuilder.append(randomChar);
                }
            }

            System.out.println(regex.substring(0, i + 1) + " -> " + currentBuilder.toString());
            result.append(currentBuilder);
        }

        return result.toString();
    }
}
