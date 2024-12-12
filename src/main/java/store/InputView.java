package store;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InputView {
    public List<PurchaseItem> readPurchaseItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String purchaseItemCandidate = Console.readLine();
        return parse(purchaseItemCandidate);
    }

    private List<PurchaseItem> parse(String purchaseItemCandidate) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        String[] split = purchaseItemCandidate.split(",");
        for (String item : split) {
            if(!Pattern.matches("\\[[가-힣]*-[0-9]*\\]+", item)) {
                throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
            }
            item = item.substring(1, item.length() - 1);
            String[] nameAndCount = item.split("-");
            PurchaseItem purchaseItem = new PurchaseItem(nameAndCount[0], Integer.parseInt(nameAndCount[1]));
            purchaseItems.add(purchaseItem);
        }
        return purchaseItems;
    }
}
