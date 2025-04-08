import java.util.Scanner;

public class Home {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What would you like to vote on?");
        System.out.println("1. Movie vote");
        System.out.println("2. Food vote");
        System.out.print("Enter your choice (1 or 2): ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Movie Vote");

                break;
            case 2:
                System.out.println("Food vote");

                break;
            default:
                System.out.println("Invalid choice. Please enter 1 or 2.");
        }

        scanner.close();
    }
}