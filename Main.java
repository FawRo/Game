import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // ایجاد یک شیء از کلاس Game با 3 بازیکن و حالت بازی true
        Game game = new Game(3, true);

        // ایجاد یک شیء از کلاس Scanner برای دریافت ورودی از کاربر
        Scanner scanner = new Scanner(System.in);

        // شمارنده نوبت بازیکن‌ها
        int n = 0;

        // چاپ پیام شروع بازی
        System.out.println("start ______ Game\n\n\n");

        // حلقه اجرای بازی تا زمانی که بازی تمام نشده باشد
        while (game.end_game == 0) {
            // فراخوانی متد gues برای بازیکن جاری
            game.gues(n, scanner);
            n++; // افزایش شمارنده نوبت
            // چاپ خط جداکننده
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n\n");
            // int k = scanner.nextInt(); // انتظار ورودی از کاربر (غیرفعال شده)
        }

        // بستن شیء Scanner بعد از پایان حلقه
        scanner.close();
    }

}
