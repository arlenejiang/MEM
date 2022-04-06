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
    }

    // Add's a new member to the member's treeMap.
    // Writes out all the members to User_Info.txt
    public void addToMap(String email, String amount) {

        payments.put(email, amount);
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

    public void PrintMap() {

        Set<String> keySet = payments.keySet();

        for (String key : keySet) {
            System.out.println("User ID: " + key + "\n Pending Amount" + payments.get(key));
        }
    }

    public void UpdateMapandFile(String file) throws FileNotFoundException {

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
                iterator.remove();
                clearConsole();

            }

            else {
                clearConsole();
                System.out.println("This will update the balance");
            }

        }

        // if (entry.getKey().equals("t10huynh@ryerson.ca")) {
        // keyToBeRemoved = entry.getKey();
        // }
        // // Check if this key is the required key
        // if (keyToBeRemoved.equals(entry.getKey())) {

        // // Remove this entry from HashMap
        // iterator.remove();
        // }
        // }

        // // Print the modified HashMap
        // System.out.println("Modified HashMap: "
        // + payments);

        // for (Entry<String, String> payment : payments.entrySet()) {
        // System.out.println("User ID: " + payment.getKey() + "\nPending Amount: $" +
        // payment.getValue());

        // Scanner in = new Scanner(System.in);
        // String option = "";

        // while (option == "" || option == null) {
        // System.out.print("\nApprove (A)\t");
        // System.out.print("Deny (D)\n");
        // System.out.print("\n> ");

        // option = in.nextLine();

        // if (option == "" || option == null) {
        // // clearConsole();
        // // System.out.println("*** Payment ***\n");
        // System.out.println("Please approve or deny transaction\n");
        // option = "";
        // }
        // }

        // if (option.equalsIgnoreCase("A")) {
        // System.out.println("Key is: " + payment.getKey());
        // String keyToBeRemoved = payment.getKey();
        // payments.remove(keyToBeRemoved);

        // }

        // else {
        // System.out.println("This will update the balance");
        // }

        // }

        writeToFile(file);
    }

    // Clears the console
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
