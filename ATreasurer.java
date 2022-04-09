import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class ATreasurer {

    static Map<String, String> payments = new TreeMap<String, String>();
    static Map<String, MemberBalance> treasurers = new TreeMap<String, MemberBalance>();
    File file1 = new File("PendingPayments.txt");
    File file2 = new File("Balances.txt");

    public ATreasurer() throws FileNotFoundException {

        // Reads each member's info from the PendingPayments.txt file.
        // Adds the member's email and the PendingAmount to the treeMap
        ClubManager.fromFile(file1);

        // Reads each member's info from the PendingPayments.txt file.
        // Adds the member's email and the PendingAmount to the treeMap
        ClubManager.fromFile(file2);
    }

    // Add's a new member to the member's payment treeMap.
    // Writes out all the members to PendingPayments.txt
    public static void addToMap(String email, String amount) {

        payments.put(email, amount);
    }

    public static void addToBalance(String email, MemberBalance person) {

        treasurers.put(email, person);
    }

    public void PrintMap() {

        Set<String> keySet = payments.keySet();

        for (String key : keySet) {
            System.out.println("User ID: " + key + "\n Pending Amount" + payments.get(key));
        }
    }

    public static void Choose() throws IOException {

        // MemberBalance person = new MemberBalance();

        Iterator<Map.Entry<String, String>> iterator = payments.entrySet().iterator();

        // Iterate over the HashMap
        while (iterator.hasNext()) {

            // Get the entry at this iteration
            Map.Entry<String, String> entry = iterator.next();

            System.out.println("User ID: " + entry.getKey() + "\nPending Amount: $" + entry.getValue());

            String option = "";

            while (option == "" || option == null) {
                System.out.print("\nApprove (A)\t");
                System.out.print("Deny (D)\n");
                System.out.print("\n> ");

                option = MEM.in.nextLine();

                if (option == "" || option == null) {
                    // clearConsole();
                    // System.out.println("*** Payment ***\n");
                    System.out.println("Please approve or deny transaction\n");
                    option = "";
                }
            }

            // String keyToBeRemoved = "";

            if (option.equalsIgnoreCase("A")) {
                System.out.println("Key is: " + entry.getKey());
                // keyToBeRemoved = entry.getKey();

                MemberBalance person = new MemberBalance(entry.getKey(), entry.getValue(), "1", "0");
                treasurers.put(entry.getKey(), person);

                iterator.remove();
                clearConsole();

            }

            else if (option.equalsIgnoreCase("D")) {
                clearConsole();
                System.out.println("Payment Denied");
            }
        }

        ClubManager.toFile(new FileWriter("PendingPayments.txt"));
        ClubManager.toFile(new FileWriter("PendingPayments.txt"));
    }

    // Clears the console
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
