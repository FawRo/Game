package fateme.src;

public class Place extends Card {
    public Place(String name) {
        super(name);
    }

    @Override
    public String getCardType() {
        return "Place";
    }
}