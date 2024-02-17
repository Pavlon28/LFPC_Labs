package Lab_1.SourceCode;

import java.util.List;
class ProductionRule {
    private String nonTerminal;
    private List<String> production;

    public ProductionRule(String nonTerminal, List<String> production) {
        this.nonTerminal = nonTerminal;
        this.production = production;
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public List<String> getProduction() {
        return production;
    }
}