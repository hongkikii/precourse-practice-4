package store;

public class OutputView {
    public void showWelcomeComment() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public void show(Inventory inventory) {
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
        inventory.printFormatted();
        System.out.println();
    }

    public void showReceipt(User user, Inventory inventory, Membership membership) {
        System.out.println("===========W 편의점=============");
        System.out.printf("%-8s %5s %5s", "상품명", "수량", "금액");
        System.out.println();
        for (PurchaseItem purchaseItem : user.getShoppingCart()) {
            String productName = purchaseItem.getProductName();
            int totalCount = purchaseItem.getPromotionCount() + purchaseItem.getFreeCount() + purchaseItem.getNonPromotionCount();
            System.out.printf("%-8s %5s %8s",
                    productName,
                    totalCount,
                    inventory.getPriceBy(productName) * totalCount);
            System.out.println();
        }
        System.out.println("===========증\t정=============");
        for (PurchaseItem purchaseItem : user.getShoppingCart()) {
            if (purchaseItem.getFreeCount() > 0) {
                System.out.printf("%-8s %5s",
                        purchaseItem.getProductName(),
                        purchaseItem.getFreeCount());
                System.out.println();
            }
        }
        System.out.println("==============================");
        System.out.printf("%-8s %5s %8s",
                "총구매액",
                user.getTotalCount(),
                getFormattedPrice(user.getTotalPrice(inventory)));
        System.out.println();
        System.out.printf("%-13s %8s",
                "행사할인",
                user.getFormattedFreePrice(inventory));
        System.out.println();
        System.out.printf("%-13s %8s",
                "멤버십할인",
                membership.getFormattedPrice());
        System.out.println();
        int payment = user.getTotalPrice(inventory) - user.getFreePrice(inventory) - membership.getPrice();
        System.out.printf("%-13s %8s",
                "내실돈",
                getFormattedPrice(payment));
        System.out.println();
    }

    private String getFormattedPrice(int price) {
        return String.format("%,d", price);
    }
}
