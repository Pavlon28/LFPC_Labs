package Lab_1;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Grammar definition
        List<String> VN = List.of("S", "A", "B", "C");
        List<String> VT = List.of("a", "b", "c", "d");
        List<ProductionRule> P = new ArrayList<>();
        P.add(new ProductionRule("S", List.of("d", "A")));
        P.add(new ProductionRule("A", List.of("a", "B")));
        P.add(new ProductionRule("B", List.of("b", "C")));
        P.add(new ProductionRule("C", List.of("c", "B")));
        P.add(new ProductionRule("B", List.of("d")));
        P.add(new ProductionRule("C", List.of("a", "A")));
        P.add(new ProductionRule("A", List.of("b")));
        String S = "S";

        Grammar grammar = new Grammar(VN, VT, P, S);

        // Generating 5 valid strings
        System.out.println("5 valid strings:");
        for (int i = 0; i < 5; i++) {
            System.out.println(grammar.generateString());
        }

        // Conversion to Finite Automaton
        FiniteAutomaton finiteAutomaton = grammar.toFiniteAutomaton();
    }
}