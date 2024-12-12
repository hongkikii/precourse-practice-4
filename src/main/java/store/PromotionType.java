package store;

public enum PromotionType {
    ONE_PLUS_ONE(1, 1),
    TWO_PLUS_ONE(2, 1),
    NONE(0, 0);

    private final int buyCount;
    private final int getcount;

    PromotionType(int buyCount, int getcount) {
        this.buyCount = buyCount;
        this.getcount = getcount;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public int getGetCount() {
        return getcount;
    }

    public int getTotalCount() {
        return buyCount + getcount;
    }

    public static PromotionType getBy(int buyCount, int getcount) {
        if(buyCount == 1 && getcount == 1) {
            return PromotionType.ONE_PLUS_ONE;
        }
        if(buyCount == 2 && getcount == 1) {
            return PromotionType.TWO_PLUS_ONE;
        }
        return PromotionType.NONE;
    }
}
