package Lab_2.SourceCode;

import java.util.*;
import java.util.stream.Collectors;

class Grammar {
    Set<String> VN; // Set of non-terminals
    Set<Character> VT; // Set of terminals
    Map<String, List<String>> P; // Dictionary of productions

    Grammar() {
        // Initialize the grammar with empty sets and dictionary
        VN = new HashSet<>();
        VT = new HashSet<>();
        P = new HashMap<>();
    }

    List<String> generateString() {
        // Generate strings from the grammar
        List<String> generatedStrings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            generatedStrings.add(generateStringHelper("S", ""));
        }
        return generatedStrings;
    }

    private String generateStringHelper(String symbol, String currentString) {
        // Helper function to recursively generate strings
        if (VT.contains(symbol.charAt(0))) {
            return currentString + symbol;
        } else {
            List<String> productions = P.get(symbol);
            String chosenProduction = productions.get(new Random().nextInt(productions.size()));
            for (char s : chosenProduction.toCharArray()) {
                currentString = generateStringHelper(String.valueOf(s), currentString);
            }
            return currentString;
        }
    }

    FiniteAutomaton toFiniteAutomaton() {
        // Convert the grammar to a finite automaton
        FiniteAutomaton finiteAutomaton = new FiniteAutomaton();

        finiteAutomaton.Q = new HashSet<>(VN);
        finiteAutomaton.Sigma = VT;
        finiteAutomaton.delta = new HashSet<>();

        for (Map.Entry<String, List<String>> entry : P.entrySet()) {
            for (String production : entry.getValue()) {
                if (production.length() > 1) {
                    finiteAutomaton.delta.add(new FiniteAutomaton.Triplet(entry.getKey(),
                            production.charAt(0), String.valueOf(production.charAt(1))));
                } else {
                    if (VN.contains(entry.getKey())) {
                        finiteAutomaton.delta.add(new FiniteAutomaton.Triplet(entry.getKey(), production.charAt(0),
                                "X"));
                    } else {
                        if (production.equals("b")) {
                            finiteAutomaton.delta.add(new FiniteAutomaton.Triplet(entry.getKey(), production.charAt(0),
                                    "X"));
                        } else if (production.equals("d")) {
                            finiteAutomaton.delta.add(new FiniteAutomaton.Triplet(entry.getKey(), production.charAt(0),
                                    "X"));
                        } else {
                            finiteAutomaton.delta.add(new FiniteAutomaton.Triplet(entry.getKey(), production.charAt(0),
                                    String.valueOf(production.charAt(0))));
                        }
                    }
                }
            }
        }

        finiteAutomaton.q0 = "S";
        finiteAutomaton.F = new HashSet<>();
        finiteAutomaton.F.add("X");

        // Convert VT to Strings and add them to Q
        finiteAutomaton.Q.addAll(VT.stream().map(Object::toString).collect(Collectors.toList()));

        return finiteAutomaton;
    }


    String checkGrammarType() {
        String startSymbol = null;
        boolean hasEpsilon = false;
        for (Map.Entry<String, List<String>> entry : P.entrySet()) {
            if (startSymbol == null) {
                startSymbol = entry.getKey();
            }
            for (String production : entry.getValue()) {
                if (production.contains("ε")) {
                    hasEpsilon = true;
                }
                if (production.length() > 2) {
                    return "Type-0 (Unrestricted)";
                }
                if (production.length() == 2) {
                    if (VN.contains(String.valueOf(production.charAt(0))) && VT.contains(production.charAt(1))) {
                        return "Type-1 (Context-Sensitive)";
                    }
                }
                if (production.length() == 1) {
                    if (VT.contains(production.charAt(0))) {
                        return "Type-3 (Regular)";
                    }
                }
            }
        }
        if (startSymbol != null && !hasEpsilon) {
            return "Type-2 (Context-Free)";
        }
        return "Type-0 (Unrestricted)";
    }
}
