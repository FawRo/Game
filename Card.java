// کلاس Card: این کلاس انتزاعی (abstract) به عنوان کلاس پایه برای انواع کارت های بازی عمل می‌کند.
public abstract class Card {
    // نام کارت (مثلا نام اتاق، شخص یا مکان)
    private String name;

    // سازنده کلاس Card: نام کارت را مقداردهی اولیه می‌کند.
    public Card(String name) {
        this.name = name;
    }

    // متد getName: نام کارت را برمی‌گرداند.
    public String getName() {
        return name;
    }

    // متد انتزاعی getCardType: این متد در کلاس‌های فرزند پیاده‌سازی می‌شود
    // و نوع کارت (مثلا "Room"، "Person" یا "Place") را برمی‌گرداند.
    public abstract String getCardType();
}
