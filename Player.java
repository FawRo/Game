import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// کلاس Player: این کلاس بازیکنان بازی سرنخ را مدل‌سازی می‌کند.
public class Player {
    // نام بازیکن
    private String name;
    // لیست کارت‌های بازیکن
    private List<Card> cards;
    // تعداد کارت‌های بازیکن
    private int cards_number;
    // شماره اتاق فعلی بازیکن
    private int room_number;
    // لیست کارت‌های مکان بازیکن (برای ردیابی کارت‌های دیده شده)
    private List<Place> places;
    // لیست کارت‌های شخص بازیکن (برای ردیابی کارت‌های دیده شده)
    private List<Person> persons;
    // لیست کارت‌های اتاق بازیکن (برای ردیابی کارت‌های دیده شده)
    private List<Room> rooms;

    // سازنده اول کلاس Player: یک بازیکن جدید با نام و لیست کارت‌های اولیه ایجاد
    // می‌کند.

    public Player(String name, List<Card> cards) {

        this.name = name; // مقداردهی نام بازیکن
        this.cards = cards; // مقداردهی لیست کارت‌ها
        this.cards_number = cards.size(); // محاسبه تعداد کارت‌ها
        this.room_number = 1; // مقداردهی اولیه اتاق فعلی به 1 (اتاق شروع)

        // ایجاد لیست‌های خالی برای نگهداری کارت‌های مکان، شخص و اتاق
        this.places = new ArrayList<>();
        this.persons = new ArrayList<>();
        this.rooms = new ArrayList<>();

        // دسته‌بندی کارت‌های اولیه به مکان، شخص و اتاق
        for (Card card : cards) {
            if (card instanceof Place) {
                places.add((Place) card);
            } else if (card instanceof Person) {
                persons.add((Person) card);
            } else if (card instanceof Room) {
                rooms.add((Room) card);
            }
        }
    }

    // متد addCard: یک کارت جدید به لیست کارت‌های بازیکن اضافه می‌کند.
    public void addCard(Card card) {
        cards.add(card); // اضافه کردن کارت به لیست
        cards_number++; // افزایش تعداد کارت‌ها
        addCard_to_data(card); // اضافه کردن کارت به لیست‌های دسته‌بندی شده
    }

    // متد addCard_to_data: این متد یک کارت را به لیست مربوط به نوع آن (مکان، شخص یا
    // اتاق) اضافه می‌کند.
    public void addCard_to_data(Card card) {
        // بررسی نوع کارت با استفاده از instanceof
        if (card instanceof Place) { // اگر کارت از نوع Place باشد
            // تبدیل نوع کارت به Place و اضافه کردن آن به لیست places
            places.add((Place) card);
        } else if (card instanceof Person) { // اگر کارت از نوع Person باشد
            // تبدیل نوع کارت به Person و اضافه کردن آن به لیست persons
            persons.add((Person) card);
        } else if (card instanceof Room) { // اگر کارت از نوع Room باشد
            // تبدیل نوع کارت به Room و اضافه کردن آن به لیست rooms
            rooms.add((Room) card);
        }
    }

    // متد getCards: لیست کارت‌های بازیکن را برمی‌گرداند.
    public List<Card> getCards() {
        return cards;
    }

    // متد getCards_number: تعداد کارت‌های بازیکن را برمی‌گرداند.
    public int getCards_number() {
        return cards_number;
    }

    // متد getRoomNumber: شماره اتاق فعلی بازیکن را برمی‌گرداند.
    public int getRoomNumber() {
        return room_number;
    }

