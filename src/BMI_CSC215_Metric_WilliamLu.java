import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

public class BMI_CSC215_Metric_WilliamLu {

    static String name;
    static int height_cm;
    static float bmi;
    static double bmi_rounded;
    static String classification;
    static float low_weight;
    static float high_weight;
    static float user_weight;

    public static void main(String[] args) {
        intro();
        questionnaire_one();
        summary();
        questionnaire_two();
        range_table();
        outro();
    }

    // INTRO TEXT//
    public static void intro() {
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("-- Welcome to:");
        System.out.println("--            BODY MASS INDEX (BMI) Computation, CSC 215, Metric version");
        System.out.println("--                                                                    by William Lu");
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    // USER QUESTIONS 01 //
    public static void questionnaire_one() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter your full name: ");
        name = input.nextLine();

        boolean valid_height = false;
        while (!valid_height) {
            System.out.print("Please enter height in centimeters for " + name + ": ");
            try {
                height_cm = input.nextInt();
                valid_height = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                input.nextLine();
            }
        }

        float weight = 0;
        boolean valid_weight = false;
        while (!valid_weight) {
            System.out.print("Please enter weight in kilograms for " + name + ": ");
            try {
                weight = input.nextFloat();
                valid_weight = true;
                user_weight = weight; // save user's weight
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                input.next();
            }
        }
        bmi = bmi_calculator(height_cm, weight);
        bmi_rounded = bmi_rounding(bmi);
    }

    // SUMMARY TEXT //
    public static void summary () {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm:ss a");
        String dateTime = now.format(formatter);
        classification = bmi_classification(bmi_rounded);
        System.out.println("\n-- SUMMARY REPORT FOR " + name);
        System.out.println("-- Date and Time: " + dateTime);
        System.out.println("-- BMI: " + bmi + " (or " + bmi_rounded +" if rounded)");
        System.out.println("-- Weight Status: " + classification);
        System.out.println("\n");
    }

    // USER QUESTIONS 02 //
    public static void questionnaire_two() {
        Scanner input = new Scanner(System.in);
        boolean valid_low_weight = false;
        while (!valid_low_weight) {
            System.out.print("Please enter low weight for " + name + " (kg): ");
            try {
                low_weight = input.nextFloat();
                valid_low_weight = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                input.next();
            }
        }
        boolean valid_high_weight = false;
        while (!valid_high_weight) {
            System.out.print("Please enter high weight for " + name + " (kg): ");
            try {
                high_weight = input.nextFloat();
                valid_high_weight = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                input.next();
            }
        }
    }

    // BMI CALCULATION (Metric): BMI = (weight (kg) * 10000) / (height_cm²)
    public static float bmi_calculator(int height_cm, float weight) {
        bmi = (weight * 10000.0f) / (height_cm * height_cm);
        return bmi;
    }

