
// کلاس Game برای مدیریت بازی
import java.util.ArrayList; // وارد کردن کلاس ArrayList
import java.util.Collections; // وارد کردن کلاس Collections
import java.util.List; // وارد کردن کلاس List
import java.util.Random; // وارد کردن کلاس Random
import java.util.Scanner; // وارد کردن کلاس Scanner

public class Game {
    // تعریف متغیرهای خصوصی برای ذخیره اطلاعات بازی
    private List<Person> persons; // لیستی از اشخاص
    private List<Place> places; // لیستی از مکان‌ها
    private List<Room> rooms; // لیستی از اتاق‌ها
    private List<Player> players; // لیستی از بازیکن‌ها
    private List<Integer> tas; // لیستی از تاس‌ها

    // تعریف متغیرهای عمومی برای ذخیره اطلاعات بازی
    public Davar davar; // داور بازی
    public int end_game; // نشان‌دهنده پایان بازی
    public boolean play; // نشان‌دهنده وضعیت بازیکن (فعال یا غیرفعال)
    public int palayer_number; // تعداد بازیکنان

    // ساخت کلاس Game با ورودی‌های palayer_number و play
    public Game(int palayer_number, boolean play) {
        // ایجاد ArrayList برای ذخیره شخصیت‌ها
        persons = new ArrayList<>();
        // ایجاد ArrayList برای ذخیره مکان‌ها
        places = new ArrayList<>();
        // ایجاد ArrayList برای ذخیره اتاق‌ها
        rooms = new ArrayList<>();
        // ایجاد ArrayList برای ذخیره بازیکنان
        players = new ArrayList<>();
        // ایجاد ArrayList برای ذخیره اعداد (tas)
        tas = new ArrayList<>();

        // افزودن اعداد 1 تا 6 به ArrayList tas
        for (int i = 1; i <= 6; i++) {
            tas.add(i);
        }

        // فراخوانی متد add_data برای پر کردن persons، places و rooms با داده‌ها
        add_data();

        // تنظیم متغیر play بر اساس ورودی متد
        this.play = play;
        // تنظیم تعداد بازیکنان بر اساس ورودی متد
        this.palayer_number = palayer_number;
        // تنظیم متغیر end_game به صفر
        this.end_game = 0;

        // چاپ پیام "your name is palayer 1" اگر play برابر true باشد
        if (play) {
            System.out.println("your name is palayer 1");
        }

        // شافل کردن ArrayList های players، persons، places و rooms
        Collections.shuffle(players, new Random());
        Collections.shuffle(persons, new Random());
        Collections.shuffle(places, new Random());
        Collections.shuffle(rooms, new Random());

        // ساخت یک شی Davar و اختصاص دادن کارت‌های اولیه به آن
        davar = new Davar("davar", rooms.get(0), persons.get(0), places.get(0));
        // چاپ اطلاعات کارت‌های Davar
        System.out.println("davar cards:");
        System.out.println(davar.toString());

        // حذف اولین عناصر از persons، places و rooms
        persons.remove(0);
        places.remove(0);
        rooms.remove(0);

        // ایجاد لیست all_Card برای ذخیره تمام کارت‌ها
        List<Card> all_Card = new ArrayList<>();
        all_Card.addAll(persons);
        all_Card.addAll(places);
        all_Card.addAll(rooms);

        // شافل کردن لیست all_Card
        Collections.shuffle(all_Card, new Random());

        // توزیع کارت‌ها به بازیکن‌ها
        int card_number = (int) all_Card.size() / palayer_number;
        for (int i = 0; i < palayer_number; i++) {
            // ایجاد لیست cards برای ذخیره کارت‌های تخصیص یافته به هر بازیکن
            List<Card> cards = new ArrayList<>();
            for (int j = 0; j < card_number; j++) {
                // افزودن اولین کارت از all_Card به لیست cards و حذف آن از all_Card
                cards.add(all_Card.get(0));
                all_Card.remove(0);
            }
            // ایجاد بازیکن با نام "player i" و لیست cards و اضافه کردن آن به players
            Player player = new Player("player " + (i + 1), cards);
            players.add(player);
        }

        // شافل کردن مجدد لیست players
        Collections.shuffle(players, new Random());

        // چاپ اطلاعات هر بازیکن
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).toString());
        }

        // اضافه کردن کارت‌های باقی‌مانده به دیتای تمام بازیکن‌ها
        for (int i = 0; i < all_Card.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                players.get(j).addCard_to_data(all_Card.get(i));
            }
        }

        // پاک کردن ArrayList های persons، places و rooms و فراخوانی add_data
        persons = new ArrayList<>();
        places = new ArrayList<>();
        rooms = new ArrayList<>();
        add_data();

        // چاپ اندازه نهایی ArrayList های rooms، persons و places
        System.out.println(
                "end of constructor ____________ " + rooms.size() + " " + persons.size() + " " + places.size());
    }

    // تعریف متد gues با دو ورودی n و scanner
    public void gues(int n, Scanner scanner) {
        // محاسبه شماره بازیکن بر اساس باقی‌مانده از تقسیم n بر palayer_number

        Player p = players.get(n % palayer_number);
        // چاپ نام بازیکن و نوبت او
        System.out.println(p.getName() + " turn");
        // چاپ شماره اتاق بازیکن
        System.out.println("player room: " + p.getRoomNumber());

        // شافل کردن لیست tas برای دوباره استفاده
        Collections.shuffle(tas, new Random());
        int tas1 = tas.get(0);

        Collections.shuffle(tas, new Random());
        int tas2 = tas.get(0);

        // ایجاد یک لیست جدید برای کارت‌های جدید
        List<Card> newCards = new ArrayList<>();

        // اگر بازیکن player 1 باشد و حرکت توسط کاربر انجام شود
        if (play == true && p.getName().equals("player 1")) {
            System.out.println("1. porsian az davar");
            System.out.println("2. porsidan az yek nafar");

            int k = 0;
            while (k == 0) {
                k = scanner.nextInt();
                // اگر k برابر 1 باشد، حرکت final_gues انجام می‌شود
                if (k == 1) {
                    int e = p.final_gues(rooms, persons, places, davar, scanner);
                    // اگر e برابر 1 باشد، بازیکن برنده می‌شود و بازی پایان می‌یابد
                    if (e == 1) {
                        System.out.println(p.getName() + " win");
                        end_game = 1;
                    }
                    return;

                } else if (k == 2) {
                    // در غیر این صورت، حرکت ask انجام می‌شود
                    p.ask(tas1, tas2, rooms, persons, places, newCards, scanner);
                } else {
                    k = 0;
                }
            }

        } else { // اگر حرکت توسط کامپیوتر انجام شود
            if (p.final_gues(rooms, persons, places, newCards, davar) == 1) {
                // اگر بازیکن برنده شود، بازی پایان می‌یابد
                System.out.println(p.getName() + " win");
                end_game = 1;
                return;
            }
            // در غیر این صورت، حرکت ask انجام می‌شود
            p.ask(tas1, tas2, rooms, persons, places, newCards);
        }

        // بررسی پاسخ‌های بازیکنان دیگر
        int is_answer = 0;
        for (int i = 1; i < players.size(); i++) {
            System.out.println(players.get((n + i) % palayer_number).getName());
            is_answer = players.get((n + i) % palayer_number).answer(newCards, p);
            if (is_answer == 1) {
                break;
            }
        }
        // در صورتی که هیچ کس پاسخی نداشته باشد، پیام "no one have this cards" چاپ
        // می‌شود
        if (is_answer == 0) {
            System.out.println("no one have this cards");
        }

        // چاپ داده‌های بازیکن
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

    // اضافه کردن یک شخص به لیست persons
    public void addPerson(Person person) {
        persons.add(person);
    }

    // اضافه کردن یک مکان به لیست places
    public void addPlace(Place place) {
        places.add(place);
    }

    // اضافه کردن یک اتاق به لیست rooms
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // اضافه کردن یک بازیکن به لیست players
    public void addPlayer(Player player) {
        players.add(player);
    }

    // بازگرداندن لیست بازیکنان
    public List<Player> getPlayers() {
        return players;
    }

}
