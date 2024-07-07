// کلاس Place: این کلاس نمایانگر کارت های مکان در بازی سرنخ است.
public class Place extends Card {
    // سازنده کلاس Place: نام مکان را مقداردهی اولیه می‌کند.
    public Place(String name) {
        // فراخوانی سازنده کلاس پدر (Card) برای مقداردهی نام کارت
        super(name);
    }

    // بازنویسی (Override) متد getCardType از کلاس پدر (Card):
    // این متد نوع کارت را مشخص می‌کند که در اینجا "Place" است.
    @Override
    public String getCardType() {
        return "Place";
    }
}
