
import java.util.Scanner;
public class Food {
    public static void main(String[]args){
        int choice;
        String answer;
        String food;
        Scanner input = new Scanner(System.in);
System.out.println("The food on select is...\n Popcorn\nChocolates\nSweets.");

while (true) { System.out.println("Press 1 for Popcorn, 2 for Chocolates and 3 for Sweets:");
    choice = input.nextInt();
try {
    if (choice == 1) {
        System.out.println("You chose Popcorn! ");
        break;
    } else if (choice == 2) {
        System.out.println("You chose Choclate! ");
        break;
    } else if (choice == 3) {
        System.out.println("You chose Sweets!");
        break;
    } else {
        throw new IllegalArgumentException("You must Choose out of the options 1, 2 or 3. These are the food options for the next movie night. There is an option to later to input a snack selection");
    }}
    catch (IllegalArgumentException e){
        System.out.println(e.getMessage());
    } }
    System.out.println("Would you like to make any snack suggestions\nPlease enter Yes or No");
    answer = input.next();
    switch (answer.toLowerCase()){case "yes": System.out.println("What food would you like?");
        food = input.next();
        System.out.printf("Your choice of %s will gladly be taken into consideration :).", food);
        break;
        case "no": System.out.printf("Perfect! Good that you are happy :).");
    }
    
    }
}
