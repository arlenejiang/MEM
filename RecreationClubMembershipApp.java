import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class RecreationClubMembershipApp {
    static Scanner in = new Scanner(System.in);
    public static void main(String args[]) {
        // Creates a manager object and catched IOException
        clearConsole();
        ClubMain manager = null;
        try {
            manager = new ClubMain();
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

    public static void RegisterationQuestions(ClubMain manager) throws IOException {

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
        manager.registerMember(firstName, lastName, email, password);
    }

    // Log in Feature
    public static void log_in() {
        System.out.print("Email: ");
        String email= in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();

        ClubMain club = null;
        try {
            club = new ClubMain();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (Map.Entry<String,AMember> entry : club.members.entrySet()){
            if(entry.getKey().equals(email)){
                AMember member = entry.getValue();
                if ((member.getPassword()).equals(password)) {
                    AfterLogIn(member);
                } else {
                    System.out.println("You have entered one or more of the following pieces of information incorrectly: username and/or password.");
                    System.out.println("Please try again");
                    log_in();
                }
            }
        }    
    }

    // After log in options for staff and players
    public static void AfterLogIn(AMember member) {
        clearConsole();

        // Gives the user the option of entering S to send announcement, F for finance feature, 
        // P for practice schedule/scheduling, and E to exit
        System.out.println("\n*** Welcome to the Recreation Club Membership App ***\n");
        if (member.getRole().equals("Coach")) {
            System.out.print("Send Annoucement (S)\t");
        }
        System.out.print("Finances (F)\t");
        System.out.print("Pratice Schedule (P)\n");
        System.out.print("Exit (E)");
        System.out.print("\n> ");

        String option = in.nextLine();

        // If the input is a 1, go to annoucemnets
        if (option.equalsIgnoreCase("S")) {
            // try {
                clearConsole();
                System.out.println("*** Send Announcement ***\n");
                // insert method for sending email through java code
                sendAnnouncement(member.getEmail());
            // } catch (IOException e) {
            //     System.out.println(e.getMessage());
            // }
            clearConsole();
            System.out.println("Annoucement Succesfully Sent\n");

            // Allows the user to choose if they want to return to the main screen or exit after senting a annoucement
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
        } else if (option.equalsIgnoreCase("F")) {
            // insert finance code method here
        } else if (option.equalsIgnoreCase("P")) {
            // insert make a practice schedule/scheduling method here
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

    public static void sendAnnouncement(String memberemail){
      ClubMain everyone = null; //Key-values of club members
      String[] emailList = everyone.getMembers(); //all members emails

      Address[] recipients;
      int index = 0;
      for (String email: emailList) 
        {
            recipients[index] = new InternetAddress(email); 
            index++;
        }

      // Sender's email ID needs to be mentioned
      String from = memberemail;

      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipients(Message.RecipientType.TO, recipients);

         // Set Subject: header field
         System.out.println("Enter the subject line: ");
         String subject = in.nextLine();
         message.setSubject(subject);

         // Now set the actual message
         System.out.println("Enter the announcement: ");
         String announcement = in.nextLine();
         message.setText(announcement);

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }

}


