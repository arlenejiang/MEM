import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ATreasurer extends AMember{

    static Map<String, String> payments = new TreeMap<String, String>();
    static Map<String, MemberBalance> balance = new TreeMap<String, MemberBalance>();
    
    String first ;
    String last;
    String phoneNumber;
    String email;
    String password;
    String role;

    public ATreasurer(){
        for(Entry<String, AMember> entry: ClubManager.members.entrySet()){
            AMember member = entry.getValue();
            if(member.getRole().equals("Treasurer")){
                this.first = member.getFirstName();
                this.last = member.getLastName();
                this.phoneNumber = member.getPhoneNumber();
                this.email = member.getEmail();
                this.password = member.getPassword();
                this.role = member.getRole();
            }
        }
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

                MemberBalance person = new MemberBalance(entry.getKey());
                person.balance = String.valueOf(Integer.parseInt(person.balance)+Integer.parseInt(entry.getValue()));
                // entry.getValue(), "1", "0");
                balance.put(entry.getKey(), person);

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