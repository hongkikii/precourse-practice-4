package store;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        OutputView outputView = new OutputView();
        outputView.showWelcomeComment();

        Inventory inventory = new Inventory();
        outputView.show(inventory);

        User user = null;
        while (user == null) {
            try {
                InputView inputView = new InputView();
                List<PurchaseItem> purchaseItemsCandidate = inputView.readPurchaseItem();
                inventory.isValid(purchaseItemsCandidate);
                user = new User(purchaseItemsCandidate);
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        user.purchase(inventory);
    }
}