    // متد have_this_card: بررسی می‌کند که آیا بازیکن کارت مشخص شده را در اختیار
    // دارد یا خیر.
    public int have_this_card(Card card) {
        // بررسی نوع کارت برای جستجو در لیست مناسب
        if (card instanceof Place) { // اگر کارت از نوع مکان باشد
            // جستجوی کارت در لیست مکان‌های بازیکن
            for (Place place : places) {
                // مقایسه نام کارت با نام مکان‌های بازیکن
                if (place.getName().equals(card.getName())) {
                    return 1; // کارت پیدا شد (بازیکن این کارت را دارد)
                }
            }
        } else if (card instanceof Person) { // اگر کارت از نوع شخص باشد
            // جستجوی کارت در لیست اشخاص بازیکن
            for (Person person : persons) {
                // مقایسه نام کارت با نام اشخاص بازیکن
                if (person.getName().equals(card.getName())) {
                    return 1; // کارت پیدا شد (بازیکن این کارت را دارد)
                }
            }
        } else if (card instanceof Room) { // اگر کارت از نوع اتاق باشد
            // جستجوی کارت در لیست اتاق‌های بازیکن
            for (Room room : rooms) {
                // مقایسه نام کارت با نام اتاق‌های بازیکن
                if (room.getName().equals(card.getName())) {
                    return 1; // کارت پیدا شد (بازیکن این کارت را دارد)
                }
            }
        }
        return 0; // کارت پیدا نشد (بازیکن این کارت را ندارد)
    }

    // تابع is_valid_room: بررسی می‌کند که آیا رفتن به اتاق جدید با توجه به تاس‌ها و
    // قوانین بازی معتبر است یا خیر.
    public int is_valid_room(int newRoom, int tas1, int tas2) {

        // بررسی اینکه آیا اتاق جدید با اتاق فعلی بازیکن یکی است یا خیر
        if (newRoom == room_number) {
            // اگر یکی بودند یعنی بازیکن در همان اتاق مانده و حرکت نکرده
            return 0; // حرکت نامعتبر
        }

        // قانون زوج و فرد: اگر مجموع تاس‌ها زوج باشد، فقط می‌توان به اتاق‌های زوج رفت و
        // برعکس.
        if ((tas1 + tas2) % 2 != newRoom % 2) {
            return 0; // حرکت نامعتبر
        }

        // بررسی محدودیت‌های حرکت بین اتاق‌ها (مثلاً نمی‌توان از اتاق 2 به اتاق 3 یا 9
        // رفت)
        // این محدودیت‌ها با توجه به ساختار صفحه بازی تعیین می‌شوند.

        // بررسی حرکت از اتاق 2 به اتاق‌های 3 و 9
        if (room_number == 2 && (newRoom == 3 || newRoom == 9)) {
            return 0; // حرکت نامعتبر
        }

        // بررسی حرکت از اتاق 3 به اتاق‌های 2 و 4
        if (room_number == 3 && (newRoom == 2 || newRoom == 4)) {
            return 0; // حرکت نامعتبر
        }

        // بررسی حرکت از اتاق 4 به اتاق‌های 3 و 5
        if (room_number == 4 && (newRoom == 3 || newRoom == 5)) {
            return 0; // حرکت نامعتبر
        }

        // بررسی حرکت از اتاق 5 به اتاق‌های 4 و 6
        if (room_number == 5 && (newRoom == 4 || newRoom == 6)) {
            return 0; // حرکت نامعتبر
        }

        // بررسی حرکت از اتاق 6 به اتاق‌های 5 و 7
        if (room_number == 6 && (newRoom == 5 || newRoom == 7)) {
            return 0; // حرکت نامعتبر
        }

        // بررسی حرکت از اتاق 7 به اتاق‌های 6 و 8
        if (room_number == 7 && (newRoom == 6 || newRoom == 8)) {
            return 0; // حرکت نامعتبر
        }

        // بررسی حرکت از اتاق 8 به اتاق‌های 7 و 9
        if (room_number == 8 && (newRoom == 7 || newRoom == 9)) {
            return 0; // حرکت نامعتبر
        }

        // بررسی حرکت از اتاق 9 به اتاق‌های 8 و 2
        if (room_number == 9 && (newRoom == 8 || newRoom == 2)) {
            return 0; // حرکت نامعتبر
        }

        // در صورتی که هیچ یک از شرایط بالا برقرار نباشند، حرکت معتبر است
        return 1;
    }

