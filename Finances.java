import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;  
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Arrays;

/**
 * main
 */
public class Finances {
    static String[] mos = {"January", "February", "March", "April", "May", "June", 
        "July", "August", "September", "October", "November", "December"};
    static int RENT = 500;
    static LinkedList<String> rentMonths = new LinkedList<String>();
    static LinkedList<String> coachFees = new LinkedList<String>();
    

    public static void displayDebt() throws IOException{
        
        String input;
        File myFile = new File("finances.txt");
        Scanner myReader = new Scanner(myFile);
        String lastUpdate = myReader.nextLine().substring(0,10);
        
        while ((myReader.nextLine()).equals("Unpaid Monthly Rent")){};
        myReader.nextLine();

        // Add previous unpaid months from file to list rentMonths
        input = myReader.nextLine();
        while (input.length() > 0) {
            rentMonths.add(input);
            input = myReader.nextLine();
        }
        // Add new unpaid months to list rentMonths
        rentCharge(lastUpdate); 

        // Add previous unpaid months from file to list coachFees
        myReader.nextLine();
        while(myReader.hasNextLine()) {
            coachFees.add(myReader.nextLine());
        }
        myReader.close();

        //Output Fees
        MEM.clearConsole();
        
        System.out.println("\n\nUNPAID DEBTS\n");
        System.out.println("Due Date\tFees\tPayee\n");

        int c=0, r=0;

        String cDate, rDate, cFee, rFee, cYear, rYear, cMonth, rMonth;

        while (c < (coachFees.size()-1) || r < (rentMonths.size())-1) {
            cDate = coachFees.get(c);
            cFee = cDate.split(" ")[2]; 
            cYear = cDate.split(" ")[1]; 
            cMonth = cDate.split(" ")[0];
            rDate = rentMonths.get(r);
            rFee = rDate.split(" ")[2]; 
            rYear = rDate.split(" ")[1];
            rMonth = rDate.split(" ")[0];

            // Print unpaid charges by due date
            if (Integer.valueOf(rYear) < Integer.valueOf(cYear) || 
            (cYear.equals(rYear) && 
            Arrays.asList(mos).indexOf(rMonth) <= Arrays.asList(mos).indexOf(cMonth))) {
                System.out.printf("%s 1, %s", rMonth.substring(0,3), rYear);
                System.out.printf("\t$%s", rFee);
                System.out.printf("\tHall Rent (%s)\n", rMonth);
                r++;
                
            }
            else {
                System.out.printf("%s 1, %s", cMonth.substring(0,3), cYear);
                System.out.printf("\t$%s", cFee);
                System.out.printf("\tCoach Fees\n");
                c++;
            }
        }
        if (c < coachFees.size()-1){
            cDate = coachFees.get(c);
            cFee = cDate.split(" ")[2]; 
            cYear = cDate.split(" ")[1]; 
            cMonth = cDate.split(" ")[0];
            System.out.printf("%s 1, %s", cMonth.substring(0,3), cYear);
            System.out.printf("\t$%s", cFee);
            System.out.printf("\tCoach Fees\n\n");
        }
        else{
            rDate = rentMonths.get(r);
            rFee = rDate.split(" ")[2]; 
            rYear = rDate.split(" ")[1];
            rMonth = rDate.split(" ")[0];
            System.out.printf("%s 1, %s", rMonth.substring(0,3), rYear);
            System.out.printf("\t$%s", rFee);
            System.out.printf("\tHall Rent (%s)\n\n", rMonth);   
        }
        

        // Update finances.txt file
        FileWriter myWriter = new FileWriter("finances2.txt");
        myWriter.write(java.time.Clock.systemUTC().instant().toString());
        myWriter.write("\n\nUnpaid Monthly Rent\n");
        for (String months: rentMonths){
            myWriter.write(months);
            myWriter.write("\n");
        }
        myWriter.write("\nUnpaid Coach Fees\n");
        for (String cf: coachFees){
            myWriter.write(cf + "\n");
        }
       
        myWriter.close();
    }

    // TODO modify based on attendance logs
    private static void coachCharge(String date) throws FileNotFoundException {
        // Check number of rent days (1st of the month)
        String input;
        LocalDate lt= LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();
        int months = (int) ChronoUnit.MONTHS.between(lt.withDayOfMonth(1), currentDate);

        // Scan log
        File myFile = new File("coaches.txt");
        Scanner myReader = new Scanner(myFile);
        
        myReader.close();
    }

    // Adds unpaid rent months to list based on current date
    private static void rentCharge(String date) {
        int year = Integer.valueOf(date.substring(0, 4));
        int month = Integer.valueOf(date.substring(5, 7));

        // Check number of rent days (1st of the month)
        LocalDate lt= LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();
        int months = (int) ChronoUnit.MONTHS.between(lt.withDayOfMonth(1), currentDate);

        // Add months to list
        for (int i = 0; i < months; i++) {
            if (month != 12) {
                month++;
            }
            else {
                month = 01;
                year++;
            }
            rentMonths.add(String.format("%s %d %d", mos[month-1], year, RENT));
        }
    }
}
