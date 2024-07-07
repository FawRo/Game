package fateme.src;

// کلاس Person که از Card ارث بری می‌کند
public class Person extends Card {
    public Person(String name) {
        super(name);
    }

    @Override
    public String getCardType() {
        return "Person";
    }
}