package store;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final PromotionType promotionType;

    public Promotion(String name, LocalDate startDate, LocalDate endDate, PromotionType promotionType) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.promotionType = promotionType;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPositive() {
        return this.promotionType != PromotionType.NONE;
    }
}
