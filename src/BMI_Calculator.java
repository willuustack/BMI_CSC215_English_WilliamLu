//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class BMI_Calculator {

    static String name;
    static int height_feet;
    static int height_inches;
    static float bmi;
    static double bmi_rounded;
    static String classification = bmi_classification(bmi_rounded);
    static float low_weight;
    static float high_weight;

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

        int height_feet = 0;
        int height_inches = 0;
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
        int weight = 0;
        boolean valid_weight = false;
        while (!valid_weight) {
            System.out.print("Please enter weight in pounds for " + name + ":");
            try {
                weight = input.nextInt();
                valid_weight = true;
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
        System.out.println("\n-- SUMMARY REPORT FOR " + name);
        System.out.println("-- Date and Time: " + dateTime);
        System.out.println("-- BMI: " + bmi + " (or " + bmi_rounded +" if rounded)");
        System.out.println("-- Weight Status: " + classification);
        System.out.println("\n");
    }

    //USER QUESTION 02
    public static void questionnaire_two() {
        float low_weight = 0;
        float high_weight = 0;
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

    public static void range_table() {
        for(float i = low_weight; i <= high_weight; i += 2.5) {
            int bmi = weight / Math.pow() * 703;
            System.out.println(i);

        }
    }


    //BMI CALCULATION//
    public static float bmi_calculator(int height_feet, int height_inches, int weight) {
        int total_height_inches = (height_feet * 12) + height_inches;
        bmi = (weight * 703.0f) / (total_height_inches * total_height_inches);
        return bmi;
        return total_height_inches;
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
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
}


