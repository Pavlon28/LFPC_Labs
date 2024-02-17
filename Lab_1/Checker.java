package Lab_1;

public class Checker {

    public static boolean containsUppercase(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }


}