    // متد ask: این متد توسط بازیکن برای انتخاب کارت‌های اتاق، شخص و مکان مورد نظر
    // برای پرسیدن سوال استفاده می‌شود (نسخه خودکار)
    public void ask(int tas1, int tas2, List<Room> allrooms_, List<Person> allpersons_, List<Place> allplaces_,
            List<Card> newCards) {

        // نمایش مقادیر تاس‌ها (برای اهداف اشکال‌زدایی و مشاهده مقادیر تاس‌ها)
        System.out.println("tas1 : " + tas1); // چاپ مقدار تاس اول
        System.out.println("tas2 : " + tas2); // چاپ مقدار تاس دوم

        // ایجاد کپی از لیست‌های اصلی اتاق‌ها، اشخاص و مکان‌ها
        // این کار برای جلوگیری از تغییر لیست‌های اصلی انجام می‌شود تا در صورت نیاز
        // بتوان به حالت اولیه آن‌ها بازگشت.
        List<Room> allrooms = new ArrayList<>(allrooms_.subList(0, allrooms_.size()));
        List<Person> allpersons = new ArrayList<>(allpersons_.subList(0, allpersons_.size()));
        List<Place> allplaces = new ArrayList<>(allplaces_.subList(0, allplaces_.size()));

        // بر زدن (Shuffle) لیست‌ها برای انتخاب تصادفی کارت‌ها
        // این کار باعث می‌شود انتخاب کارت‌ها به صورت تصادفی و غیرقابل پیش‌بینی باشد.
        Collections.shuffle(allrooms, new Random()); // بر زدن لیست اتاق‌ها
        Collections.shuffle(allpersons, new Random()); // بر زدن لیست اشخاص
        Collections.shuffle(allplaces, new Random()); // بر زدن لیست مکان‌ها

        // انتخاب اتاق جدید
        Room newRoom = null; // مقداردهی اولیه متغیر newRoom به null
        // حلقه برای بررسی تک تک اتاق‌ها در لیست
        for (Room room : allrooms) {
            // بررسی دو شرط:
            // 1. آیا رفتن به این اتاق با توجه به تاس‌ها معتبر است؟ (با استفاده از تابع
            // is_valid_room)
            // 2. آیا بازیکن کارت این اتاق را در دست ندارد؟ (با استفاده از تابع
            // have_this_card)
            if (is_valid_room(room.getN(), tas1, tas2) == 1 && have_this_card(room) == 0) {
                newRoom = room; // اگر هر دو شرط برقرار بودند، این اتاق را انتخاب کن
                break; // از حلقه خارج شو چون اتاق مناسب پیدا شد
            }
        }

        // اگر اتاق معتبری که بازیکن کارت آن را نداشته باشد پیدا نشد، یک اتاق معتبر را
        // انتخاب کن
        if (newRoom == null) { // اگر هنوز newRoom برابر null باشد (یعنی اتاق مناسب پیدا نشده)
            for (Room room : allrooms) { // دوباره لیست اتاق‌ها را بررسی کن
                // این بار فقط شرط اعتبار حرکت را بررسی کن، بدون توجه به اینکه بازیکن کارت اتاق
                // را دارد یا خیر
                if (is_valid_room(room.getN(), tas1, tas2) == 1) {
                    newRoom = room; // یک اتاق معتبر را انتخاب کن
                    break; // از حلقه خارج شو
                }
            }
        }

        // انتخاب شخص جدید (مشابه انتخاب اتاق جدید)
        Person newPerson = null;
        for (Person person : allpersons) {
            if (have_this_card(person) == 0) {
                newPerson = person;
                break;
            }
        }
        if (newPerson == null) {
            newPerson = allpersons.get(0);
        }

        // انتخاب مکان جدید (مشابه انتخاب اتاق جدید)
        Place newPlace = null;
        for (Place place : allplaces) {
            if (have_this_card(place) == 0) {
                newPlace = place;
                break;
            }
        }
        if (newPlace == null) {
            newPlace = allplaces.get(0);
        }

        // نمایش کارت‌های انتخاب شده توسط بازیکن (برای اهداف اشکال‌زدایی و نمایش به
        // کاربر)
        System.out.println(name + " : I select this cards");
        System.out.println("new room : " + newRoom.getName());
        System.out.println("new person : " + newPerson.getName());
        System.out.println("new place : " + newPlace.getName());

        // اضافه کردن کارت‌های انتخاب شده به لیست کارت‌های جدید (newCards)
        // این لیست برای نمایش به سایر بازیکنان استفاده می‌شود.
        newCards.add(newRoom);
        newCards.add(newPerson);
        newCards.add(newPlace);

        // به‌روزرسانی شماره اتاق فعلی بازیکن با توجه به اتاق انتخاب شده
        room_number = newRoom.getN();
    }

