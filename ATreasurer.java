import java.io.IOException;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

public class ATreasurer extends AMember {

    static Map<String, Integer> payments = new TreeMap<String, Integer>();
    static Map<String, MemberBalance> balance = new TreeMap<String, MemberBalance>();

    String first;
    String last;
    String phoneNumber;
    String email;
    String password;
    String role;

    public ATreasurer() {
        for (Entry<String, AMember> entry : ClubManager.members.entrySet()) {
            AMember member = entry.getValue();
            if (member.getRole().equals("Treasurer")) {
                this.first = member.getFirstName();
                this.last = member.getLastName();
                this.phoneNumber = member.getPhoneNumber();
                this.email = member.getEmail();
                this.password = member.getPassword();
                this.role = member.getRole();
            }
        }
    }

    // Add's a new member to the member's payment treeMap.
    // Writes out all the members to PendingPayments.txt

    public void PrintMap() {

        Set<String> keySet = payments.keySet();

        for (String key : keySet) {
            System.out.println("User ID: " + key + "\n Pending Amount" + payments.get(key));
        }
    }

    public static void Choose() throws IOException {

        // MemberBalance person = new MemberBalance();

        Iterator<Map.Entry<String, Integer>> iterator = payments.entrySet().iterator();

        // Iterate over the HashMap
        while (iterator.hasNext()) {

            // Get the entry at this iteration
            Map.Entry<String, Integer> entry = iterator.next();

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

            MemberBalance person = null;

            if (option.equalsIgnoreCase("A")) {
                // System.out.println("Key is: " + entry.getKey());//debugging

                for (Entry<String, MemberBalance> e : balance.entrySet()) {
                    if (e.getKey().equals(entry.getKey())) {
                        person = e.getValue();
                        // System.out.println(person.toString());////////debugging
                        person.updateBalance(entry.getValue());// entry.getValue is the amount pending
                        // for(int i=0; i<(person.getBalance()/10); i+=10) {
                        person.updateNumOfPayments();// }
                        iterator.remove();
                        PaymentEmail(entry.getKey(), String.valueOf(entry.getValue()), "a");

                    }
                }

                // System.out.println("person addded: "+person.toString());//debugging

                ClubManager.toFile("PendingPayments.txt");
                ClubManager.toFile("Balances.txt");
                System.out.println("Payment approved.");

            }

            else if (option.equalsIgnoreCase("D")) {
                clearConsole();
                // System.out.println("Payment Denied");
                PaymentEmail(entry.getKey(), String.valueOf(entry.getValue()), "d");
                // ClubManager.toFile("PendingPayments.txt");
                iterator.remove();
                ClubManager.toFile("PendingPayments.txt");
                System.out.println("Payment denied.");
            }

        }
    }

    // Clears the console
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Option "c" : paypal to treasurer to announce member payment
    // Option "a" : paypal to member to announce their payment is denied
    // Option "d" : paypal to member to announce their payment is denied

    public static void PaymentEmail(String memberEmail, String amount, String option) throws IOException {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "imap.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        String receiver = memberEmail;
        String paypalEmail = "group66paypal@gmail.com";
        String paypalPassword = "april2022";

        String subj = "";
        String body = "";
        Session session;

        

        if (option.equalsIgnoreCase("M")) {
            session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("group66club@gmail.com", paypalPassword);
                    }
                });
            subj = "Missing Payments";
            body = "You have missed " + amount + " months of payment.";
        }
        else{
            if (option.equalsIgnoreCase("C")) {
            receiver = "group66club@gmail.com";
            subj = "Money Sent from " + memberEmail;
            body = memberEmail + " sent you $" + amount + "(CAD).";
            }

            else if (option.equalsIgnoreCase("A")) {
                subj = "Payment Successful!";
                body = "Payment approved. The amount $" + amount
                        + "(CAD) has been successfully deposited into your account.";
            }

            else if (option.equalsIgnoreCase("D")) {
                subj = "Cancelled Transaction";
                body = "Your transaction was denied. Please try again!";
            }

            session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(paypalEmail, paypalPassword);
                    }
                });

        }

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(paypalEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    //
                    InternetAddress.parse(receiver) // For 1 person, just enter the email string ex:
                                                    // "kffjk322@gmail.com"
            );
            message.setSubject(subj);
            message.setText("Hello! \n\n"
                    + body);

            Transport.send(message);

            // System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}