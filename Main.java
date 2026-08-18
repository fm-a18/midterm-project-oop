import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static final int tableWidth = 100;
    private static final ArrayList<Item> listOfItems = new ArrayList<>();

    public static void tableHeader(String tableName, boolean showCategory) {
        int tableNameLength = tableName.length();
        int padding = (tableWidth - tableNameLength) / 2;

        System.out.println("=".repeat(tableWidth));
        System.out.println(" ".repeat(Math.max(0, padding)) + tableName);
        System.out.println("=".repeat(tableWidth));

        if (showCategory) {
            System.out.printf(
                    "%-10s | %-30s | %-15s | %-15s | %-15s%n",
                    "ID", "Name", "Quantity", "Price", "Category"
            );
        } else {
            System.out.printf(
                    "%-10s | %-30s | %-15s | %-15s%n",
                    "ID", "Name", "Quantity", "Price"
            );
        }

        System.out.println("-".repeat(tableWidth));
    }

    public static void displayAllItemsTable(ArrayList<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            System.out.println("-".repeat(tableWidth));
            return;
        }

        for (Item item : items) {
            System.out.printf(
                    "%-10s | %-30s | %-15d | %-15s | %-15s%n",
                    item.getItemID(),
                    item.getItemName(),
                    item.getItemQuantity(),
                    String.format("Php %,.2f", item.getItemPrice()),
                    item.getCategory()
            );
        }

        System.out.println("-".repeat(tableWidth));
    }

    public static void displayCategoryTable(ArrayList<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            System.out.println("-".repeat(tableWidth));
            return;
        }

        for (Item item : items) {
            System.out.printf(
                    "%-10s | %-30s | %-15d | %-15s%n",
                    item.getItemID(),
                    item.getItemName(),
                    item.getItemQuantity(),
                    String.format("Php %,.2f", item.getItemPrice())
            );
        }

        System.out.println("-".repeat(tableWidth));
    }

    private static void addItem() {
        System.out.println("""
                ========================================
                        AVAILABLE CATEGORIES
                ========================================
                 [1] Clothing
                 [2] Electronics
                 [3] Entertainment
                ========================================
                """);
        int itemCategory = DataValidations.intChoiceValidation(sc, "Category Type", 1, 2, 3);
        String itemID = DataValidations.validateItemID(sc, listOfItems);
        String itemName = DataValidations.validateItemName(sc);
        int itemQuantity = DataValidations.validateInt(sc, "Item Quantity");
        double itemPrice = DataValidations.validatePrice(sc);

        Item newItem;
        switch (itemCategory) {
            case 1 -> newItem = new Clothing(itemID, itemName, itemQuantity, itemPrice);
            case 2 -> newItem = new Electronics(itemID, itemName, itemQuantity, itemPrice);
            case 3 -> newItem = new Entertainment(itemID, itemName, itemQuantity, itemPrice);
            default -> throw new IllegalStateException("Unexpected Category Type: " + itemCategory);
        }

        listOfItems.add(newItem);
        newItem.displayInfo();

    }

    public static Item getItemByID(Scanner sc, ArrayList<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items available.");
            return null;
        }

        String inputID = DataValidations.validateItemID(sc);

        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(inputID)) {
                return item;
            }
        }

        System.out.println("Item ID not found.");
        return null;
    }

    private static void updateItem() {
        Item item = getItemByID(sc, listOfItems);
        if (item == null) {
            return;
        }
        System.out.println("""
                ========================================
                                 EDIT
                ========================================
                 [1] Quantity
                 [2] Price
                ========================================
                """);
        int editChoice = DataValidations.intChoiceValidation(sc, "Edit Item", 1, 2);

        switch (editChoice) {
            case 1 -> {
                int oldQuantity = item.getItemQuantity();
                int newQuantity = DataValidations.validateInt(sc, "New Quantity");
                item.setItemQuantity(newQuantity);
                System.out.printf("""
                        ========================================
                                    QUANTITY UPDATED
                        ========================================
                         Item         : %s
                         Old Quantity : %d
                         New Quantity : %d
                        ========================================
                        %n""", item.getItemName(), oldQuantity, newQuantity);
            }
            case 2 -> {
                double oldPrice = item.getItemPrice();
                double newPrice = DataValidations.validateInt(sc, "New Price");
                item.setItemPrice(newPrice);
                System.out.printf("""
                        ========================================
                                      ITEM UPDATED
                        ========================================
                         Item      : %s
                         Old Price : %.2f
                         New Price : %.2f
                        ========================================
                        %n""", item.getItemName(), oldPrice, newPrice);
            }
        }
    }

    private static void removeItem() {
        Item item = getItemByID(sc, listOfItems);
        if (item == null) {
            return;
        }
        String itemName = item.getItemName();
        listOfItems.remove(item);
        System.out.printf("%s has been removed from the inventory.\n", itemName);
    }

    private static void displayItemsByCategory() {
        if (listOfItems.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        System.out.println("""
                ========================================
                           AVAILABLE CATEGORIES
                ========================================
                 [1] Clothing
                 [2] Electronics
                 [3] Entertainment
                ========================================
                """);
        int itemCategory = DataValidations.intChoiceValidation(sc, "Category Type", 1, 2, 3);

        String categoryName = switch (itemCategory) {
            case 1 -> "Clothing";
            case 2 -> "Electronics";
            case 3 -> "Entertainment";
            default -> throw new IllegalStateException("Unexpected Category Type: " + itemCategory);
        };

        ArrayList<Item> filteredItems = new ArrayList<>();
        for (Item item : listOfItems) {
            if (item.getCategory().equalsIgnoreCase(categoryName)) {
                filteredItems.add(item);
            }
        }

        tableHeader(categoryName.toUpperCase(), false);
        displayCategoryTable(filteredItems);
    }

    private static void displayAllItems() {
        tableHeader("ALL ITEMS", true);
        displayAllItemsTable(listOfItems);
    }

    private static void searchItem() {
        Item item = getItemByID(sc, listOfItems);
        if (item == null) {
            return;
        }
        item.displayInfo();
    }

    private static void sortItems() {
        if (listOfItems.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        System.out.println("""
                ========================================
                                SORT BY
                ========================================
                 [1] Quantity
                 [2] Price
                ========================================
                """);
        int sortBy = DataValidations.intChoiceValidation(sc, "Sort By", 1, 2);
        System.out.println("""
                ========================================
                             SORTING ORDER
                ========================================
                 [1] Ascending
                 [2] Descending
                ========================================
                """);
        int order = DataValidations.intChoiceValidation(sc, "Order", 1, 2);

        Comparator<Item> comparator = (sortBy == 1)
                ? Comparator.comparingInt(Item::getItemQuantity)
                : Comparator.comparingDouble(Item::getItemPrice);

        if (order == 2) {
            comparator = comparator.reversed();
        }

        ArrayList<Item> sortedItems = new ArrayList<>(listOfItems);
        sortedItems.sort(comparator);

        tableHeader("SORTED ITEMS", true);
        displayAllItemsTable(sortedItems);
    }

    private static void displayLowStockItems() {
        ArrayList<Item> lowStockItems = new ArrayList<>();
        for (Item item : listOfItems){
            if(item.getItemQuantity() <= 5){
                lowStockItems.add(item);
            }
        }
        tableHeader("LOW STOCK ITEMS", true);
        displayAllItemsTable(lowStockItems);
    }

    public static void main(String[] args) {
        System.out.println("Welcome!");

        boolean isDone = false;
        while (!isDone) {
            System.out.println("""
                    ==================================================
                                INVENTORY MANAGEMENT SYSTEM
                    ==================================================
                    [1] Add Item
                    [2] Update Item
                    [3] Remove Item
                    [4] Display Items by Category
                    [5] Display All Items
                    [6] Search Item
                    [7] Sort Items
                    [8] Display Low Stock Items
                    [9] Exit
                    ==================================================
                    """);

            int menuChoice = DataValidations.intChoiceValidation(sc, "Select Option", 1, 2, 3, 4, 5, 6, 7, 8, 9);
            switch (menuChoice) {
                case 1 -> addItem();
                case 2 -> updateItem();
                case 3 -> removeItem();
                case 4 -> displayItemsByCategory();
                case 5 -> displayAllItems();
                case 6 -> searchItem();
                case 7 -> sortItems();
                case 8 -> displayLowStockItems();
                case 9 -> isDone = true;
            }
        }
        sc.close();
        System.out.println("Closing Program.");
    }
}
