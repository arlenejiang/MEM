import java.io.IOException;
import java.util.Scanner;

public class RecreationClubMembershipApp {
    public static void main(String args[])
    {
        // Creates a manager object and catched IOException
        ClubManager manager = null;
        try
        {
            manager = new ClubManager();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        // Gives the user the option of registering or logging in
        // Enter 1 to register, enter 2 to log in
        System.out.println("\n***Welcome to the Recreation Club Membership App***\n");
        System.out.print("Register(1)\t");
        System.out.print("Login(2)\n");

        Scanner scanner = new Scanner(System.in);
        System.out.print("\n> ");

        Boolean checkValidInput = false; // Checks to make sure input is a number
        int input = 0; // User chooses what they want to do in the app 
        while (checkValidInput == false || (input != 1 && input != 2)) // 
        {
            // Convert the input to an integer.
            // If the input is not an integer, or is not equal to 1 or 2, show error messages
            // Keep getting the input until a valid number is entered
            try
            {
                input = Integer.parseInt(scanner.nextLine());
                checkValidInput = true;
                if (input != 1 && input != 2)
                {
                    System.out.println("\nPlease enter a valid number (1 or 2)");
                    System.out.print("> ");
                    checkValidInput = false;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("\nPlease enter 1 or 2");
                System.out.print("> ");
            }
        }

        // If the input is a 1, allow the user to register
        if (input == 1)
        {
            try 
            {
                System.out.println();
                RegisterationQuestions(manager); // Shows the registration questions
            }
            catch(IOException e)
            {
                System.out.println(e.getMessage());
            }

            System.out.println("\nRegistration Complete\n");

            // Allows the user to choose if they want to login or exit after registering
            // Enter 1 to login, enter 2 to exit.
            System.out.print("Login(1)\t");
            System.out.print("Exit(2)\n");
            System.out.print("\n> ");

            checkValidInput = false;
            while (checkValidInput == false || (input != 1 && input != 2))
            {
                // Convert the input to an integer.
                // If the input is not an integer, or is not equal to 1 or 2, show error messages
                // Keep getting the input until a valid number is entered
                try
                {
                    input = Integer.parseInt(scanner.nextLine());
                    checkValidInput = true;
                    if (input != 1 && input != 2)
                    {
                        System.out.println("\nPlease enter a valid number (1 or 2)");
                        System.out.print("> ");
                        checkValidInput = false;
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("\nPlease enter 1 or 2");
                    System.out.print("> ");
                }
            }

            // Exit if the user chooses number 2
            if (input == 2)
            {
                System.out.println("\nHave a nice day\n");
                System.exit(0);
            }
            // If the user want to login, change input to 2.
            // The next if statement will allow logging in when input = 2.
            if (input == 1)
            {
                input = 2;
            }  
        }

        // For logging in.
        if (input == 2)
        {

        }
    }

    public static void RegisterationQuestions(ClubManager manager) throws IOException
    {
        Scanner input1 = new Scanner(System.in);

        // Asks for the first name of the user
        String firstName = "";
        while (firstName == "" || firstName == null)
        {
            System.out.print("Enter your first name: ");
            firstName = input1.nextLine();
        }
        System.out.println();

        // Asks for the last name of the user
        String lastName = "";
        while (lastName == "" || lastName == null)
        {
            System.out.print("Enter your last name: ");
            lastName = input1.nextLine();
        }
        System.out.println();

        // Asks for the email of the user
        // Makes sure that a person cannot register with an email that is already being used
        Boolean emailFound = true;
        String email = "";
        while (emailFound == true || email == "" || email == null)
        {
            System.out.print("Enter your email: ");
            email = input1.nextLine();
            emailFound = manager.checkEmail(email);

            if (emailFound == true)
            {
                System.out.println("This email is already being used by a current member. Please enter another email.\n");
            }
        }
        System.out.println();

        // Asks user for a password.
        // Makes sure password is longer than 8 characters
        String password = "";  
        while (password.length() < 8)
        {
            System.out.print("Enter your password: ");
            password = input1.nextLine();

            if (password.length() < 8)
            {
                System.out.println("Password needs to be at least 8 characters.");
            }
            System.out.println();
        }

        // Asks user to verify password
        // Makes sure that the password is the same as the password entered before.
        String password2 = "";
        while (password.equals(password2) == false)
        {
            System.out.print("Re-Enter your password: ");
            password2 = input1.nextLine();

            if (password.equals(password2) == false)
            {
                System.out.println("Passwords do not match. Please enter your password again.");
                System.out.println();
            }
        }
        manager.registerMember(firstName, lastName, email, password);
    }
}
