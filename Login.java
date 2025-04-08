import java.io.*;
import java.util.*;

public class Login {
    private static final String FILE_NAME = "users.csv";
    private static String currentUsername = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ensureCSVExists();

        while (true) {
            System.out.println("\n--- LOGIN SYSTEM ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    register(scanner);
                    break;
                case "2":
                    login(scanner);
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void ensureCSVExists() {
        File file = new File(FILE_NAME);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Created users.csv file.");
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        if (userExists(username)) {
            System.out.println("Username already exists.");
            return;
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "," + password + ",,"); // movieVote and snackVote empty initially
            writer.newLine();
            System.out.println("Registration successful.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    found = true;
                    currentUsername = username;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (found) {
            System.out.println("Login successful.");
            showVotingMenu(scanner);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking file: " + e.getMessage());
        }
        return false;
    }

    private static void showVotingMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Voting Menu ---");
            System.out.println("1. Vote for a movie");
            System.out.println("2. Vote for a snack");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    vote(scanner, "movie");
                    break;
                case "2":
                    vote(scanner, "snack");
                    break;
                case "3":
                    System.out.println("Exiting vote menu.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void vote(Scanner scanner, String type) {
        System.out.println("Choose a " + type + " (1-9):");
        for (int i = 1; i <= 9; i++) {
            System.out.println(i + ". " + type.substring(0, 1).toUpperCase() + type.substring(1) + " " + i);
        }

        System.out.print("Enter your choice: ");
        String input = scanner.nextLine();

        try {
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > 9) {
                System.out.println("Invalid choice. Must be between 1 and 9.");
                return;
            }

            updateUserVote(currentUsername, type, String.valueOf(choice));
            System.out.println("Your vote for " + type + " " + choice + " has been recorded.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 9.");
        }
    }

    private static void updateUserVote(String username, String type, String vote) {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username)) {

                    String movieVote = parts.length > 2 ? parts[2] : "";
                    String snackVote = parts.length > 3 ? parts[3] : "";

                    if (type.equals("movie")) {
                        movieVote = vote;
                    } else if (type.equals("snack")) {
                        snackVote = vote;
                    }

                    String newLine = parts[0] + "," + parts[1] + "," + movieVote + "," + snackVote;
                    updatedLines.add(newLine);
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
