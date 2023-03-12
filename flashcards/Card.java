package flashcards;

import java.util.Objects;

public class Card {
    private String term;
    private String definition;

    private int mistakes;

    public Card(String term, String definition) {
        this.term = term;
        this.definition = definition;
        this.mistakes = 0;
    }

    public Card(String term, String definition, int mistakes) {
        this.term = term;
        this.definition = definition;
        this.mistakes = mistakes;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public void addMistake() {
        this.mistakes++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(term, card.term) && Objects.equals(definition, card.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, definition);
    }
}
