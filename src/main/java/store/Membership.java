package store;

public class Membership {
    private int price = 0;

    public Membership() {

    }

    public void set(int purchasePrice) {
        int price = (int) (purchasePrice * 0.3);
        if (price >= 8000) {
            this.price = 8000;
            return;
        }
        this.price = price;
    }

    public String getFormattedPrice() {
        return String.format("-%,d", price);
    }

    public int getPrice() {
        return price;
    }
}
