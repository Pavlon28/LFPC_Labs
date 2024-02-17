package Lab_1.SourceCode;

import Lab_1.SourceCode.ProductionRule;
import Lab_1.SourceCode.FiniteAutomaton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grammar {
    private List<String> VN;
    private List<String> VT;
    private List<ProductionRule> P;
    private String S;
    private Set<String> generatedStrings; // Set to store generated strings

    public Grammar(List<String> VN, List<String> VT, List<ProductionRule> P, String S) {
        this.VN = VN;
        this.VT = VT;
        this.P = P;
        this.S = S;
        this.generatedStrings = new HashSet<>();
    }

    public String generateString() {
        StringBuilder stringBuilder = new StringBuilder();
        generateStringHelper(S, stringBuilder);
        String generatedString = stringBuilder.toString();
        while (generatedStrings.contains(generatedString)) { // Regenerate if string has been generated before
            stringBuilder = new StringBuilder();
            generateStringHelper(S, stringBuilder);
            generatedString = stringBuilder.toString();
        }
        generatedStrings.add(generatedString);
        return generatedString;
    }

    private void generateStringHelper(String symbol, StringBuilder stringBuilder) {
        if (VT.contains(symbol)) {
            stringBuilder.append(symbol);
        } else {
            List<ProductionRule> rules = getRulesForSymbol(symbol);
            ProductionRule selectedRule = rules.get((int) (Math.random() * rules.size()));
            for (String s : selectedRule.getProduction()) {
                generateStringHelper(s, stringBuilder);
            }
        }
    }

    private List<ProductionRule> getRulesForSymbol(String symbol) {
        List<ProductionRule> rules = new ArrayList<>();
        for (ProductionRule rule : P) {
            if (rule.getNonTerminal().equals(symbol)) {
                rules.add(rule);
            }
        }
        return rules;
    }


    public FiniteAutomaton toFiniteAutomaton() {
        FiniteAutomaton fa = new FiniteAutomaton();
        fa.setStartState(this.S);

        for (ProductionRule rule : this.P) {
            String nonTerminal = rule.getNonTerminal();
            List<String> production = rule.getProduction();

            if (production.size() == 1) {
                fa.addTransition(nonTerminal, production.get(0), "ACCEPT");
                fa.addAcceptState("ACCEPT");
            } else if (production.size() == 2) {
                fa.addTransition(nonTerminal, production.get(0), production.get(1));
            }
        }

        return fa;
    }
}
