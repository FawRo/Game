package fateme.src;

public class Davar {
    private String name;
    private Room room;
    private Place place;
    private Person person;

    public Davar(String name, Room room, Person person, Place place) {
        this.name = name;
        this.room = room;
        this.place = place;
        this.person = person;
    }

    public int is_correct(Room room, Person person, Place place) {

        String r1 = this.room.getName();
        String r2 = room.getName();
        String p1 = this.person.getName();
        String p2 = person.getName();
        String pl1 = this.place.getName();
        String pl2 = place.getName();

        if (r1.equals(r2) && p1.equals(p2) && pl1.equals(pl2)) {
            return 1;
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        String s = "";
        s += "room : " + room.getName() + "\n";
        s += "person : " + person.getName() + "\n";
        s += "place : " + place.getName() + "\n";
        return s;
    }
}
