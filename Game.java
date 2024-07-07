package fateme.src;

// کلاس Game برای مدیریت بازی
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private List<Person> persons;
    private List<Place> places;
    private List<Room> rooms;
    private List<Player> players;
    private List<Integer> tas;

    public Davar davar;
    public int end_game;
    public boolean play;
    public int palayer_number;

    public Game(int palayer_number, boolean play) {
        persons = new ArrayList<>();
        places = new ArrayList<>();
        rooms = new ArrayList<>();
        players = new ArrayList<>();
        tas = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            tas.add(i);
        }

        add_data();
        this.play = play;
        this.palayer_number = palayer_number;
        this.end_game = 0;
        if (play) {
            System.out.println("your name is palayer 1");
        }

        Collections.shuffle(players, new Random());
        Collections.shuffle(persons, new Random());
        Collections.shuffle(places, new Random());
        Collections.shuffle(rooms, new Random());

        // دادن کار های جواب به داور و حذف ان ها از کارت ها
        davar = new Davar("davar", rooms.get(0), persons.get(0), places.get(0));
        System.out.println("davar cards:");
        System.out.println(davar.toString());

        persons.remove(0);
        places.remove(0);
        rooms.remove(0);

        List<Card> all_Card = new ArrayList<>();
        all_Card.addAll(persons);
        all_Card.addAll(places);
        all_Card.addAll(rooms);

        Collections.shuffle(all_Card, new Random());

        // توزیع کارت ها به بازیکن ها
        int card_number = (int) all_Card.size() / palayer_number;
        for (int i = 0; i < palayer_number; i++) {
            List<Card> cards = new ArrayList<>();
            for (int j = 0; j < card_number; j++) {
                cards.add(all_Card.get(0));
                all_Card.remove(0);
            }
            // افزودن بازیکن‌ها
            Player player = new Player("player " + (i + 1), cards);
            players.add(player);
        }

        Collections.shuffle(players, new Random());
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).toString());
        }

        // اضافه کردن کارت های باقی مانده به دیتا تمام بازیکن ها
        for (int i = 0; i < all_Card.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                players.get(j).addCard_to_data(all_Card.get(i));
            }
        }

        persons = new ArrayList<>();
        places = new ArrayList<>();
        rooms = new ArrayList<>();
        add_data();
        System.out.println(
                "end of constructor ____________ " + rooms.size() + " " + persons.size() + " " + places.size());
    }

    public void gues(int n, Scanner scanner) {
        // int index = n % palayer_number;
        Player p = players.get(n % palayer_number);
        System.out.println(p.getName() + " turn");
        System.out.println("player room: " + p.getRoomNumber());
        // شافل کردن تاس
        Collections.shuffle(tas, new Random());
        int tas1 = tas.get(0);

        Collections.shuffle(tas, new Random());
        int tas2 = tas.get(0);

        List<Card> newCards = new ArrayList<>();

        if (play == true && p.getName().equals("player 1")) {// انجام حرکت توسط کاربر
            System.out.println("1. porsian az davar");
            System.out.println("2. porsidan az yek nafar");

            int k = 0;
            while (k == 0) {
                k = scanner.nextInt();
                if (k == 1) {
                    int e = p.final_gues(rooms, persons, places, davar, scanner);
                    if (e == 1) {
                        System.out.println(p.getName() + " win");
                        end_game = 1;
                    }
                    return;

                } else if (k == 2) {
                    {
                        p.ask(tas1, tas2, rooms, persons, places, newCards, scanner);
                    }
                } else {
                    k = 0;
                }
            }

        } else {// انجام حرکت به وسیله کامپیوتر
            if (p.final_gues(rooms, persons, places, newCards, davar) == 1) {
                System.out.println(p.getName() + " win");
                end_game = 1;
                return;
            }
            p.ask(tas1, tas2, rooms, persons, places, newCards);
        }

        int is_answer = 0;
        for (int i = 1; i < players.size(); i++) {
            System.out.println(players.get((n + i) % palayer_number).getName());
            is_answer = players.get((n + i) % palayer_number).answer(newCards, p);
            if (is_answer == 1) {
                break;
            }
        }
        if (is_answer == 0) {
            System.out.println("no one have this cards");
        }

        System.out.println("show player data");
        System.out.println(p.show_data());

    }

    public void add_data() {

        // افزودن شخصیت‌ها
        addPerson(new Person("Emma"));
        addPerson(new Person("Liam"));
        addPerson(new Person("Jack"));
        addPerson(new Person("Sophia"));
        addPerson(new Person("Emily"));
        addPerson(new Person("Ella"));

        // افزودن مکان‌ها
        addPlace(new Place("zire goldan"));
        addPlace(new Place("kashoue makhfi"));
        addPlace(new Place("poshte aks"));
        addPlace(new Place("dakhele jaba"));
        addPlace(new Place("zire miz"));
        addPlace(new Place("balaye kamad"));

        // اتاق‌ها
        addRoom(new Room("paziraee", 1));
        addRoom(new Room("otagh piano", 2));
        addRoom(new Room("golkhone", 3));
        addRoom(new Room("otagh motalee", 4));
        addRoom(new Room("otagh bilyard", 5));
        addRoom(new Room("otagh khaab", 6));
        addRoom(new Room("ghaza khori", 7));
        addRoom(new Room("ketabkhane", 8));
        addRoom(new Room("ashpazkhane", 9));
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public void addPlace(Place place) {
        places.add(place);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public static void main(String[] args) {
        Game game = new Game(3, true);

        Scanner scanner = new Scanner(System.in);

        int n = 0;
        System.out.println("start ______ Game\n\n\n");
        while (game.end_game == 0) {
            game.gues(n, scanner);
            n++;
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n\n");
            // int k = scanner.nextInt();
        }
        // game.gues(n);
        scanner.close();

    }
}
