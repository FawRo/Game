// کلاس Davar: این کلاس اطلاعات مربوط به پاسخ نهایی بازی سرنخ را نگهداری می‌کند.
public class Davar {
    // نام داور )
    private String name;
    // اتاق محل وقوع جرم
    private Room room;
    // شخص دزد
    private Person person;
    // محل اختفای الماس
    private Place place;

    // سازنده کلاس Davar: اطلاعات مربوط به پاسخ نهایی بازی را مقداردهی اولیه می‌کند.
    public Davar(String name, Room room, Person person, Place place) {
        this.name = name; // نام داور
        this.room = room; // اتاق
        this.place = place; // مکان الماس
        this.person = person; // شخص دزد
    }

    // متد is_correct: بررسی می‌کند که آیا حدس بازیکن با پاسخ نهایی بازی مطابقت دارد
    // یا خیر.
    public int is_correct(Room room, Person person, Place place) {
        // دریافت نام اتاق، شخص و مکان از پاسخ نهایی بازی
        String r1 = this.room.getName();
        String p1 = this.person.getName();
        String pl1 = this.place.getName();

        // دریافت نام اتاق، شخص و مکان از حدس بازیکن
        String r2 = room.getName();
        String p2 = person.getName();
        String pl2 = place.getName();

        // مقایسه مقادیر و برگرداندن 1 در صورت تطابق کامل و 0 در غیر این صورت
        if (r1.equals(r2) && p1.equals(p2) && pl1.equals(pl2)) {
            return 1; // تطابق کامل، حدس درست است
        }
        return 0; // عدم تطابق، حدس نادرست است
    }

    // متد getName: نام داور را برمی‌گرداند.
    public String getName() {
        return name;
    }

    // متد toString: اطلاعات مربوط به پاسخ نهایی بازی را به صورت یک رشته فرمت شده
    // برمی‌گرداند.
    public String toString() {
        String s = "";
        s += "DAVAR\n";
        s += "room : " + room.getName() + "\n"; // نام اتاق
        s += "person : " + person.getName() + "\n"; // نام شخص دزد
        s += "place : " + place.getName() + "\n"; // نام محل اختفای الماس
        return s;
    }
}
