import java.util.Scanner;

public class BMI_Master_WilliamLu {
    public static void main(String[] args) {
        runMaster();
    }

    public static void runMaster() {
        Scanner input = new Scanner(System.in);
        String choice = "";

        while (true) {
            System.out.println("My CSC 215 BMI Calculator Projects:");
            System.out.println("1. BMI, English");
            System.out.println("2. BMI, Metric");
            System.out.println();
            System.out.println("[ USER MANUAL ] Enter an exclamation mark ! to end.");
            System.out.print("Please enter the version you want to try: ");
            choice = input.nextLine().trim();
            String choiceLower = choice.toLowerCase();
            if (choice.equals("!")) {
                System.out.println("\nProgram ended. Thank you!");
                break;
            }
            else if (choiceLower.contains("en")) {
                System.out.println();
                BMI_CSC215_English_WilliamLu.main(null);
                System.out.println();
            }
            else if (choiceLower.contains("me")) {
                System.out.println();
                BMI_CSC215_Metric_WilliamLu.main(null);
                System.out.println();
            }
            else {
                System.out.println("\nUnrecognized version. Please try again!\n");
            }
        }
    }
}

