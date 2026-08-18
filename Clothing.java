public class Clothing extends Item {
    public Clothing(String idName, String itemName, int itemQuantity, double itemPrice) {
        super(idName, itemName, itemQuantity, itemPrice);
    }

    @Override
    public String getCategory() {
        return "Clothing";
    }
}