    // متد ask: این متد توسط بازیکن برای انتخاب کارت‌های اتاق، شخص و مکان مورد نظر
    // برای پرسیدن سوال استفاده می‌شود (نسخه تعاملی با ورودی کاربر).
    public void ask(int tas1, int tas2, List<Room> allrooms, List<Person> allpersons, List<Place> allplaces,
            List<Card> newCards, Scanner scanner) {

        // نمایش مقادیر تاس‌های انداخته شده به بازیکن
        System.out.println("tas1 : " + tas1); // چاپ مقدار تاس اول
        System.out.println("tas2 : " + tas2); // چاپ مقدار تاس دوم

        // انتخاب اتاق جدید توسط بازیکن
        Room newRoom = null; // مقداردهی اولیه متغیر newRoom به null
        int i = 1; // شمارنده برای نمایش شماره گزینه‌ها به کاربر
        System.out.println("Room : "); // چاپ عنوان "Room"
        // نمایش لیست اتاق‌های موجود به بازیکن به همراه شماره‌شان
        for (Room room : allrooms) {
            System.out.print(i + ". " + room.getName() + "\t"); // چاپ شماره و نام اتاق
            i++; // افزایش شمارنده
        }

        // دریافت ورودی از بازیکن برای انتخاب اتاق
        int room_i = 0; // مقداردهی اولیه متغیر room_i به 0
        while (true) { // حلقه تا زمانی که ورودی معتبر دریافت شود
            System.out.print("\nselect a room : "); // درخواست از بازیکن برای انتخاب اتاق
            room_i = scanner.nextInt(); // دریافت ورودی از بازیکن

            // بررسی اعتبار ورودی بازیکن:
            // 1. آیا شماره وارد شده در محدوده معتبر است؟ (1 تا 9)
            // 2. آیا رفتن به این اتاق با توجه به تاس‌ها معتبر است؟ (با استفاده از تابع
            // is_valid_room)
            if (room_i > 0 && room_i < 10 && is_valid_room(room_i, tas1, tas2) == 1) {
                break; // اگر ورودی معتبر بود، از حلقه خارج شو
            } else {
                System.out.println("please enter a valid number"); // درخواست از بازیکن برای وارد کردن شماره معتبر
            }
        }

        // انتخاب شخص جدید توسط بازیکن (مشابه انتخاب اتاق جدید)
        Person newPerson = null;
        int person_i = 0;
        System.out.println("Person : ");
        i = 1;
        for (Person person : allpersons) {
            System.out.print(i + ". " + person.getName() + "\t");
            i++;
        }

        while (true) {
            System.out.print("\nselect a person :");
            person_i = scanner.nextInt();
            if (person_i > 0 && person_i < 7) {
                break;
            } else {
                System.out.println("please enter a valid number");
            }
        }

        // انتخاب مکان جدید توسط بازیکن (مشابه انتخاب اتاق جدید)
        Place newPlace = null; // مقداردهی اولیه متغیر newPlace به null (هنوز مکانی انتخاب نشده است)

        // نمایش لیست مکان‌ها به بازیکن
        int place_i = 0; // مقداردهی اولیه متغیر place_i به 0 (برای ذخیره شماره مکان انتخابی)
        System.out.println("Place : "); // چاپ عنوان "Place" برای راهنمایی بازیکن

        // شمارنده برای نمایش شماره گزینه‌ها به کاربر (از 1 شروع می‌شود)
        i = 1;

        // حلقه برای نمایش تک تک مکان‌ها به بازیکن
        for (Place place : allplaces) {
            System.out.print(i + ". " + place.getName() + "\t"); // چاپ شماره و نام مکان
            i++; // افزایش شمارنده برای مکان بعدی
        }

        // حلقه برای دریافت ورودی معتبر از بازیکن
        while (true) {
            System.out.print("\nselect a place : "); // درخواست از بازیکن برای انتخاب مکان
            place_i = scanner.nextInt(); // دریافت ورودی از بازیکن (شماره مکان)

            // بررسی اعتبار ورودی بازیکن:
            // 1. آیا شماره وارد شده در محدوده معتبر است؟ (1 تا 6)
            if (place_i > 0 && place_i < 7) {
                break; // اگر ورودی معتبر بود، از حلقه خارج شو
            } else {
                System.out.println("please enter a valid number"); // درخواست از بازیکن برای وارد کردن شماره معتبر
            }
        }

        // دریافت اشیاء انتخاب شده از لیست‌ها با توجه به شماره وارد شده توسط بازیکن
        newRoom = allrooms.get(room_i - 1); // شماره‌های لیست از 0 شروع می‌شوند، بنابراین 1 واحد کم می‌کنیم
        newPerson = allpersons.get(person_i - 1);
        newPlace = allplaces.get(place_i - 1);

        // نمایش کارت‌های انتخاب شده توسط بازیکن (برای اهداف اشکال‌زدایی و نمایش به
        // کاربر)
        System.out.println(name + " : I select this cards");
        System.out.println("new room : " + newRoom.getName());
        System.out.println("new person : " + newPerson.getName());
        System.out.println("new place : " + newPlace.getName());

        // اضافه کردن کارت‌های انتخاب شده به لیست کارت‌های جدید (newCards)
        // این لیست برای نمایش به سایر بازیکنان استفاده می‌شود.
        newCards.add(newRoom);
        newCards.add(newPerson);
        newCards.add(newPlace);

        // به‌روزرسانی شماره اتاق فعلی بازیکن با توجه به اتاق انتخاب شده
        room_number = newRoom.getN();
    }

