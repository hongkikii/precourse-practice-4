package store;

import static store.Constants.COMMON_ERROR_MESSAGE;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;

public class User {
    private final List<PurchaseItem> shoppingCart;

    public User(List<PurchaseItem> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<PurchaseItem> getShoppingCart() {
        return shoppingCart;
    }

    public int getTotalCount() {
        int totalCount = 0;
        for(PurchaseItem item: shoppingCart) {
            totalCount += (item.getFreeCount() + item.getPromotionCount() + item.getNonPromotionCount());
        }
        return totalCount;
    }

    public int getTotalPrice(Inventory inventory) {
        int totalPrice = 0;
        for(PurchaseItem item: shoppingCart) {
            String productName = item.getProductName();
            Product product = inventory.getBy(productName);
            totalPrice += product.getPrice() *
                    (item.getFreeCount() + item.getPromotionCount() + item.getNonPromotionCount());
        }
        return totalPrice;
    }

    public String getFormattedFreePrice(Inventory inventory) {
        int totalPrice = 0;
        for(PurchaseItem item: shoppingCart) {
            if(!(item.getFreeCount() > 0)) continue;
            String productName = item.getProductName();
            Product product = inventory.getBy(productName);
            totalPrice += product.getPrice() * item.getFreeCount();
        }
        return String.format("-%,d", totalPrice);
    }

    public int getFreePrice(Inventory inventory) {
        int totalPrice = 0;
        for(PurchaseItem item: shoppingCart) {
            if(!(item.getFreeCount() > 0)) continue;
            String productName = item.getProductName();
            Product product = inventory.getBy(productName);
            totalPrice += product.getPrice() * item.getFreeCount();
        }
        return totalPrice;
    }

    public int getNonPromotionPurchasePrice(Inventory inventory) {
        int totalPrice = 0;
        List<PurchaseItem> nonPromotionPurchaseProducts = shoppingCart.stream()
                .filter(p -> p.getPromotionCount() == 0)
                .toList();
        for(PurchaseItem purchaseItem : nonPromotionPurchaseProducts) {
            String productName = purchaseItem.getProductName();
            Product product = inventory.getBy(productName);
            totalPrice += product.getPrice() * purchaseItem.getProductCount();
        }
        return totalPrice;
    }

    public void purchase(Inventory inventory) {
        List<Product> products = inventory.getProducts();
        for (PurchaseItem purchaseItem : shoppingCart) {
            int promotionCount = 0;
            int nonPromotionCount = 0;
            int freeCount = 0;
            int purchaseAmount = purchaseItem.getProductCount();
            List<Product> matchingProduct = products.stream()
                    .filter(p -> p.getName().equals(purchaseItem.getProductName()))
                    .toList();

            if (matchingProduct.size() > 1) {
                Product promotionProduct = matchingProduct.stream()
                        .filter(p -> p.getPromotion().isPositive())
                        .findAny()
                        .orElseThrow(IllegalArgumentException::new);

                PromotionType promotionType = promotionProduct.getPromotion().getPromotionType();
                promotionCount = (purchaseAmount / promotionType.getTotalCount()) * promotionType.getBuyCount(); // 10 / 3 = 3 * 2 = 6
                if(promotionCount == 0) promotionCount = purchaseAmount;
                freeCount = (purchaseAmount / promotionType.getTotalCount()) * promotionType.getGetCount(); // 10 / 3 = 3 * 1 = 3

                if (promotionCount + freeCount > promotionProduct.getCount()) {
                    promotionCount = (promotionProduct.getCount() / promotionType.getTotalCount()) * promotionType.getBuyCount();
                    freeCount = (promotionProduct.getCount() / promotionType.getTotalCount()) * promotionType.getGetCount();
                }
                promotionProduct.sub(promotionCount + freeCount);

                if(purchaseAmount % promotionType.getTotalCount() == promotionType.getBuyCount()
                && promotionProduct.getCount() >= 1) {
                    if(isApproveAddFreeCount(promotionProduct.getName())) {
                        freeCount += 1;
                        promotionProduct.sub(1);
                    }
                }
                System.out.println(purchaseAmount);
                purchaseAmount -= (promotionCount + freeCount);
                System.out.println(purchaseAmount);
                if (purchaseAmount > 0) {
                    if(isDenyAddNonPromotionCount(promotionProduct.getName(), purchaseAmount)) {
                        purchaseItem.addAfter(promotionCount, nonPromotionCount, freeCount);
                        continue;
                    }
                }
            }

            if (purchaseAmount > 0) {
                Product nonPromotionProduct = matchingProduct.stream()
                        .filter(p -> !p.getPromotion().isPositive())
                        .findAny()
                        .orElseThrow(IllegalArgumentException::new);

                nonPromotionProduct.sub(purchaseAmount);
                nonPromotionCount = purchaseAmount;
            }
            purchaseItem.addAfter(promotionCount, nonPromotionCount, freeCount);
        }
    }

    private boolean isDenyAddNonPromotionCount(String productName, int count) {
        while(true) {
            try {
                System.out.println();
                System.out.println("현재 " + productName + " " +count + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
                String answer = Console.readLine();
                if(answer.equals("Y")) {
                    return false;
                }
                if (answer.equals("N")) {
                    return true;
                }
                throw new IllegalArgumentException(COMMON_ERROR_MESSAGE);
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isApproveAddFreeCount(String productName) {
        while(true) {
            try {
                System.out.println();
                System.out.println("현재 " + productName + "은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
                String answer = Console.readLine();
                if(answer.equals("Y")) {
                    return true;
                }
                if (answer.equals("N")) {
                    return false;
                }
                throw new IllegalArgumentException(COMMON_ERROR_MESSAGE);
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
