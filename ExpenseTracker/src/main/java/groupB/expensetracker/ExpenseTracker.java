/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package groupB.expensetracker;

/**
 *
 * @author USER
 */
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents a single financial transaction
class Transaction {
    LocalDate date;
    BigDecimal amount;
    String description;
    String category;

    Transaction(LocalDate date, BigDecimal amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.category = "Uncategorized";
    }
}

// Core logic of the Expense Tracker
public class ExpenseTracker {

    public static void main(String[] args) {
        // Interactive testing harness
        List<Transaction> testData = collectUserInput();
        if (testData.isEmpty()) {
            System.out.println("No transactions entered. Exiting.");
            return;
        }

        // Process data
        List<Transaction> cleanedData = normalizeAndClean(testData);
        if (!validateData(cleanedData)) {
            System.err.println("ERROR: Some transactions were invalid. Exiting.");
            return;
        }

        List<Transaction> categorizedData = categorizeTransactions(cleanedData);
        BigDecimal forecastExpense = forecastBudget(categorizedData);
        List<String> recommendations = generateRecommendations(categorizedData, forecastExpense);

        // Display results
        displayDashboard(categorizedData, forecastExpense, recommendations);

        if (checkAlerts(categorizedData)) {
            sendAlerts();
        }
    }

    // === Interactive Input Collection ===
    private static List<Transaction> collectUserInput() {
        Scanner scanner = new Scanner(System.in);
        List<Transaction> data = new ArrayList<>();

        System.out.println("Enter transactions one per line in format: YYYY-MM-DD,amount,description");
        System.out.println("Type DONE when finished.");

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("DONE")) {
                break;
            }
            String[] parts = line.split(",", 3);
            if (parts.length < 3) {
                System.err.println("Invalid format. Please use: YYYY-MM-DD,amount,description");
                continue;
            }
            try {
                LocalDate date = LocalDate.parse(parts[0]);
                BigDecimal amount = new BigDecimal(parts[1]);
                String desc = parts[2].trim();
                data.add(new Transaction(date, amount, desc));
            } catch (DateTimeParseException | NumberFormatException e) {
                System.err.println("Invalid date or amount. Please retry.");
            }
        }
        return data;
    }

    // === Data Preparation ===
    private static List<Transaction> normalizeAndClean(List<Transaction> data) {
        for (Transaction t : data) {
            t.description = t.description.trim();
        }
        return data;
    }

    private static boolean validateData(List<Transaction> data) {
        for (Transaction t : data) {
            if (t.date == null || t.amount == null) {
                return false;
            }
        }
        return true;
    }

    // === Business Logic ===
    private static List<Transaction> categorizeTransactions(List<Transaction> data) {
        for (Transaction t : data) {
            String desc = t.description.toLowerCase();
            if (desc.contains("grocery") || desc.contains("groceries")) {
                t.category = "Food";
            } else if (desc.contains("fuel") || desc.contains("petrol")) {
                t.category = "Transport";
            } else if (desc.contains("rent")) {
                t.category = "Housing";
            } else {
                t.category = "Other";
            }
        }
        return data;
    }

    private static BigDecimal forecastBudget(List<Transaction> data) {
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction t : data) {
            total = total.add(t.amount);
        }
        return total.multiply(new BigDecimal("1.10")); // +10% forecast
    }

    private static List<String> generateRecommendations(List<Transaction> data, BigDecimal forecast) {
        List<String> recs = new ArrayList<>();
        BigDecimal budgetCap = new BigDecimal("500.00");
        if (forecast.compareTo(budgetCap) > 0) {
            recs.add("Forecast (" + forecast + ") exceeds budget (" + budgetCap + ").");
            recs.add("Consider reducing non-essential spending by 10%.");
        } else {
            recs.add("Forecasted spending within budget. Good job!");
        }
        return recs;
    }

    // === Presentation ===
    private static void displayDashboard(List<Transaction> data, BigDecimal forecast, List<String> recs) {
        System.out.println("\n===== Expense Dashboard =====");
        System.out.println("Date\t\tAmount\tDescription\tCategory");
        for (Transaction t : data) {
            System.out.printf("%s\t%.2f\t%s\t%s%n",
                t.date, t.amount, t.description, t.category);
        }
        System.out.println("-----------------------------");
        System.out.printf("Forecasted Total: %.2f%n", forecast);
        System.out.println("Recommendations:");
        for (String r : recs) {
            System.out.println(" - " + r);
        }
    }

    // === Alerts ===
    private static boolean checkAlerts(List<Transaction> data) {
        BigDecimal threshold = new BigDecimal("100.00");
        for (Transaction t : data) {
            if (t.amount.compareTo(threshold) > 0) {
                return true;
            }
        }
        return false;
    }

    private static void sendAlerts() {
        System.out.println("\n🚨 ALERT: One or more transactions exceed your alert threshold!");
    }
}
