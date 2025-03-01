import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;


public class BMI_CSC215_English_WilliamLu {

    static String name;
    static int height_feet;
    static int height_inches;
    static float bmi;
    static double bmi_rounded;
    static String classification;
    static float low_weight;
    static float high_weight;
    static int total_height;
    static float user_weight;

    public static void main(String[] args) {
        intro();
        questionnaire_one();
        summary();
        questionnaire_two();
        range_table();
        outro();
    }

    //INTRO TEXT//
    public static void intro() {
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("-- Welcome to:");
        System.out.println("--            BODY MASS INDEX (BMI) Computation, CSC 215, English version");
        System.out.println("--                                                                    by William Lu");
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    //USER QUESTIONS 01
    public static void questionnaire_one() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter your full name: ");
        name = input.nextLine();
        boolean valid_height = false;
        while (!valid_height) {
            System.out.print("Please enter Height in feet and inches for " + name + ":");
            try {
                height_feet = input.nextInt();
                height_inches = input.nextInt();
                valid_height = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                input.nextLine();
            }
        }
        float weight = 0;
        boolean valid_weight = false;
        while (!valid_weight) {
            System.out.print("Please enter weight in pounds for " + name + ":");
            try {
                weight = input.nextFloat();
                valid_weight = true;
                user_weight = weight;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                input.next();
            }
        }
        bmi = bmi_calculator(height_feet, height_inches, weight);
        bmi_rounded = bmi_rounding(bmi);
    }

    //SUMMARY TEXT//
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

    //USER QUESTION 02
    public static void questionnaire_two() {
        Scanner input = new Scanner(System.in);
        boolean valid_low_weight = false;
        while (!valid_low_weight) {
            System.out.print("Please enter low weight for " + name + ":");
            try {
                low_weight = input.nextFloat();
                valid_low_weight = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                input.nextFloat();
            }
        }
        boolean valid_high_weight = false;
        while (!valid_high_weight) {
            System.out.print("Please enter high weight for " + name + ":");
            try {
                high_weight = input.nextFloat();
                valid_high_weight = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    //BMI CALCULATION//
    public static float bmi_calculator(int height_feet, int height_inches, float weight) {
        int total_height_inches = (height_feet * 12) + height_inches;
        total_height = total_height_inches;
        bmi = (weight * 703.0f) / (total_height_inches * total_height_inches);
        return bmi;
    }

    //RANGE TABLE//
    public static void range_table() {
        System.out.println("--------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-20s |\n", "WEIGHT", "BMI", "WEIGHT STATUS");
        System.out.println("--------------------------------------------------");
        DecimalFormat bmi_format = new DecimalFormat("#.#####");
        float step = 5.00f;
        int status_cell_width = 20;
        boolean first_row = true;
        boolean user_printed = false;
        float prev = low_weight;
        for (float w = low_weight; w <= high_weight + 0.0001f; w += step) {
            if (w + step > high_weight + 0.0001f && w < high_weight) {
                w = high_weight;
            }
            if (!user_printed && user_weight > prev && user_weight < w) {
                double user_bmi = (user_weight * 703.0) / (total_height * total_height);
                String user_bmi_string = bmi_format.format(user_bmi);
                String user_status = bmi_classification(user_bmi);
                String user_marker = " (THIS)";
                int len = user_status.length() + user_marker.length();
                String status_formatted = (len < status_cell_width)
                        ? user_status + String.format("%" + (status_cell_width - len) + "s", "") + user_marker
                        : user_status + user_marker;
                System.out.printf("| %-10.2f | %-10s | %-20s |\n", user_weight, user_bmi_string, status_formatted);
                user_printed = true;
            }
            double localBmi = (w * 703.0) / (total_height * total_height);
            String bmi_string = bmi_format.format(localBmi);
            String base_status = bmi_classification(localBmi);
            String marker = "";
            if (first_row) {
                marker = " (LOW)";
                first_row = false;
            }
            if (Math.abs(w - high_weight) < 0.001f) {
                marker = " (HIGH)";
            }
            if (Math.abs(w - user_weight) < 0.001f) {
                marker += " (THIS)";
                user_printed = true;
            }
            int total_length = base_status.length() + marker.length();
            String status_formatted = (total_length < status_cell_width)
                    ? base_status + String.format("%" + (status_cell_width - total_length) + "s", "") + marker
                    : base_status + marker;
            System.out.printf("| %-10.2f | %-10s | %-20s |\n", w, bmi_string, status_formatted);

            prev = w;
        }
        System.out.println("--------------------------------------------------");
    }

    //ROUNDING CALCULATION//
    public static double bmi_rounding(float bmi) {
        return bmi_rounded = Math.round(bmi * 10.0) / 10.0;
    }

    //CLASSIFIES BMI//
    public static String bmi_classification(double bmi_rounded) {
        if (bmi_rounded <= 18.5) {
            classification = "Underweight";
        } else if (bmi_rounded <= 24.9) {
            classification = "Healthy Weight";
        } else if (bmi_rounded <= 29.9) {
            classification = "Overweight";
        }else {
            classification = "Obesity";
        }
        return classification;
    }

    //OUTRO TEXT//
    public static void outro() {
        System.out.println("\nThe SFSU Mashouf Wellness Center is at 755 Font Blvd.");
        System.out.println("\n-----------------------------------------------------------------------------------------------");
        System.out.println("-- Thank you for using my program, "+ name +"!");
        System.out.println("-- Ear-esistible!!!");
        System.out.println("-------------------------------------------------------------------------------------------- ---");
    }
}


