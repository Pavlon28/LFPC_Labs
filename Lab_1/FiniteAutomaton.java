package Lab_1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FiniteAutomaton {
    private Map<String, Map<String, String>> transitions;
    private String startState;
    private Set<String> acceptStates;

    public FiniteAutomaton() {
        this.transitions = new HashMap<>();
        this.acceptStates = new HashSet<>();
    }

    public void addTransition(String state, String input, String nextState) {
        this.transitions.putIfAbsent(state, new HashMap<>());
        this.transitions.get(state).put(input, nextState);
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public void addAcceptState(String acceptState) {
        this.acceptStates.add(acceptState);
    }

}