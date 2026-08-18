import java.util.ArrayList;

public class DisplayUtils {
    public static final int TABLE_WIDTH = 100;
    public static final int MENU_WIDTH = 40;

    private static void centeredTitle(String tableName, int width) {
        System.out.println("=".repeat(width));
        int padding = (width - tableName.length()) / 2;
        System.out.println(" ".repeat(Math.max(0, padding)) + tableName);
        System.out.println("=".repeat(width));
    }

    public static void tableHeader(String tableName, boolean showCategory) {
        centeredTitle(tableName, TABLE_WIDTH);

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

        System.out.println("-".repeat(TABLE_WIDTH));
    }

    public static void displayAllItemsTable(ArrayList<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            System.out.println("-".repeat(TABLE_WIDTH));
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

        System.out.println("-".repeat(TABLE_WIDTH));
    }

    public static void displayCategoryTable(ArrayList<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            System.out.println("-".repeat(TABLE_WIDTH));
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

        System.out.println("-".repeat(TABLE_WIDTH));
    }

    public static void printSummaryBox(String tableName, String[][] rows) {
        int labelWidth = 0;
        for (String[] row : rows) {
            labelWidth = Math.max(labelWidth, row[0].length());
        }

        centeredTitle(tableName, MENU_WIDTH);
        for (String[] row : rows) {
            System.out.printf(" %-" + labelWidth + "s : %s%n", row[0], row[1]);
        }
        System.out.println("=".repeat(MENU_WIDTH));
    }

    public static void printMenu(String tableName, String... options) {
        centeredTitle(tableName, MENU_WIDTH);

        for (int i = 0; i < options.length; i++) {
            System.out.printf(" [%d] %s%n", i + 1, options[i]);
        }
        System.out.println("=".repeat(MENU_WIDTH));
    }

    public static void printMenuInput(String tableName, String... options) {
        centeredTitle(tableName, MENU_WIDTH);

        for (String option : options) {
            System.out.printf(" • %s%n", option);
        }
        System.out.println("=".repeat(MENU_WIDTH));
    }
}