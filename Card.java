package fateme.src;

// کلاس Card
public abstract class Card {
    private String name;

    public Card(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getCardType();
}