    // متد final_gues: این متد توسط بازیکن برای انجام حدس نهایی در بازی استفاده
    // می‌شود (نسخه خودکار).
    public int final_gues(List<Room> allrooms, List<Person> allpersons, List<Place> allplaces, List<Card> newCards,
            Davar davar) {
        // محاسبه تعداد کارت‌های هر نوع در دست بازیکن
        int room_n = rooms.size(); // تعداد کارت‌های اتاق
        int person_n = persons.size(); // تعداد کارت‌های شخص
        int place_n = places.size(); // تعداد کارت‌های مکان

        // بررسی اینکه آیا بازیکن تمام کارت‌های مورد نیاز را دارد
        // (8 کارت اتاق، 5 کارت شخص و 5 کارت مکان)
        if (room_n == 8 && person_n == 5 && place_n == 5) {
            // اگر تعداد کارت‌ها کامل بود، به این معنی است که بازیکن می‌تواند حدس نهایی را
            // بزند.

            // پیدا کردن کارت‌های گمشده برای حدس نهایی
            for (Room room : allrooms) {
                // اگر بازیکن این کارت اتاق را ندارد، آن را به لیست کارت‌های جدید اضافه می‌کند.
                if (have_this_card(room) == 0) {
                    newCards.add(room);
                    break; // از حلقه خارج شو چون کارت اتاق پیدا شد
                }
            }

            for (Person person : allpersons) {
                // اگر بازیکن این کارت شخص را ندارد، آن را به لیست کارت‌های جدید اضافه می‌کند.
                if (have_this_card(person) == 0) {
                    newCards.add(person);
                    break; // از حلقه خارج شو چون کارت شخص پیدا شد
                }
            }

            for (Place place : allplaces) {
                // اگر بازیکن این کارت مکان را ندارد، آن را به لیست کارت‌های جدید اضافه می‌کند.
                if (have_this_card(place) == 0) {
                    newCards.add(place);
                    break; // از حلقه خارج شو چون کارت مکان پیدا شد
                }
            }

            // بررسی درستی حدس نهایی بازیکن با استفاده از متد is_correct در کلاس Davar
            if (davar.is_correct((Room) newCards.get(0), (Person) newCards.get(1), (Place) newCards.get(2)) == 1) {
                // اگر حدس درست بود:

                // چاپ نام اتاق، شخص و مکان حدس زده شده
                System.out.println("player guess : ");
                System.out.println("room : " + newCards.get(0).getName());
                System.out.println("person : " + newCards.get(1).getName());
                System.out.println("place : " + newCards.get(2).getName());

                // چاپ اطلاعات پاسخ صحیح
                System.out.println(davar.toString());

                // برگرداندن 1 به معنی حدس درست
                return 1;
            } else {
                // اگر حدس نادرست بود، 0 را برمی‌گرداند
                return 0;
            }
        } else {
            // اگر بازیکن هنوز کارت‌های کافی برای حدس نهایی را ندارد، 0 را برمی‌گرداند
            return 0;
        }
    }

