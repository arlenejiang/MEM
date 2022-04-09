import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ClubManager {

    static Map<String, AMember> members = new TreeMap<String, AMember>();
    Scanner in = new Scanner(System.in);
    File file = new File("User_Info.txt");

    public ClubManager() throws IOException {
        fromFile(file);
    }

    // Add's a new member to the member's treeMap.
    // Writes out all the members to User_Info.txt
    public void registerMember(String first, String last, String phoneNumber, String email, String password)
            throws FileNotFoundException {
        AMember person = new AMember(first, last, phoneNumber, email, password, "Member");
        members.put(email, person);

        PrintWriter out = new PrintWriter("User_Info.txt");

        for (AMember member : members.values()) {
            out.println(member.getFirstName() + " " + member.getLastName() + " " + member.getPhoneNumber() + " "
                    + member.getEmail() + " " + member.getPassword() + " " + member.getRole());
        }

        out.close();
    }

    // Checks the user's email with the emails of the members already in the club
    // If the email is found, return true, else return false
    public boolean checkEmail(String userEmail) {
        for (String email : members.keySet()) {
            if (email.equals(userEmail)) {
                return true;
            }
        }
        return false;
    }

    public String emailsToString() {
        String allEmails = "";
        for (String email : members.keySet()) {
            allEmails += email + ", ";
        }
        String removeSplice = "";
        if (allEmails != null && allEmails.length() > 1) {
            removeSplice += allEmails.substring(0, allEmails.length() - 2);
        }

        return removeSplice;
    }

    public static void fromFile(File file) {
        
        Scanner sc = null;
        try {
            sc = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (file.equals(new File("User_Info.txt"))) {
            while (sc.hasNextLine()) {
                AMember person = new AMember(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next());
                members.put(person.getEmail(), person);
            }
        } else if (file.equals(new File ("finances.txt"))) {
            String input;
            String lastUpdate = sc.nextLine().substring(0, 10);

            while ((sc.nextLine()).equals("Unpaid Monthly Rent")) {
            }
            ;
            sc.nextLine();

            // Add previous unpaid months from file to list rentMonths
            input = sc.nextLine();
            while (input.length() > 0) {
                Finances.rentMonths.add(input);
                input = sc.nextLine();
            }
            // Add new unpaid months to list rentMonths
            Finances.rentCharge(lastUpdate);

            // Add previous unpaid months from file to list coachFees
            sc.nextLine();
            while (sc.hasNextLine()) {
                Finances.coachFees.add(sc.nextLine());
            }
        }
        sc.close();

    }
}
