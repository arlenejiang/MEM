import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

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
        String line;
        Scanner word = null;
        try {
            sc = new Scanner(new FileInputStream(file));

            if (file.equals(new File("User_Info.txt"))) {
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    word = new Scanner(line);

                    AMember person = new AMember(word.next(), word.next(), word.next(), word.next(), word.next(),
                        word.next());
                    members.put(person.getEmail(), person);
                }
            }
            if (file.equals(new File("finances.txt"))) {
                String lastUpdate = sc.nextLine().substring(0, 10);

                while ((sc.nextLine()).equals("Unpaid Monthly Rent")) {}
                sc.nextLine();
                // Add previous unpaid months from file to list rentMonths
                line = sc.nextLine();
                while (line.length() > 0) {
                    Finances.rentMonths.add(line);
                    line = sc.nextLine();
                }
                // Add new unpaid months to list rentMonths
                Finances.rentCharge(lastUpdate);

                // Add previous unpaid months from file to list coachFees
                sc.nextLine();
                while (sc.hasNextLine()) {
                    Finances.coachFees.add(sc.nextLine());
                }
            } 
            if (file.equals(new File("PendingPayments.txt"))) {
                String email;
                int amount;

                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    word = new Scanner(line);

                    email = word.next();
                    amount = Integer.parseInt(word.next());
                    
                    ATreasurer.payments.put(email, amount);
                }
            } 
            if (file.equals(new File("Balances.txt"))) {
                while (sc.hasNextLine()) {

                    line = sc.nextLine();
                    word = new Scanner(line);
                    MemberBalance person = new MemberBalance(word.next(), word.nextInt(), word.nextInt(), word.nextInt());

                    ATreasurer.balance.put(person.getEmail(), person);
                    word.close();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.close();

    }

    public static void toFile(String fileName) throws IOException{

        FileWriter fw = null; 
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(fileName, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            if(fileName.equals("User_Info.txt")){
                for (Entry<String, AMember> entry : members.entrySet()){
                    AMember member = entry.getValue();
                    pw.println(member.getFirstName() + " " + member.getLastName() + " " + member.getPhoneNumber()
                     + " " + member.getEmail() + " " + member.getPassword() + " " + member.getRole());
                }
            }else if(fileName.equals("PendingPayments.txt")){
                for (Entry<String, Integer> email : ATreasurer.payments.entrySet()){
                    int amount = email.getValue();
                    pw.println(email.getKey() + " " + String.valueOf(amount));
                }
            }
            else if(fileName.equals("Balances.txt")){
                for (Entry<String, MemberBalance> entry : ATreasurer.balance.entrySet()){
                    MemberBalance str = entry.getValue();
                    pw.println(str.getEmail() + " " + String.valueOf(str.getBalance()) + " " + String.valueOf(str.getNumOfPayments()) + " "
                    + String.valueOf(str.getMissingPayments()));
                }
            }
            System.out.println("Data Successfully appended into file");
            pw.flush();
        } finally {
            try {
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException io) {// can't do anything }
            }
        }
    }
}