    public int final_gues(List<Room> allrooms, List<Person> allpersons, List<Place> allplaces, Davar davar,
            Scanner scanner) {

        // مقداردهی اولیه متغیر شمارنده i به 1
        int i = 1;
        // چاپ پیام "Room : " برای راهنمایی کاربر
        System.out.println("Room : ");
        // حلقه‌ای برای نمایش نام تمام اتاق‌ها با شماره‌های متوالی
        for (Room room : allrooms) {
            // چاپ شماره اتاق و نام آن به صورت پشت سر هم
            System.out.print(i + ". " + room.getName() + "\t");
            // افزایش شمارنده i
            i++;
        }

        // مقداردهی اولیه متغیر room_i به 0
        int room_i = 0;
        // حلقه‌ای بی‌نهایت برای دریافت ورودی معتبر از کاربر
        while (true) {
            // درخواست انتخاب اتاق از کاربر
            System.out.print("\nselect a room : ");
            // دریافت ورودی از کاربر و ذخیره آن در room_i
            room_i = scanner.nextInt();
            // بررسی می‌کند که آیا شماره وارد شده معتبر است یا نه
            if (room_i > 0 && room_i < 10) {
                // اگر شماره معتبر باشد، از حلقه خارج می‌شود
                break;
            } else {
                // اگر شماره معتبر نباشد، پیام خطا چاپ می‌کند
                System.out.println("please enter a valid number");
            }
        }

        // مقداردهی اولیه متغیر person_i به 0
        int person_i = 0;
        // چاپ پیام "Person : " برای راهنمایی کاربر
        System.out.println("Person : ");
        // مقداردهی مجدد شمارنده i به 1
        i = 1;
        // حلقه‌ای برای نمایش نام تمام اشخاص با شماره‌های متوالی
        for (Person person : allpersons) {
            // چاپ شماره شخص و نام آن به صورت پشت سر هم
            System.out.print(i + ". " + person.getName() + "\t");
            // افزایش شمارنده i
            i++;
        }

        // حلقه‌ای بی‌نهایت برای دریافت ورودی معتبر از کاربر
        while (true) {
            // درخواست انتخاب شخص از کاربر
            System.out.print("\nselect a person :");
            // دریافت ورودی از کاربر و ذخیره آن در person_i
            person_i = scanner.nextInt();
            // بررسی می‌کند که آیا شماره وارد شده معتبر است یا نه
            if (person_i > 0 && person_i < 7) {
                // اگر شماره معتبر باشد، از حلقه خارج می‌شود
                break;
            } else {
                // اگر شماره معتبر نباشد، پیام خطا چاپ می‌کند
                System.out.println("please enter a valid number");
            }
        }

        // مقداردهی اولیه متغیر place_i به 0
        int place_i = 0;
        // چاپ پیام "Place : " برای راهنمایی کاربر
        System.out.println("Place : ");
        // مقداردهی مجدد شمارنده i به 1
        i = 1;
        // حلقه‌ای برای نمایش نام تمام مکان‌ها با شماره‌های متوالی
        for (Place place : allplaces) {
            // چاپ شماره مکان و نام آن به صورت پشت سر هم
            System.out.print(i + ". " + place.getName() + "\t");
            // افزایش شمارنده i
            i++;
        }

        // حلقه‌ای بی‌نهایت برای دریافت ورودی معتبر از کاربر
        while (true) {
            // درخواست انتخاب مکان از کاربر
            System.out.print("\nselect a place : ");
            // دریافت ورودی از کاربر و ذخیره آن در place_i
            place_i = scanner.nextInt();
            // بررسی می‌کند که آیا شماره وارد شده معتبر است یا نه
            if (place_i > 0 && place_i < 7) {
                // اگر شماره معتبر باشد، از حلقه خارج می‌شود
                break;
            } else {
                // اگر شماره معتبر نباشد، پیام خطا چاپ می‌کند
                System.out.println("please enter a valid number");
            }
        }

        // انتخاب اتاق بر اساس شماره وارد شده توسط کاربر
        Room newRoom = allrooms.get(room_i - 1);
        // انتخاب شخص بر اساس شماره وارد شده توسط کاربر
        Person newPerson = allpersons.get(person_i - 1);
        // انتخاب مکان بر اساس شماره وارد شده توسط کاربر
        Place newPlace = allplaces.get(place_i - 1);

        // بررسی می‌کند که آیا ترکیب انتخابی کاربر صحیح است یا نه
        if (davar.is_correct(newRoom, newPerson, newPlace) == 1) {
            // اگر ترکیب صحیح باشد، پیام "you win" چاپ می‌کند
            System.out.println("you win");
            // نام اتاق انتخابی را چاپ می‌کند
            System.out.println("room : " + newRoom.getName());
            // نام شخص انتخابی را چاپ می‌کند
            System.out.println("person : " + newPerson.getName());
            // نام مکان انتخابی را چاپ می‌کند
            System.out.println("place : " + newPlace.getName());

            // اطلاعات داور را چاپ می‌کند
            System.out.println(davar.toString());

            // 1 را برمی‌گرداند تا نشان دهد کاربر برنده شده است
            return 1;
        }

        // اگر ترکیب نادرست باشد، پیام "you lose" چاپ می‌کند
        System.out.println("you lose");
        // 0 را برمی‌گرداند تا نشان دهد کاربر بازنده شده است
        return 0;
    }

