package store;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        OutputView outputView = new OutputView();
        outputView.showWelcomeComment();

        Inventory inventory = new Inventory();
        outputView.show(inventory);

        List<PurchaseItem> purchaseItems = null;
        while (purchaseItems == null) {
            try {
                InputView inputView = new InputView();
                List<PurchaseItem> purchaseItemsCandidate = inputView.readPurchaseItem();
                inventory.isValid(purchaseItemsCandidate);
                purchaseItems = purchaseItemsCandidate;
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
