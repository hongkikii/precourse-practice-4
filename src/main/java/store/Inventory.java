package store;

import static store.Constants.COMMON_ERROR_MESSAGE;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Inventory {
    private final List<Product> products;
    private final Map<String, Promotion> promotions;
    private final Promotion nonPromotion = new Promotion("", null, null, PromotionType.NONE);

    public Inventory() {
        this.promotions = new HashMap<>();
        loadPromotion();
        this.products = new ArrayList<>();
        loadProduct();
    }

    public void printFormatted() {
        for(Product product : products) {
            System.out.println(product.getFormatted());
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    private void loadPromotion() {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/promotions.md"));
            scanner.next();
            while (scanner.hasNext()) {
                String line = scanner.next();
                String[] split = line.split(",");
                String promotionName = split[0];
                int buyCount = Integer.parseInt(split[1]);
                int getCount = Integer.parseInt(split[2]);
                PromotionType promotionType = PromotionType.getBy(buyCount, getCount);
                LocalDate startDate = LocalDate.parse(split[3], DateTimeFormatter.ISO_DATE);
                LocalDate endDate = LocalDate.parse(split[4], DateTimeFormatter.ISO_DATE);
                Promotion promotion = new Promotion(promotionName, startDate, endDate, promotionType);
                promotions.put(promotionName, promotion);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(COMMON_ERROR_MESSAGE);
        }
    }

    private void loadProduct() {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/products.md"));
            scanner.next();
            List<String> productInfos = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.next();
                productInfos.add(line);
            }
            for (int i=0; i<productInfos.size(); i++) {
                String[] split = productInfos.get(i).split(",");
                String productName = split[0];
                int price = Integer.parseInt(split[1]);
                int count = Integer.parseInt(split[2]);
                String promotionName = split[3];
                Promotion promotion = promotions.get(promotionName);
                if(promotion == null) {
                    promotion = nonPromotion;
                }
                Product product = new Product(productName, count, price, promotion);
                LocalDate today = LocalDate.now();
                if(promotion.isPositive() &&
                        (today.isBefore(promotion.getStartDate()) || today.isAfter(promotion.getEndDate()))) {
                    continue;
                }
                products.add(product);
                if (promotion.isPositive()) {
                    if(i == productInfos.size() - 1) {
                        Product nonPromotionProduct = new Product(productName, 0, price, nonPromotion);
                        products.add(nonPromotionProduct);
                        continue;
                    }
                    String nextProductName = productInfos.get(i + 1).split(",")[0];
                    if (!nextProductName.equals(productName)) {
                        Product nonPromotionProduct = new Product(productName, 0, price, nonPromotion);
                        products.add(nonPromotionProduct);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(COMMON_ERROR_MESSAGE);
        }
    }

    public void isValid(List<PurchaseItem> purchaseItems) {
        for (PurchaseItem purchaseItem : purchaseItems) {
            List<Product> matchingProduct = products.stream()
                    .filter(p -> p.getName().equals(purchaseItem.getProductName()))
                    .toList();
            if (matchingProduct.isEmpty()) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
            int totalCount = 0;
            for (Product product : matchingProduct) {
                totalCount += product.getCount();
            }
            if (totalCount < purchaseItem.getProductCount()) {
                throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }
}
