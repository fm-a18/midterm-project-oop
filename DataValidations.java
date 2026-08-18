import java.util.ArrayList;
import java.util.Scanner;

public class DataValidations {
    public static String validateItemID(Scanner sc, ArrayList<Item> items) {
        boolean isValidated = false;
        String input = "";

        while (!isValidated) {
            System.out.print("Item ID: ");
            try {
                input = sc.nextLine().trim();
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Error: No input available. Please try again.");
                continue;
            }

            if (input.isEmpty()) {
                System.out.println("Error: Item ID cannot be empty.");
            } else if (!input.matches("^[A-Za-z0-9_-]{3,20}$")) {
                System.out.println("Error: Item ID must contain only letters and numbers (3-20 characters).");
            } else if (itemNumberExists(input, items)) {
                System.out.println("Error: Item ID already exists.");
            } else {
                isValidated = true;
            }
        }
        return input;
    }

    public static boolean itemNumberExists(String itemID, ArrayList<Item> items) {
        boolean found = false;
        for (Item item : items) {
            if (item.getItemID().equals(itemID)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static String validateItemID(Scanner sc) {
        boolean isValidated = false;
        String input = "";

        while (!isValidated) {
            System.out.print("Item ID: ");
            try {
                input = sc.nextLine().trim();
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Error: No input available. Please try again.");
                continue;
            }

            if (input.isEmpty()) {
                System.out.println("Error: Item ID cannot be empty.");
            } else if (!input.matches("^[A-Za-z0-9_-]{3,20}$")) {
                System.out.println("Error: Item ID must contain only letters and numbers (3-20 characters).");
            } else {
                isValidated = true;
            }
        }
        return input;
    }

    public static String validateItemName(Scanner sc) {
        boolean isValidated = false;
        String input = "";

        while (!isValidated) {
            System.out.print("Item Name: ");
            try {
                input = sc.nextLine().trim();
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Error: No input available. Please try again.");
                continue;
            }

            if (input.isEmpty()) {
                System.out.println("Error: Item Name cannot be empty.");
            } else if (!input.matches("^[A-Za-z0-9 ]+$")) {
                System.out.println("Error: Item Name must contain only letters, numbers, and spaces (2-50 characters).");
            } else {
                isValidated = true;
            }
        }
        return input;
    }

    public static double validatePrice(Scanner sc) { //limit
        boolean isValidated = false;
        double value = 0;
        while (!isValidated) {
            System.out.print("Price: ");
            String input = sc.nextLine().trim();

            if (!input.matches("(0|[1-9][0-9]*)(\\.[0-9]+)?")) {
                System.out.println("Error: Please enter a valid number (e.g., 350, 400.5).");
                continue;
            }

            try {
                value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Error: Price must be greater than 0.");
                } else {
                    isValidated = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number (e.g., 350, 400, 400.5).");
            }
        }
        return value;
    }

    public static int validateInt(Scanner sc, String prompt) { //limit
        int value = 0;
        boolean isValidated = false;
        while (!isValidated) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine().trim();

            if (!input.matches("[1-9][0-9]*")) {
                System.out.println("Error: Please enter whole numbers only.");
                continue;
            }

            try {
                value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.printf("Error: %s must be greater than 0.\n", prompt);
                } else {
                    isValidated = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
        return value;
    }

    public static int intChoiceValidation(Scanner sc, String prompt, int... choices) {
        int value = 0;
        boolean isValidated = false;
        while (!isValidated) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine().trim();

            if (!input.matches("[1-9][0-9]*")) {
                System.out.println("Error: Please enter digits only.");
                continue;
            }

            try {
                value = Integer.parseInt(input);
                boolean found = false;
                for (int choice : choices) {
                    if (value == choice) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    isValidated = true;
                } else {
                    printValidChoices(choices);
                }
            } catch (NumberFormatException e) {
                printValidChoices(choices);
            }
        }
        return value;
    }

    public static void printValidChoices(int... choices) { //int values
        System.out.print("Error: Please select only from (");
        for (int i = 0; i < choices.length; i++) {
            System.out.print(choices[i]);
            if (i < choices.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(").");
    }

}