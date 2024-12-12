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

    public void showPurchaseRequest() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
    }
}
