import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    private static final ArrayList<Item> listOfItems = new ArrayList<>();

    private static void addItem() {
        DisplayUtils.printMenuInput("AVAILABLE CATEGORIES","Clothing", "Electronics", "Entertainment");
        String itemCategory = DataValidations.categoryValidation(sc);
        String itemID = DataValidations.validateItemID(sc, listOfItems);
        String itemName = DataValidations.validateItemName(sc);
        int itemQuantity = DataValidations.validateInt(sc, "Item Quantity");
        double itemPrice = DataValidations.validatePrice(sc);

        Item newItem;
        switch (itemCategory) {
            case "Clothing" -> newItem = new Clothing(itemID, itemName, itemQuantity, itemPrice);
            case "Electronics" -> newItem = new Electronics(itemID, itemName, itemQuantity, itemPrice);
            case "Entertainment" -> newItem = new Entertainment(itemID, itemName, itemQuantity, itemPrice);
            default -> throw new IllegalStateException("Unexpected Category Type: " + itemCategory);
        }

        listOfItems.add(newItem);
        newItem.displayInfo();
        System.out.println("Item Added successfully!\n");
    }

    public static Item getItemByID(Scanner sc, ArrayList<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            return null;
        }

        String inputID = DataValidations.validateItemID(sc);

        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(inputID)) {
                return item;
            }
        }

        System.out.println("Item not found.");
        return null;
    }

    private static void updateItem() {
        Item item = getItemByID(sc, listOfItems);
        if (item == null) {
            return;
        }
        DisplayUtils.printMenu("EDIT", "Quantity", "Price");
        int editChoice = DataValidations.intChoiceValidation(sc, "Edit Item", 1, 2);

        switch (editChoice) {
            case 1 -> {
                int oldQuantity = item.getItemQuantity();
                int newQuantity = DataValidations.validateIntForUpdate(sc, "New Quantity");
                item.setItemQuantity(newQuantity);

                DisplayUtils.printSummaryBox("QUANTITY UPDATED", new String[][]{
                        {"Item", item.getItemName()},
                        {"Old Quantity", String.valueOf(oldQuantity)},
                        {"New Quantity", String.valueOf(newQuantity)}
                });
            }
            case 2 -> {
                double oldPrice = item.getItemPrice();
                double newPrice = DataValidations.validatePrice(sc);
                item.setItemPrice(newPrice);

                DisplayUtils.printSummaryBox("PRICE UPDATED", new String[][]{
                        {"Item", item.getItemName()},
                        {"Old Price", String.format("Php %,.2f", oldPrice)},
                        {"New Price", String.format("Php %,.2f", newPrice)}
                });
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
        System.out.printf("Item [%s] has been removed from the inventory.\n", itemName);
    }

    private static void displayItemsByCategory() {
        if (listOfItems.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        DisplayUtils.printMenuInput("AVAILABLE CATEGORIES", "Clothing", "Electronics", "Entertainment");
        String categoryName = DataValidations.categoryValidation(sc);

        ArrayList<Item> filteredItems = new ArrayList<>();
        for (Item item : listOfItems) {
            if (item.getCategory().equalsIgnoreCase(categoryName)) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            System.out.printf("Category [%s] does not exist!\n\n", categoryName);
            return;
        }

        DisplayUtils.tableHeader(categoryName.toUpperCase(), false);
        DisplayUtils.displayCategoryTable(filteredItems);
    }

    private static void displayAllItems() {
        DisplayUtils.tableHeader("ALL ITEMS", true);
        DisplayUtils.displayAllItemsTable(listOfItems);
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

        DisplayUtils.printMenu("EDIT", "Quantity", "Price");
        int sortBy = DataValidations.intChoiceValidation(sc, "Sort By", 1, 2);

        DisplayUtils.printMenu("SORTING ORDER", "Ascending", "Descending");
        int order = DataValidations.intChoiceValidation(sc, "Order", 1, 2);

        Comparator<Item> comparator = (sortBy == 1)
                ? Comparator.comparingInt(Item::getItemQuantity)
                : Comparator.comparingDouble(Item::getItemPrice);

        if (order == 2) {
            comparator = comparator.reversed();
        }

        ArrayList<Item> sortedItems = new ArrayList<>(listOfItems);
        sortedItems.sort(comparator);

        DisplayUtils.tableHeader("SORTED ITEMS", true);
        DisplayUtils.displayAllItemsTable(sortedItems);
    }

    private static void displayLowStockItems() {
        ArrayList<Item> lowStockItems = new ArrayList<>();
        for (Item item : listOfItems) {
            if (item.getItemQuantity() <= 5) {
                lowStockItems.add(item);
            }
        }
        DisplayUtils.tableHeader("LOW STOCK ITEMS", true);
        DisplayUtils.displayAllItemsTable(lowStockItems);
    }

    public static void main(String[] args) {
        System.out.println("Welcome!");

        boolean isDone = false;
        while (!isDone) {
            DisplayUtils.printMenu("INVENTORY MANAGEMENT SYSTEM",
                    "Add Item",
                    "Update Item",
                    "Remove Item",
                    "Display Items by Category",
                    "Display All Items",
                    "Search Item",
                    "Sort Items",
                    "Display Low Stock Items",
                    "Exit"
            );

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
