//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class BMI_Calculator {

    public static void main(String[] args) {
        intro(); //run intro

/////////////////////////////////////////////////NAME QUESTIONNAIRE////////////////////////////////////////////////////////
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter your full name: ");
        String name = input.nextLine();
/////////////////////////////////////////////////NAME QUESTIONNAIRE////////////////////////////////////////////////////////


/////////////////////////////////////////////////HEIGHT QUESTIONNAIRE////////////////////////////////////////////////////////
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
                input.next();
            }
        }
/////////////////////////////////////////////////HEIGHT QUESTIONNAIRE////////////////////////////////////////////////////////
/////////////////////////////////////////////////WEIGHT QUESTIONNAIRE////////////////////////////////////////////////////////
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
/////////////////////////////////////////////////WEIGHT QUESTIONNAIRE////////////////////////////////////////////////////////
        summary(name);//run summary
        outro(name);//run outro
        bmi_calculator(height_feet, height_inches, weight);
    }

    public static void summary(String name) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm:ss a");
        String dateTime = now.format(formatter);
        System.out.println("\n-- SUMMARY REPORT FOR " + name);
        System.out.println("-- Date and Time: " + dateTime);
    }

    public static void bmi_calculator(int height_feet, int height_inches, int weight) {
        int total_height_inches = (height_feet * 12) + height_inches;
        float bmi = (weight / (total_height_inches * total_height_inches)) * 703;
        System.out.println("BMI: " + bmi);
    }

    public static void intro() {
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("-- Welcome to:");
        System.out.println("--            BODY MASS INDEX (BMI) Computation, CSC 215, English version");
        System.out.println("--                                                                    by William Lu");
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
    public static void outro(String name) {
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("-- Thank you for using my program, "+ name +"!");
        System.out.println("-- Ear-esistible!!!");
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
}

