public class Electronics extends Item {
    public Electronics(String idName, String itemName, int itemQuantity, double itemPrice) {
        super(idName, itemName, itemQuantity, itemPrice);
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }
}
