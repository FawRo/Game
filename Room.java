package fateme.src;

// کلاس Room که از Card ارث بری می‌کند
public class Room extends Card {
    private int n;

    public Room(String name, int n) {
        super(name);
        this.n = n;

    }

    public int getN() {
        return n;
    }

    @Override
    public String getCardType() {
        return "Room";
    }
}