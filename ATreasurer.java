import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ATreasurer {

    Map<String, String> payments = new TreeMap<String, String>();

    Map<String, MemberBalance> treasurers = new TreeMap<String, MemberBalance>();

    public ATreasurer() throws FileNotFoundException {
        File input = new File("PendingPayments.txt");
        Scanner in = new Scanner(input);

        String member = "";
        String email = "";
        String amount = "";

        // Reads each member's info from the PendingPayments.txt file.
        // Adds the member's email and the PendingAmount to the treeMap
        while (in.hasNextLine()) {

            member = in.nextLine();
            Scanner word = new Scanner(member);

            email = word.next();

            amount = word.next();

            payments.put(email, amount);
            word.close();
        }

        in.close();

        File input2 = new File("Balances.txt");
        Scanner sc = new Scanner(input2);

        // public MemberBalance(String email, String balance, String numOfPayments,
        // String missingPayments) {

        String treasurer = "";
        String email2 = "";
        String balance = "";
        String numOfPayments = "";
        String missingPayments = "";

        // Reads each member's info from the PendingPayments.txt file.
        // Adds the member's email and the PendingAmount to the treeMap
        while (sc.hasNextLine()) {

            treasurer = sc.nextLine();
            Scanner word2 = new Scanner(treasurer);

            email2 = word2.next();

            balance = word2.next();

            numOfPayments = word2.next();

            missingPayments = word2.next();

            MemberBalance person = new MemberBalance(email2, balance, numOfPayments, missingPayments);

            treasurers.put(email, person);

            word2.close();
        }

        sc.close();
    }

    // Add's a new member to the member's treeMap.
    // Writes out all the members to User_Info.txt
    public void addToMap(String email, String amount) {

        payments.put(email, amount);
    }

    public void addToBalance(String email, MemberBalance person) {

        treasurers.put(email, person);
    }

    public void writeToFile(String file) throws FileNotFoundException {

        PrintWriter out = new PrintWriter(file);

        Set<String> keySet = payments.keySet();

        for (String key : keySet) {
            // System.out.println(key + ": " + payments.get(key)); -> debugging purpose
            out.println(key + " " + payments.get(key));
        }

        out.close();

    }

    public void writeToBalance() throws FileNotFoundException {

        PrintWriter out = new PrintWriter("Balances.txt");

        Set<String> keySet = treasurers.keySet();

        // if (file.equals("PendingPayments.txt")) {
        // for (String key : payments.keySet()) {
        // // System.out.println(key + ": " + payments.get(key)); -> debugging purpose
        // out.println(key + " " + payments.get(key));
        // }
        // }

        // else if (file.equals("Balances.txt")) {
        for (String key : keySet) {
            // System.out.println(key + ": " + payments.get(key)); -> debugging purpose
            MemberBalance str = treasurers.get(key);

            out.println(str.getEmail() + " " + str.getBalance() + " " + str.getNumOfPayments() + " "
                    + str.getMissingPayments());
        }
        // }

        out.close();

    }

    public void PrintMap() {

        Set<String> keySet = payments.keySet();

        for (String key : keySet) {
            System.out.println("User ID: " + key + "\n Pending Amount" + payments.get(key));
        }
    }

    public void UpdateMapandFile(String file) throws FileNotFoundException {

        // MemberBalance person = new MemberBalance();

        Iterator<Map.Entry<String, String>> iterator = payments.entrySet().iterator();

        // Iterate over the HashMap
        while (iterator.hasNext()) {

            // Get the entry at this iteration
            Map.Entry<String, String> entry = iterator.next();

            System.out.println("User ID: " + entry.getKey() + "\nPending Amount: $" + entry.getValue());

            Scanner in = new Scanner(System.in);
            String option = "";

            while (option == "" || option == null) {
                System.out.print("\nApprove (A)\t");
                System.out.print("Deny (D)\n");
                System.out.print("\n> ");

                option = in.nextLine();

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
            // if deny -> send an email to the member telling them that there payment is
            // denied
            else {
                clearConsole();
                System.out.println("Deny option");
            }

        }

        writeToFile(file);
        writeToBalance();
    }

    // Clears the console
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
