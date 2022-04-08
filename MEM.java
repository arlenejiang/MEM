import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.PasswordAuthentication;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;
// import javax.mail.Authenticator;
import javax.mail.Session;

public class MEM {
    static Scanner in = new Scanner(System.in);

    public static void main(String args[]) {
        // Creates a manager object and catched IOException
        clearConsole();
        ClubManager manager = null;
        try {
            manager = new ClubManager();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Gives the user the option of registering or logging in
        // Enter 1 to register, enter 2 to log in
        System.out.println("\n*** Welcome to the Recreation Club Membership App ***\n");
        System.out.print("Register(1)\t");
        System.out.print("Login(2)\n");
        System.out.print("\n> ");

        int input = convertInputToInteger(2, 1);

        // If the input is a 1, allow the user to register
        if (input == 1) {
            try {
                clearConsole();
                System.out.println("*** Registration ***\n");
                RegisterationQuestions(manager); // Shows the registration questions
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            clearConsole();
            System.out.println("Registration Complete\n");

            // Allows the user to choose if they want to login or exit after registering
            // Enter 1 to login, enter 2 to exit.
            System.out.print("Login(1)\t");
            System.out.print("Exit(2)\n");
            System.out.print("\n> ");

            input = convertInputToInteger(2, 2);

            // Exit if the user chooses number 2
            if (input == 2) {
                System.out.println("\nHave a nice day\n");
                System.exit(0);
            }
            // If the user want to login, change input to 2.
            // The next if statement will allow logging in when input = 2.
            if (input == 1) {
                input = 2;
            }
        }

        // For logging in.
        if (input == 2) {
            log_in();
        }
    }

    public static void registerLogin() {
        System.out.println("\n*** Welcome to the Recreation Club Membership App ***\n");
        System.out.print("Register(1)\t");
        System.out.print("Login(2)\n");
    }

    public static void loginExit() {
        System.out.print("Login(1)\t");
        System.out.print("Exit(2)\n");
        System.out.print("\n> ");
    }

    public static void chooseAppFeature(int num) {
        if (num == 1) {
            registerLogin();
        } else if (num == 2) {
            loginExit();
        }
    }

    // Converts user input fot choosing what to do in the app to an integer
    // Max input is the maximum number the user can enter
    public static int convertInputToInteger(int maxInput, int featureNum) {
        Boolean checkValidInput = false;
        int input = 0;
        while (checkValidInput == false || (input < 1 || input > maxInput)) {
            try {
                input = Integer.parseInt(in.nextLine());
                checkValidInput = true;
                if (input < 1 || input > maxInput) {
                    clearConsole();
                    chooseAppFeature(featureNum);
                    System.out.println("\nPlease enter a valid number");
                    System.out.print("> ");
                    checkValidInput = false;

                }
            } catch (NumberFormatException e) {
                clearConsole();
                chooseAppFeature(featureNum);
                System.out.println("\nPlease enter a valid number");
                System.out.print("> ");
            }
        }
        return input;

    }

    public static void RegisterationQuestions(ClubManager manager) throws IOException {

        // Asks for the first name of the user
        String firstName = "";
        while (firstName == "" || firstName == null) {
            System.out.print("Enter your first name: ");
            firstName = in.nextLine();
            if (firstName == "" || firstName == null) {
                clearConsole();
                System.out.println("*** Registration ***\n");
                System.out.println("Can't leave first name empty.\n");
            }
        }
        clearConsole();
        System.out.println("*** Registration ***\n");

        // Asks for the last name of the user
        String lastName = "";
        while (lastName == "" || lastName == null) {
            System.out.print("Enter your last name: ");
            lastName = in.nextLine();
            if (lastName == "" || lastName == null) {
                clearConsole();
                System.out.println("*** Registration ***\n");
                System.out.println("Can't leave last name empty.\n");
            }
        }
        clearConsole();
        System.out.println("*** Registration ***\n");

        // Asks for phone number of the user
        String phoneNumber = "";
        while (phoneNumber == "" || phoneNumber == null) {
            System.out.print("Enter your phone number: ");
            phoneNumber = in.nextLine();
            if (phoneNumber == "" || phoneNumber == null) {
                clearConsole();
                System.out.println("*** Registration ***\n");
                System.out.println("Can't leave phone number empty.\n");
            }
        }
        clearConsole();
        System.out.println("*** Registration ***\n");

        // Asks for the email of the user
        // Makes sure that a person cannot register with an email that is already being
        // used
        Boolean emailFound = true;
        String email = "";
        while (emailFound == true || email == "" || email == null) {
            System.out.print("Enter your email: ");
            email = in.nextLine();
            emailFound = manager.checkEmail(email);

            if (emailFound == true) {
                clearConsole();
                System.out.println("*** Registration ***\n");
                System.out
                        .println("This email is already being used by a current member. Please enter another email.\n");
            }

            if (email == "" || email == null) {
                clearConsole();
                System.out.println("*** Registration ***\n");
                System.out.println("Can't leave email empty.\n");
            }
        }
        clearConsole();
        System.out.println("*** Registration ***\n");

        // Asks user for a password.
        // Makes sure password is longer than 8 characters
        String password = "0";

        // Asks user to verify password
        // Makes sure that the password is the same as the password entered before.
        String password2 = "1";
        while (password.equals(password2) == false) {
            while (password.length() < 8) {
                System.out.print("Enter your password: ");
                password = in.nextLine();

                if (password.length() < 8) {
                    clearConsole();
                    System.out.println("*** Registration ***\n");
                    System.out.println("Password needs to be at least 8 characters.");
                }
                System.out.println();
            }

            System.out.print("Re-Enter your password: ");
            password2 = in.nextLine();

            if (password.equals(password2) == false) {
                clearConsole();
                System.out.println("*** Registration ***\n");
                System.out.println("Passwords do not match. Please enter your password again.");
                System.out.println();
                password = "-1";
            }
        }
        manager.registerMember(firstName, lastName, phoneNumber, email, password);
    }

    // Log in Feature
    public static void log_in() {
        System.out.print("Email: ");
        String email = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();

        ClubManager club = null;
        try {
            club = new ClubManager();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<AMember> people = new ArrayList<>();

        for (Map.Entry<String, AMember> entry : club.members.entrySet()) {
            people.add(entry.getValue());
        }

        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getEmail().equals(email) && people.get(i).getPassword().equals(password)) {
                AfterLogIn(people.get(i));
            } else if (i == people.size() - 1) {
                PrintMessage();
            }
        }

    }

    public static void PrintMessage() {
        System.out.println(
                "You have entered one or more of the following pieces of information incorrectly: username and/or password.");
        System.out.println("Please try again");
        log_in();
    }

    /*
     * NOTE: Can only send through Gmail. Can send to any address.
     * 
     * 1. Go onto the sender's Gmail.
     * 2. Go to settings icon > See All Settings > Forwarding and POP/IMAP
     * 3. Enable IMAP access
     * 
     * 1. Go onto myaccount.google.com
     * 2. Scroll down to less secure app access
     * 3. Enable less secure app access
     */

    public static void PaypalConfirmationemail(String treasurerEmail, String treasurerPassword, String fullname)
            throws IOException {
        final String username = treasurerEmail;
        final String password = treasurerPassword;

        // For a single person, get rid of from here
        // ClubManager manager = null;
        // try {
        // manager = new ClubManager();
        // } catch (IOException e) {
        // System.out.println(e.getMessage());

        System.out.println("Enter the subject line: ");

        String subj = in.nextLine();
        System.out.println("Enter the body of the email (with \\n for new lines): ");
        String body = "";
        String next;

        while (in.hasNextLine() && !(next = in.nextLine()).equals("")) {
            body += next;
            body += "\n";
        }

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "imap.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(treasurerEmail, treasurerPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    //
                    InternetAddress.parse("group66club@gmail.com") // For 1 person, just enter the email string ex:
                                                                   // "kffjk322@gmail.com"
            );
            message.setSubject("** ANNOUNCEMENT **: " + subj);
            message.setText("Hello! \n\n"
                    + body + "\n\n" + fullname);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void PendingPayments(String email, String amount) throws FileNotFoundException {

        ATreasurer treasurer = null;

        try {
            treasurer = new ATreasurer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        treasurer.addToMap(email, amount);
        treasurer.writeToFile("PendingPayments.txt");

    }

    public static void ApprovedPayments(String email, MemberBalance person) throws FileNotFoundException {

        ATreasurer balance = null;

        try {
            balance = new ATreasurer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        balance.addToBalance(email, person);

        // balance.writeToFile("Balances.txt");
        balance.writeToBalance();

    }

    public static void ValidatePayments(String file) throws FileNotFoundException {

        ATreasurer treasurer = null;

        try {
            treasurer = new ATreasurer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        treasurer.UpdateMapandFile(file);
    }

    // After log in options for staff and players
    public static void AfterLogIn(AMember member) {
        clearConsole();

        // Gives the user the option of entering S to send announcement, F for finance
        // feature,
        // P for practice schedule/scheduling, and E to exit
        System.out.println("\n*** Welcome to the Recreation Club Membership App ***\n");
        if (member.getRole().equals("Coach")) {
            System.out.print("Send Annoucement (S)\t");
        } else if (member.getRole().equals("Treasurer")) {
            System.out.print("Pending Payments List (L)\t");
        }

        System.out.print("Finances (F)\t");
        System.out.print("Practice Schedule (P)\n");
        System.out.print("Attendance (A)\n");
        System.out.print("Exit (E)");
        System.out.print("\n> ");

        String option = in.nextLine();

        // If the input is a 1, go to annoucemnets
        if (option.equalsIgnoreCase("S")) {
             try {
                clearConsole();
                System.out.println("*** Send Announcement ***\n");
                // insert method for sending email through java code
                sendAnnouncements(member.email, member.password, member.firstName + member.lastName);
             } catch (IOException e) {
                 System.out.println(e.getMessage());
             }
            clearConsole();
            System.out.println("Announcement Successfully Sent\n");

            // Allows the user to choose if they want to return to the main screen or exit
            // after senting a annoucement
            // Enter 1 to main screen, enter 2 to exit.
            System.out.print("Return to Main Screen(1)\t");
            System.out.print("Exit(2)\n");
            System.out.print("\n> ");

            int input = convertInputToInteger(2, 1);

            // If the input is a 1, return to the main screen
            if (input == 1) {
                clearConsole();
                AfterLogIn(member);
            }
            // For exiting the annoucement feature.
            else if (input == 2) {
                System.out.println("\nHave a nice day\n");
                System.exit(0);
            }
        } else if (option.equalsIgnoreCase("L")) {
            clearConsole();
            System.out.println("Showing list of pending payments\n");
            try {
                // System.out.println("*** Registration ***\n");
                ValidatePayments("PendingPayments.txt");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            System.out.print("Return to Main Screen(1)\t");
            System.out.print("Exit(2)\n");
            System.out.print("\n> ");

            int anotherInput = convertInputToInteger(2, 1);

            if (anotherInput == 1) {
                clearConsole();
                AfterLogIn(member);
            }
            // For exiting the annoucement feature.
            else if (anotherInput == 2) {
                System.out.println("\nHave a nice day!\n");
                System.exit(0);
            }

        } else if (option.equalsIgnoreCase("F")) {
            // insert finance code method here
            if (member.getRole().equals("Treasurer")) {
                System.out.print("Display Debts (D)\t");
                option = in.nextLine();
                if (option.equalsIgnoreCase("D")) {
                    try {
                        Finances.displayDebt();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                System.out.print("Return to Main Screen(1)\t");
                System.out.print("Exit(2)\n");
                System.out.print("\n> ");

                int input = convertInputToInteger(2, 1);

                // If the input is a 1, return to the main screen --- Refactor
                if (input == 1) {
                    clearConsole();
                    AfterLogIn(member);
                } else if (input == 2) {
                    System.out.println("\nHave a nice day\n");
                    System.exit(0);
                }
            }
            System.out.print("Top up account balance(1)\t");
            System.out.print("Return to Main Screen(2)\t");
            System.out.print("Exit(3)\n");
            System.out.print("\n> ");

            int input = convertInputToInteger(3, 1);

            // If the input is a 1, return to the main screen
            if (input == 1) {
                clearConsole();
                System.out.println("Here are some steps to top up your account balance:");
                System.out
                        .println("\n1. Go to this link: https://paypal.me/memgroup66?country.x=CA&locale.x=en_US");
                System.out.println("2. Click SEND");
                System.out.println("3. Log in to PayPal. Sign up if you don't have an account");
                System.out.println("4. Enter the amount you want to send");
                System.out.println(
                        "5. Add your payment method. You can either link a credit/debit card or a bank account");
                System.out.println("6. Click SEND to complete your payment!");

                String amount = "";

                while (amount == "" || amount == null) {
                    System.out.print("\n\nEnter the amount you paid: $");
                    amount = in.nextLine();

                    if (amount == "" || amount == null || !amount.matches("[0-9]+")) {
                        clearConsole();
                        System.out.println("*** Payment ***\n");
                        System.out.println("Invalid amount.\n");
                        amount = "";
                    }
                }

                System.out.print("\n\nThank you for your payment! ");
                System.out.println("Funds will be ready to use in 4-24 hours.");

                // try {
                // PaypalConfirmationemail("group66club@gmail.com", "AppleBee", "AmandaScott");
                // } catch (IOException e) {
                // System.out.println(e.getMessage());
                // }

                // MemberBalance balance = new MemberBalance(member.getEmail(), amount, "0",
                // "0");

                try {
                    // System.out.println("*** Registration ***\n");
                    PendingPayments(member.getEmail(), amount);
                    // ApprovedPayments(member.getEmail(), balance);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("The amount is: " + amount);

                System.out.print("Check your balance(1)\t");
                System.out.print("Return to Main Screen(2)\t");
                System.out.print("Exit(3)\n");
                System.out.print("\n> ");

                int anotherInput = convertInputToInteger(3, 1);

                if (anotherInput == 1) {
                    clearConsole();
                    // System.out.println("Account balance: $" + balance);
                } else if (anotherInput == 2) {
                    clearConsole();
                    AfterLogIn(member);
                }
                // For exiting the annoucement feature.
                else if (anotherInput == 3) {
                    System.out.println("\nHave a nice day!\n");
                    System.exit(0);
                }

            } else if (input == 2) {
                clearConsole();
                AfterLogIn(member);
            }
            // For exiting the annoucement feature.
            else if (input == 3) {
                System.out.println("\nHave a nice day!\n");
                System.exit(0);
            }

            // System.out.println("member email: " + member.getEmail());
            // System.out.println("amount is: " + amount);

            // int balance = Integer.parseInt(amount);
            // System.out.println("Balance before top up: $" + member.getBalance());

            // member.setBalance(member.getBalance() + balance);
        } else if (option.equalsIgnoreCase("P")) {
            // insert make a practice schedule/scheduling method here
        } else if (option.equalsIgnoreCase("A")) {
            // insert make a attendance method here
        } else if (option.equalsIgnoreCase("E")) {
            System.out.println("\nHave a nice day\n");
            System.exit(0);
        }
    }

    // Clears the console
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /* 
        NOTE: Can only send through Gmail. Can send to any address.

        1. Go onto the sender's Gmail.
        2. Go to settings icon > See All Settings > Forwarding and POP/IMAP
        3. Enable IMAP access

        1. Go onto myaccount.google.com
        2. Scroll down to less secure app access
        3. Enable less secure app access
    */
    public static void sendAnnouncements(String coachEmail, String coachPassword, String fullname) throws IOException{
        final String username = coachEmail;
        final String password = coachPassword;

        //For a single person, get rid of from here
        ClubManager manager = null;
        try {
            manager = new ClubManager();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //to here
    
        System.out.println("Enter the subject line: ");
        String subj = in.nextLine();
        System.out.println("Enter the body of the email (with \\n for new lines): ");
        String body = "";
        String next;
        while(in.hasNextLine() && !(next = in.nextLine()).equals("")){
            body += next;
            body += "\n";
        }

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "imap.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(manager.emailsToString()) //For 1 person, just enter the email string ex: "kffjk322@gmail.com"
            );
            message.setSubject("** ANNOUNCEMENT **: " + subj);
            message.setText("Hello! \n\n"
                    + body + "\n\n" + fullname);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}