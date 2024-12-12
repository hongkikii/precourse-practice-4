package store;

import static store.Constants.COMMON_ERROR_MESSAGE;

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

    public boolean readMembershipApplied() {
        while(true) {
            try {
                System.out.println();
                System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
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

    public boolean notLoop() {
        while(true) {
            try {
                System.out.println();
                System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
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
}
