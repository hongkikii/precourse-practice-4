package store;

public class Product {
    private final String name;
    private int count;
    private final int price;
    private final Promotion promotion;

    public Product(String name, int count, int price, Promotion promotion) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public String getFormatted() {
        return "- " + name + " " + getFormattedPrice() + " " + getFormattedCount() + " " + promotion.getName();
    }

    public void sub(int amount) {
        count -= amount;
    }

    private String getFormattedPrice() {
        return String.format("%,d원", price);
    }

    private String getFormattedCount() {
        if (count == 0) {
            return "재고 없음";
        }
        return count + "개";
    }
}
