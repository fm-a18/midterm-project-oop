public abstract class Item {
    private final String itemID;
    private final String itemName;
    private int itemQuantity;
    private double itemPrice;

    public Item(String itemID, String itemName, int itemQuantity, double itemPrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            System.out.println("Quantity cannot be negative.");
        }
        this.itemQuantity = newQuantity;
    }

    public void setItemPrice(double newPrice) {
        if (newPrice < 0) {
            System.out.println("Price cannot be negative.");
        }
        this.itemPrice = newPrice;
    }

    public void displayInfo() {
        System.out.printf("""
                        ========================================
                                    ITEM INFORMATION
                        ========================================
                         Item ID       : %s
                         Item Name     : %s
                         Category      : %s
                         Item Quantity : %d
                         Item Price    : Php %,.2f
                        ========================================
                        """,
                itemID,
                itemName,
                getCategory(),
                itemQuantity,
                itemPrice);
    }

    public abstract String getCategory();

}