    public int answer(List<Card> newCards, Player other) {
        // بازیابی اتاق از کارت‌های جدید و تبدیل آن به نوع Room
        Room newRoom = (Room) newCards.get(0);
        // بازیابی شخص از کارت‌های جدید و تبدیل آن به نوع Person
        Person newPerson = (Person) newCards.get(1);
        // بازیابی مکان از کارت‌های جدید و تبدیل آن به نوع Place
        Place newPlace = (Place) newCards.get(2);

        // مقداردهی اولیه کارت نمایشی به null
        Card showCard = null;

        // حلقه برای بررسی کارت‌های بازیکن
        for (Card card : cards) {
            // اگر نام کارت برابر با نام اتاق جدید یا شخص جدید یا مکان جدید باشد
            if (card.getName().equals(newRoom.getName()) || card.getName().equals(newPerson.getName())
                    || card.getName().equals(newPlace.getName())) {
                // اگر بازیکن دیگر این کارت را ندارد
                if (other.have_this_card(card) == 0) {
                    // کارت نمایشی را به این کارت تنظیم می‌کند
                    showCard = card;
                    // اضافه کردن کارت نمایشی به داده‌های بازیکن دیگر
                    other.addCard_to_data(showCard);
                    // خارج شدن از حلقه
                    break;
                }
            }
        }

        // اگر کارت نمایشی null باقی مانده باشد، یعنی هیچ کارتی پیدا نشده است
        if (showCard == null) {
            // چاپ پیام که هیچ یک از این کارت‌ها را ندارد
            System.out.println(name + " : I don't have any of these cards");
            // برگرداندن مقدار 0
            return 0;
        }

        // اگر کارت نمایشی تنظیم شده باشد، چاپ پیام که کارت مورد نظر را دارد
        System.out.println(name + " : I have this card : " + showCard.getName());
        // برگرداندن مقدار 1
        return 1;
    }

