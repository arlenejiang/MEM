import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Arrays;

/**
 * main
 */
public class Finances {
    static String[] mos = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };
    static int RENT = 500;
    static LinkedList<String> rentMonths = new LinkedList<String>();
    static LinkedList<String> coachFees = new LinkedList<String>();
    static File file1 = new File("finances.txt");

    public static void getData() {
        ClubManager.fromFile(file1);
    }

    public static void displayDebt() throws IOException {
        // Output Fees

        System.out.println("\n\nUNPAID DEBTS\n");
        System.out.println("Due Date\tFees\tPayee\n");

        int c = 0, r = 0;

        String cDate, rDate, cFee, rFee, cYear, rYear, cMonth, rMonth;

        while (c < (coachFees.size() - 1) || r < (rentMonths.size()) - 1) {
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
                System.out.printf("%s 1, %s", rMonth.substring(0, 3), rYear);
                System.out.printf("\t$%s", rFee);
                System.out.printf("\tHall Rent (%s)\n", rMonth);
                r++;

            } else {
                System.out.printf("%s 1, %s", cMonth.substring(0, 3), cYear);
                System.out.printf("\t$%s", cFee);
                System.out.printf("\tCoach Fees\n");
                c++;
            }
        }
        if (c < coachFees.size() - 1) {
            cDate = coachFees.get(c);
            cFee = cDate.split(" ")[2];
            cYear = cDate.split(" ")[1];
            cMonth = cDate.split(" ")[0];
            System.out.printf("%s 1, %s", cMonth.substring(0, 3), cYear);
            System.out.printf("\t$%s", cFee);
            System.out.printf("\tCoach Fees\n\n");
        } else {
            rDate = rentMonths.get(r);
            rFee = rDate.split(" ")[2];
            rYear = rDate.split(" ")[1];
            rMonth = rDate.split(" ")[0];
            System.out.printf("%s 1, %s", rMonth.substring(0, 3), rYear);
            System.out.printf("\t$%s", rFee);
            System.out.printf("\tHall Rent (%s)\n\n", rMonth);
        }
    }

    // TODO modify based on attendance logs
    // private static void coachCharge(String date) throws FileNotFoundException {
    // // Check number of rent days (1st of the month)
    // String input;
    // LocalDate lt= LocalDate.parse(date);
    // LocalDate currentDate = LocalDate.now();
    // int months = (int) ChronoUnit.MONTHS.between(lt.withDayOfMonth(1),
    // currentDate);

    // // Scan log
    // File myFile = new File("coaches.txt");
    // //Scanner myReader = new Scanner(myFile);

    // //myReader.close();
    // }

    // Adds unpaid rent months to list based on current date
    static void rentCharge(String date) {
        int year = Integer.valueOf(date.substring(0, 4));
        int month = Integer.valueOf(date.substring(5, 7));

        // Check number of rent days (1st of the month)
        LocalDate lt = LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();
        int months = (int) ChronoUnit.MONTHS.between(lt.withDayOfMonth(1), currentDate);

        // Add months to list
        for (int i = 0; i < months; i++) {
            if (month != 12) {
                month++;
            } else {
                month = 01;
                year++;
            }
            rentMonths.add(String.format("%s %d %d", mos[month - 1], year, RENT));
        }
    }

    public static void displayPayables() {
        // Read from Balances.txt file into "Map<String, MemberBalance> balance"
        ClubManager.fromFile(new File("Balances.txt"));

        System.out.println("\n\nACCOUNT PAYABLES\n");
        // Loop through Map and output members with numOfPayments > 0
        for (MemberBalance mb : ATreasurer.balance.values()) {
            if (mb.getBalance() > 0) {
                AMember a = ClubManager.members.get(mb.getEmail());
                System.out.println(a.getFirstName() + " " + a.getLastName());
                // System.out.println(mb.getBalance());
            }
        }
        System.out.println("\n");
    }

    // Clears the console
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}