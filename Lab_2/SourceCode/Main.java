package Lab_2.SourceCode;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Define the grammar variant
        Grammar grammar = new Grammar();
        grammar.VN = new HashSet<>(Arrays.asList("S", "A", "B"));
        grammar.VT = new HashSet<>(Arrays.asList('a', 'b', 'c'));
        grammar.P = new HashMap<>();
        grammar.P.put("S", Arrays.asList("aA", "bB"));
        grammar.P.put("A", Arrays.asList("bS", "cA", "aB"));
        grammar.P.put("B", Arrays.asList("aB", "b"));

        // Check the type of each grammar
        System.out.println("Grammar Classification: " + grammar.checkGrammarType());

        // Define the finite automaton variant (Variant 26)
        FiniteAutomaton finiteAutomaton = new FiniteAutomaton();
        finiteAutomaton.Q = new HashSet<>(Arrays.asList("q0", "q1", "q2", "q3")); // States
        finiteAutomaton.Sigma = new HashSet<>(Arrays.asList('a', 'b', 'c')); // Input alphabet
        finiteAutomaton.delta = new HashSet<>(Arrays.asList(new FiniteAutomaton.Triplet("q0", 'a', "q1"),
                new FiniteAutomaton.Triplet("q1", 'b', "q1"),
                new FiniteAutomaton.Triplet("q1", 'a', "q2"),
                new FiniteAutomaton.Triplet("q0", 'b', "q0"),
                new FiniteAutomaton.Triplet("q2", 'c', "q3"),
                new FiniteAutomaton.Triplet("q3", 'c', "q3")));
        finiteAutomaton.q0 = "q0"; // Initial state
        finiteAutomaton.F = new HashSet<>(Arrays.asList("q3")); // Accepting states

        // Convert finite automaton to regular grammar
        Grammar regularGrammar = finiteAutomaton.toRegularGrammar();

        // Print the regular grammar productions
        System.out.println("Regular Grammar Productions for the NDFA:");
        for (Map.Entry<String, List<String>> entry : regularGrammar.P.entrySet()) {
            for (String production : entry.getValue()) {
                System.out.println(entry.getKey() + " -> " + production);
            }
        }

        // Determine if the finite automaton is deterministic
        boolean isDeterministic = finiteAutomaton.isDeterministic();
        if (isDeterministic) {
            System.out.println("The NDFA is deterministic.");
        } else {
            System.out.println("The NDFA is non-deterministic.");
        }

        // Convert finite automaton to deterministic finite automaton
        FiniteAutomaton dfa = finiteAutomaton.toDeterministicFiniteAutomaton();

        // Check if the resulting DFA is deterministic
        boolean isDeterministicDFA = dfa.isDeterministic();
        if (isDeterministicDFA) {
            System.out.println("The converted DFA is deterministic.");
        } else {
            System.out.println("The converted DFA is non-deterministic.");
        }
    }
}

