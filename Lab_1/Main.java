package Lab_1;

import static Lab_1.Checker.containsUppercase;

public class Main {
    public static void main(String[] args) {
        String testString1 = "HelloWorld";
        String testString2 = "lowercase";

        if (containsUppercase(testString1)) {
            System.out.println(testString1 + " contains uppercase characters.");
        } else {
            System.out.println(testString1 + " does not contain uppercase characters.");
        }

        if (containsUppercase(testString2)) {
            System.out.println(testString2 + " contains uppercase characters.");
        } else {
            System.out.println(testString2 + " does not contain uppercase characters.");
        }
    }
}
