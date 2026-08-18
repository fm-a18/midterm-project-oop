public class Entertainment extends Item {
    public Entertainment(String idName, String itemName, int itemQuantity, double itemPrice) {
        super(idName, itemName, itemQuantity, itemPrice);
    }

    @Override
    public String getCategory() {
        return "Entertainment";
    }
}
