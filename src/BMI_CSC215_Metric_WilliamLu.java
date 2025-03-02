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
        print_intro();
        print_questionnaire_one();
        print_summary();
        print_questionnaire_two();
        print_range_table();
        print_outro();
    }

    // INTRO TEXT//
    public static void print_intro() {
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("-- Welcome to:");
        System.out.println("--            BODY MASS INDEX (BMI) Computation, CSC 215, Metric version");
        System.out.println("--                                                                    by William Lu");
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    // USER QUESTIONS 01 //
    public static void print_questionnaire_one() {
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
        bmi = bmi_calculation(height_cm, weight);
        bmi_rounded = bmi_rounding(bmi);
    }

    // SUMMARY TEXT //
    public static void print_summary () {
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
    public static void print_questionnaire_two() {
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
    public static float bmi_calculation(int height_cm, float weight) {
        bmi = (weight * 10000.0f) / (height_cm * height_cm);
        return bmi;
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

    // BMI FORMATTING //
    public static String format_BMI(double bmi) {
        String currentClassification = bmi_classification(bmi);
        DecimalFormat df;
        if (currentClassification.equals("Underweight")) {
            df = new DecimalFormat("#.##");       // 2 decimal places
        } else if (currentClassification.equals("Healthy Weight")) {
            df = new DecimalFormat("#.###");      // 3 decimal places
        } else if (currentClassification.equals("Overweight")) {
            df = new DecimalFormat("#.####");     // 4 decimal places
        } else { // Obesity
            df = new DecimalFormat("#.#####");    // 5 decimal places
        }
        return df.format(bmi);
    }

    // RANGE TABLE //
    public static void print_range_table() {

        // for yellow highlight
        String YELLOW_BACKGROUND_BLACK_TEXT = "\u001B[48;5;11m\u001B[30m";
        String TEXT_RESET = "\u001b[0m";
        //

        //header of table
        System.out.println("----------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-22s |\n", "WEIGHT", "BMI", "WEIGHT STATUS");
        System.out.println("----------------------------------------------------");
        DecimalFormat bmi_format = new DecimalFormat("#.#####");
        //

        float increment = 2.5f; //increment of each row
        int status_cell_width = 22; //sets 'weight column' width
        boolean first_row = true; //for printing '(low)' on table's first row
        boolean user_printed = false; //for printing '(this)' on users weight on table
        float prev = low_weight; //remembers last weight printed, used to detect if user's weight can fit inbetween

        for (float w = low_weight; w <= high_weight + 0.0001f; w += increment) {
            if (w + increment > high_weight + 0.0001f && w < high_weight) {
                w = high_weight;
            }
            if (!user_printed && user_weight > prev && user_weight < w) {
                double user_bmi = (user_weight * 10000.0) / (height_cm * height_cm);
                String user_bmi_string = format_BMI(user_bmi);
                String user_status = bmi_classification(user_bmi);
                String user_marker = " (THIS)";
                int length = user_status.length() + user_marker.length();
                String status_formatted = (length < status_cell_width)
                        ? user_status + String.format("%" + (status_cell_width - length) + "s", "") + user_marker
                        : user_status + user_marker;
                System.out.printf("| %-10.2f | %-10s | %-22s |\n", user_weight, user_bmi_string, status_formatted);
                user_printed = true;
            }
            double local_bmi = (w * 10000.0) / (height_cm * height_cm);
            String bmi_string = format_BMI(local_bmi);
            String base_status = bmi_classification(local_bmi);
            String marker = "";
            if (first_row) {
                marker = YELLOW_BACKGROUND_BLACK_TEXT + "(LOW)" + TEXT_RESET;
                first_row = false;
            }
            if (Math.abs(w - high_weight) < 0.001f) {
                marker = YELLOW_BACKGROUND_BLACK_TEXT + "(HIGH)" + TEXT_RESET;
            }
            if (Math.abs(w - user_weight) < 0.001f) {
                marker += " (THIS)";
                user_printed = true;
            }
            String marker_no_ansi = marker.replaceAll("\u001B\\[[;\\d]*m", "");
            int total_length = base_status.length() + marker_no_ansi.length();
            String status_formatted = (total_length < status_cell_width)
                    ? base_status + String.format("%" + (status_cell_width - total_length) + "s", "") + marker
                    : base_status + marker;
            System.out.printf("| %-10.2f | %-10s | %-22s |\n", w, bmi_string, status_formatted);
            prev = w;
        }
        System.out.println("----------------------------------------------------");
    }

    // ROUNDING CALCULATION //
    public static double bmi_rounding(float bmi) {
        return bmi_rounded = Math.round(bmi * 10.0) / 10.0;
    }



    // OUTRO TEXT //
    public static void print_outro() {
        System.out.println("\nThe SFSU Mashouf Wellness Center is at 755 Font Blvd.");
        System.out.println("\n----------------------------------------------------------------------------------------------");
        System.out.println("-- Thank you for using my program, " + name + "!");
        System.out.println("-- Ear-esistible!!!");
        System.out.println("------------------------------------------------------------------------------------------------");
    }
}
