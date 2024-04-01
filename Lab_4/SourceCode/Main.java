package Lab_4.SourceCode;

public class Main {

    public static void main(String[] args) {
        String re1 = "(a|b)(c|d)E+G?";
        String re2 = "P(Q|R|S)T(UV|W|X)*Z+";
        String re3 = "1(0|1)*2(3|4)^5 36";

        System.out.println("====1====");
        for (int i = 0; i < 5; i++) {
            System.out.println(RegularExpressionGenerator.generateCombinations(re1));
            System.out.println();
        }

        System.out.println("====2====");
        for (int i = 0; i < 5; i++) {
            System.out.println(RegularExpressionGenerator.generateCombinations(re2));
            System.out.println();
        }

        System.out.println("====3====");
        for (int i = 0; i < 5; i++) {
            System.out.println(RegularExpressionGenerator.generateCombinations(re3));
            System.out.println();
        }
    }
}
