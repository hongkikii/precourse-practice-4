package store;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        OutputView outputView = new OutputView();
        outputView.showWelcomeComment();

        Inventory inventory = new Inventory();
        outputView.show(inventory);

        outputView.showPurchaseRequest();
    }
}
