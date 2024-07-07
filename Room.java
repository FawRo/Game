// کلاس Room که از کلاس Card ارث بری می‌کند (اتاق یک نوع کارت است)
public class Room extends Card {
    // شماره اتاق (برای مشخص کردن اتاق در بازی)
    private int n;

    // سازنده کلاس Room: نام و شماره اتاق را مقداردهی اولیه می‌کند.
    public Room(String name, int n) {
        // فراخوانی سازنده کلاس پدر (Card) برای مقداردهی نام کارت
        super(name);
        // مقداردهی شماره اتاق
        this.n = n;
    }

    // متد getN: شماره اتاق را برمی‌گرداند.
    public int getN() {
        return n;
    }

    // بازنویسی (Override) متد getCardType از کلاس پدر (Card):
    // این متد نوع کارت را مشخص می‌کند که در اینجا "Room" است.
    @Override
    public String getCardType() {
        return "Room";
    }
}
