package store;

public class PurchaseItem {
    private final String productName;
    private final int productCount;
    private int promotionCount;
    private int nonPromotionCount;
    private int freeCount;

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

    public void addAfter(int promotionCount, int nonPromotionCount, int freeCount) {
        this.promotionCount += promotionCount;
        this.nonPromotionCount += nonPromotionCount;
        this.freeCount += freeCount;
    }

    public int getPromotionCount() {
        return promotionCount;
    }

    public int getNonPromotionCount() {
        return nonPromotionCount;
    }

    public void setNonPromotionCount(int nonPromotionCount) {
        this.nonPromotionCount = nonPromotionCount;
    }

    public int getFreeCount() {
        return freeCount;
    }
}
