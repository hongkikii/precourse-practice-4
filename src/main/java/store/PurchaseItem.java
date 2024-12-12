package store;

public class PurchaseItem {
    private final String productName;
    private final int productCount;

    public PurchaseItem(String productName, int productCount) {
        this.productName = productName;
        this.productCount = productCount;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductCount() {
        return productCount;
    }
}