    // تابع getName برای بازگرداندن نام
    public String getName() {
        // بازگرداندن مقدار name
        return name;
    }

    // تابع show_data برای نمایش داده‌ها
    public String show_data() {
        // تعریف و مقداردهی اولیه رشته s
        String s = "";
        // اضافه کردن برچسب "rooms : " به رشته s
        s += "rooms : \n";
        // حلقه‌ای برای پیمایش اتاق‌ها و اضافه کردن نام هر اتاق به رشته s
        for (Room room : rooms) {
            s += room.getName() + "\t";
        }
        // اضافه کردن خط جداکننده به رشته s
        s += "\n------------------\n";
        // اضافه کردن برچسب "persons : " به رشته s
        s += "persons : \n";
        // حلقه‌ای برای پیمایش اشخاص و اضافه کردن نام هر شخص به رشته s
        for (Person person : persons) {
            s += person.getName() + "\t";
        }
        // اضافه کردن خط جداکننده به رشته s
        s += "\n------------------\n";
        // اضافه کردن برچسب "places : " به رشته s
        s += "places : \n";
        // حلقه‌ای برای پیمایش مکان‌ها و اضافه کردن نام هر مکان به رشته s
        for (Place place : places) {
            s += place.getName() + "\t";
        }
        // اضافه کردن خط جداکننده نهایی به رشته s
        s += "\n===================================\n\n";
        // بازگرداندن رشته s
        return s;
    }

    // تابع toString برای نمایش اطلاعات به صورت رشته
    public String toString() {
        // تعریف و مقداردهی اولیه رشته s
        String s = "";
        // اضافه کردن نام و مقدار name به رشته s
        s += "name : " + name + ":\n";
        // اضافه کردن تعداد کارت‌ها به رشته s
        s += "card number : " + cards_number + "\n";
        // اضافه کردن برچسب "cards : " به رشته s
        s += "cards : \n";
        // حلقه‌ای برای پیمایش کارت‌ها و اضافه کردن نام هر کارت به رشته s
        for (Card card : cards) {
            s += card.getName() + "\t";
        }
        // اضافه کردن خط جداکننده به رشته s
        s += "\n------------------\n";
        // اضافه کردن برچسب "rooms : " به رشته s
        s += "rooms : \n";
        // حلقه‌ای برای پیمایش اتاق‌ها و اضافه کردن نام هر اتاق به رشته s
        for (Room room : rooms) {
            s += room.getName() + "\t";
        }
        // اضافه کردن خط جداکننده به رشته s
        s += "\n------------------\n";
        // اضافه کردن برچسب "persons : " به رشته s
        s += "persons : \n";
        // حلقه‌ای برای پیمایش اشخاص و اضافه کردن نام هر شخص به رشته s
        for (Person person : persons) {
            s += person.getName() + "\t";
        }
        // اضافه کردن خط جداکننده به رشته s
        s += "\n------------------\n";
        // اضافه کردن برچسب "places : " به رشته s
        s += "places : \n";
        // حلقه‌ای برای پیمایش مکان‌ها و اضافه کردن نام هر مکان به رشته s
        for (Place place : places) {
            s += place.getName() + "\t";
        }
        // اضافه کردن خط جداکننده نهایی به رشته s
        s += "\n===================================\n\n";
        // بازگرداندن رشته s
        return s;
    }

}
