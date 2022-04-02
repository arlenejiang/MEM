import java.io.IOException;
import java.util.Scanner;

public class RecreationClubMembershipApp {
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
        Scanner in = new Scanner(System.in);
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
        in.close();
        return input;

    }

    public static void RegisterationQuestions(ClubMain manager) throws IOException {
        Scanner input1 = new Scanner(System.in);

        // Asks for the first name of the user
        String firstName = "";
        while (firstName == "" || firstName == null) {
            System.out.print("Enter your first name: ");
            firstName = input1.nextLine();
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
            lastName = input1.nextLine();
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
            email = input1.nextLine();
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
                password = input1.nextLine();

                if (password.length() < 8) {
                    clearConsole();
                    System.out.println("*** Registration ***\n");
                    System.out.println("Password needs to be at least 8 characters.");
                }
                System.out.println();
            }

            System.out.print("Re-Enter your password: ");
            password2 = input1.nextLine();

            if (password.equals(password2) == false) {
                clearConsole();
                System.out.println("*** Registration ***\n");
                System.out.println("Passwords do not match. Please enter your password again.");
                System.out.println();
                password = "-1";
            }
        }
        input1.close();
        manager.registerMember(firstName, lastName, email, password);
    }

    // Log in Feature
    public static void log_in() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        ClubMain club = null;
        try {
            club = new ClubMain();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (AMember member : club.members.values()) {
            if ((member.getEmail()).equals(email) && (member.getPassword()).equals(password)) {
                AfterLogIn(member);
            } else {
                System.out.println(
                        "You have entered one or more of the following pieces of information incorrectly: username and/or password.");
                System.out.println("Please try again");
                log_in();
            }
        }

        scanner.close();
    }

    // After log in options for staff and players
    public static void AfterLogIn(AMember member) {
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
        if (member.getRole().equals("Coach")) {
            System.out.print("Send Annoucement (S)\t");
        }
        System.out.print("Finances (F)\t");
        System.out.print("Pratice Schedule (P)\n");
        System.out.print("Exit (E)");
        System.out.print("\n> ");

        Scanner scanner = new Scanner(System.in);
        String in = scanner.next();

        // If the input is a 1, go to annoucemnets
        if (in.equalsIgnoreCase("S")) {
            // try {
                clearConsole();
                System.out.println("*** Send Announcement ***\n");
                // insert method for sending email through java code
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
        } else if (in.equalsIgnoreCase("F")) {
            // insert finance code method here
        } else if (in.equalsIgnoreCase("P")) {
            // insert make a practice schedule/scheduling method here
        } else if (in.equalsIgnoreCase("E")) {
            System.out.println("\nHave a nice day\n");
            System.exit(0);
        }
        scanner.close();
    }

    // Clears the console
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
