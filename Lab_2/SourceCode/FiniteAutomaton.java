package Lab_2.SourceCode;

import java.util.*;

class FiniteAutomaton {
    Set<String> Q;       // Set of states
    Set<Character> Sigma; // Input alphabet
    Set<Triplet> delta; // Transitions
    String q0;          // Initial state
    Set<String> F;      // Set of accepting states

    static class Triplet {
        String state;
        char inputSymbol;
        String nextState;

        Triplet(String state, char inputSymbol, String nextState) {
            this.state = state;
            this.inputSymbol = inputSymbol;
            this.nextState = nextState;
        }
    }

    FiniteAutomaton() {
        // Initialize the finite automaton with empty sets
        Q = new HashSet<>();
        Sigma = new HashSet<>();
        delta = new HashSet<>();
        F = new HashSet<>();
    }

    boolean stringBelongsToLanguage(String inputString) {
        // Check if the input string belongs to the language recognized by the automaton
        String currentState = q0;
        for (char symbol : inputString.toCharArray()) {
            String nextState = null;
            for (Triplet transition : delta) {
                if (transition.state.equals(currentState) && transition.inputSymbol == symbol) {
                    nextState = transition.nextState;
                    break;
                }
            }
            if (nextState == null) {
                return false;
            }
            currentState = nextState;
        }
        return F.contains(currentState);
    }

    Grammar toRegularGrammar() {
        // Convert the finite automaton to a regular grammar
        Grammar regularGrammar = new Grammar();
        regularGrammar.VN = Q;
        regularGrammar.VT = Sigma;
        regularGrammar.P = new HashMap<>();

        for (String state : Q) {
            regularGrammar.P.put(state, new ArrayList<>());
        }

        for (Triplet transition : delta) {
            if (!transition.nextState.equals("X")) {
                String nextStateStr = String.valueOf(transition.nextState);
                regularGrammar.P.get(transition.state).add(transition.inputSymbol + nextStateStr);
            }
        }

        return regularGrammar;
    }

    boolean isDeterministic() {
        // Check if the automaton is deterministic
        for (String state : Q) {
            for (char symbol : Sigma) {
                Set<String> nextStates = new HashSet<>();
                for (Triplet transition : delta) {
                    if (transition.state.equals(state) && transition.inputSymbol == symbol) {
                        nextStates.add(transition.nextState);
                    }
                }
                if (nextStates.size() > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    FiniteAutomaton toDeterministicFiniteAutomaton() {
        // Convert the non-deterministic finite automaton (NFA) to a deterministic finite automaton (DFA)
        FiniteAutomaton dfa = new FiniteAutomaton();
        dfa.Sigma = Sigma;
        dfa.q0 = epsilonClosure(q0);
        dfa.F = new HashSet<>();
        dfa.Q = new HashSet<>();
        dfa.delta = new HashSet<>();

        Queue<String> unprocessedStates = new LinkedList<>();
        unprocessedStates.add(dfa.q0);
        dfa.Q.add(dfa.q0);

        while (!unprocessedStates.isEmpty()) {
            String currentState = unprocessedStates.poll();
            for (char symbol : Sigma) {
                Set<String> nextStates = new HashSet<>();
                for (char state : currentState.toCharArray()) {
                    for (Triplet transition : delta) {
                        if (transition.state.equals(String.valueOf(state)) && transition.inputSymbol == symbol) {
                            nextStates.add(transition.nextState);
                        }
                    }
                }
                String nextStatesClosure = epsilonClosure(nextStates.toString());
                if (!nextStatesClosure.isEmpty()) {
                    dfa.delta.add(new Triplet(currentState, symbol, nextStatesClosure));
                    if (!dfa.Q.contains(nextStatesClosure)) {
                        dfa.Q.add(nextStatesClosure);
                        unprocessedStates.add(nextStatesClosure);
                    }
                    if (nextStatesClosure.chars().anyMatch(c -> F.contains(String.valueOf((char) c)))) {
                        dfa.F.add(nextStatesClosure);
                    }
                }
            }
        }

        return dfa;
    }

    private String epsilonClosure(String state) {
        // Compute epsilon closure of a state in the NFA
        Set<Character> closure = new HashSet<>();
        Queue<Character> stack = new LinkedList<>();
        for (char c : state.toCharArray()) {
            stack.add(c);
            closure.add(c);
        }
        while (!stack.isEmpty()) {
            char currentState = stack.poll();
            for (Triplet transition : delta) {
                if (transition.state.equals(String.valueOf(currentState)) && transition.inputSymbol == 'ε'
                        && !closure.contains(transition.nextState.charAt(0))) {
                    closure.add(transition.nextState.charAt(0));
                    stack.add(transition.nextState.charAt(0));
                }
            }
        }
        StringBuilder closureStr = new StringBuilder();
        for (char c : closure) {
            closureStr.append(c);
        }
        return closureStr.toString();
    }
}