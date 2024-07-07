// کلاس Person: این کلاس نمایانگر کارت های شخصیت در بازی سرنخ است.
public class Person extends Card {
    // سازنده کلاس Person: نام شخص را مقداردهی اولیه می‌کند.
    public Person(String name) {
        // فراخوانی سازنده کلاس پدر (Card) برای مقداردهی نام کارت
        super(name);
    }

    // بازنویسی (Override) متد getCardType از کلاس پدر (Card):
    // این متد نوع کارت را مشخص می‌کند که در اینجا "Person" است.
    @Override
    public String getCardType() {
        return "Person";
    }
}
