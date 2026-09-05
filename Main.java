import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static final ArrayList<Item> listOfItems = new ArrayList<>();

    public static void addItem() {
        DisplayUtils.printMenuInput("AVAILABLE CATEGORIES", "Clothing", "Electronics", "Entertainment");
        String itemCategory = DataValidations.categoryValidation(sc);

        DisplayUtils.printMenuInput(
                "ITEM ID FORMAT",
                "Clothing      | CL### (e.g., CL001)",
                "Electronics   | EL### (e.g., EL001)",
                "Entertainment | EN### (e.g., EN001)"
        );
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

    public static void updateItem() {
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

    public static void removeItem() {
        boolean isConfirmed = false;
        while (!isConfirmed) {
            Item item = getItemByID(sc, listOfItems);

            if (item == null) {
                return;
            }

            String itemName = item.getItemName();
            char confirmDeletion = DataValidations.charChoiceValidation(sc, "Are you sure you want to remove this item? (Y/N)", 'Y', 'N');
            if (confirmDeletion == 'Y') {

                listOfItems.remove(item);
                System.out.printf("Item %s has been removed from the inventory.\n", itemName);
                isConfirmed = true;
            } else {
                System.out.println("Item removal cancelled!");
                return;
            }
        }
    }

    public static void displayItemsByCategory() {
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
            System.out.printf("Category [%s] is empty!\n\n", categoryName);
            return;
        }

        DisplayUtils.tableHeader(categoryName.toUpperCase(), false);
        DisplayUtils.displayCategoryTable(filteredItems);
    }

    public static void displayAllItems() {
        DisplayUtils.tableHeader("ALL ITEMS", true);
        DisplayUtils.displayAllItemsTable(listOfItems);
    }

    public static void searchItem() {
        Item item = getItemByID(sc, listOfItems);
        if (item == null) {
            return;
        }
        item.displayInfo();
    }

    public static void sortItems() {
        if (listOfItems.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        DisplayUtils.printMenu("SORT BY", "Quantity", "Price");
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

        String sortField = (sortBy == 1) ? "QUANTITY" : "PRICE";
        String sortOrder = (order == 1) ? "ASCENDING" : "DESCENDING";

        DisplayUtils.tableHeader("SORTED BY " + sortField + " (" + sortOrder + ")", true);
        DisplayUtils.displayAllItemsTable(sortedItems);
    }

    public static void displayLowStockItems() {
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
        //listOfItems.addAll(CsvHelper.loadFromCsv("items.csv")); //for debugging

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