    // RANGE TABLE //
    public static void range_table() {
        
        // for yellow highlighting 
        String YELLOW_BACKGROUND_BLACK_TEXT = "\u001B[48;5;11m\u001B[30m";
        String TEXT_RESET = "\u001b[0m";
        //

        //for table header
        System.out.println("----------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-22s |\n", "WEIGHT", "BMI", "WEIGHT STATUS");
        System.out.println("----------------------------------------------------");
        //
        
        //
        DecimalFormat bmi_format = new DecimalFormat("#.#####"); //formats BMI to up to 5 decimals
        float increment = 2.50f;  //how much to increment from starting weight
        int status_cell_width = 22;  //width of 'weight' column
        boolean first_row = true;  //to see if (low) should be printed
        boolean user_printed = false;  //if (this) is printed on user's weight on table
        float prev = low_weight;  //remembers the last weight printed, to detect whether or not the user's weight would fit in between two of the rows 
        //
        
        //main loop
        for (float w = low_weight; w <= high_weight + 0.0001f; w += increment) {  //start at 'low_weight' and goes up by increment untill it reaches 'high_weight'
            if (w + increment > high_weight + 0.0001f && w < high_weight) {  //if the weight (w) + the 'increment' exceeds the user's high weight 
                w = high_weight;  //sets the weight (w) to the high weight.
            }
        //
            

            //if statement to insert the users weight/bmi/status inbetween rows
            if (!user_printed && user_weight > prev && user_weight < w) {  //if user_printed has not been printed AND the user's weight is between the weight of the previous row and current row in the loop
                double user_bmi = (user_weight * 10000.0) / (height_cm * height_cm); //calculates the user's bmi
                String user_bmi_string = bmi_format.format(user_bmi); //formats the bmi using the DecimalFormat bmi_format from above
                String user_status = bmi_classification(user_bmi);  //calls the bmi_classification method to see where the bmi status falls
                String user_marker = " (THIS)";  //defines the text taht will be append to the user's row
                int length = user_status.length() + user_marker.length();  //to calculate the total number of characters in the user_status + (this) so that the table can be formatted properly
                String status_formatted = (length < status_cell_width)  //if the combined text is shorted than the int status_cell_width (from above) adds extra spaces to format properly
                        ? user_status + String.format("%" + (status_cell_width - length) + "s", "") + user_marker
                        : user_status + user_marker;
                System.out.printf("| %-10.2f | %-10s | %-22s |\n", user_weight, user_bmi_string, status_formatted); //prints the user's weight, bmi, and status row. Formatted properly
                user_printed = true; //sets the boolean to true so that this if statment closes and won't print the user's row again
            }
            //

            //
            double local_bmi = (w * 10000.0) / (height_cm * height_cm);  //calculates bmi for the current incremented weight
            String bmi_string = bmi_format.format(local_bmi);  //formats it 
            String base_status = bmi_classification(local_bmi);  //calls thge bmi_classification method to see where the bmi status falls
            String marker = "";  //prepares a string for whenever you need to print (low)/(high), the marker is always there 
            //

            //
            if (first_row) {  //if the weight is the very first iteration of the loop set  
                marker = YELLOW_BACKGROUND_BLACK_TEXT + "(LOW)" + TEXT_RESET;  //String marker (from above) = "(LOW)"
                first_row = false;  //sets boolean to false so that this if statment closes and will not print "(LOW)" again
            }
            if (Math.abs(w - high_weight) < 0.001f) { //if the absolute value of (weight (w) - high_weight) is less than 0.001
                marker = YELLOW_BACKGROUND_BLACK_TEXT + "(HIGH)" + TEXT_RESET; //sets the String marker = "(HIGH)"
            }
            if (Math.abs(w - user_weight) < 0.001f) {  //if the current weight matches the user's weight
                marker += " (THIS)";  //sets marker to "(THIS)"
                user_printed = true;  //closes if statement so that it will not print "(THIS)" again
            }
            //
            
            String marker_no_ansi = marker.replaceAll("\u001B\\[[;\\d]*m", "");  //removes ANSI escape codes (color) from marker to be able to determine character length of text
            int total_length = base_status.length() + marker_no_ansi.length();  //finds out how many visable characters are in status + marker
            String status_formatted = (total_length < status_cell_width)  //if status + marker < status_cell_width = 22 then it will add extra spaces to align appends to the right
                    ? base_status + String.format("%" + (status_cell_width - total_length) + "s", "") + marker
                    : base_status + marker;
            
            System.out.printf("| %-10.2f | %-10s | %-22s |\n", w, bmi_string, status_formatted);  //prints the current iteration of the row
            prev = w;  //stores cuurent weight as the previous weight (see line 143)
        }
        System.out.println("----------------------------------------------------");
    }

    // ROUNDING CALCULATION //
    public static double bmi_rounding(float bmi) {
        return bmi_rounded = Math.round(bmi * 10.0) / 10.0;
    }

    // CLASSIFIES BMI
    public static String bmi_classification(double bmi) {
        if (bmi < 18.5) {
            classification = "Underweight";
        } else if (bmi <= 24.9) {
            classification = "Healthy Weight";
        } else if (bmi <= 29.9) {
            classification = "Overweight";
        } else {
            classification = "Obesity";
        }
        return classification;
    }

    // OUTRO TEXT //
    public static void outro() {
        System.out.println("\nThe SFSU Mashouf Wellness Center is at 755 Font Blvd.");
        System.out.println("\n----------------------------------------------------------------------------------------------");
        System.out.println("-- Thank you for using my program, " + name + "!");
        System.out.println("-- Ear-esistible!!!");
        System.out.println("------------------------------------------------------------------------------------------------");
    }
}
