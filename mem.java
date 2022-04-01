import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Scanner;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;

public class mem {

    static ArrayList<Player> playerList = new ArrayList<Player>();
    static ArrayList<Staff> staffList = new ArrayList<Staff>();
    static String[] recipients = new String[30];

    public static void main(String[] args) {
        
        /*
        converts the info in the text file into an 2D array
        */
        int num = 4;
        String[][] fileParts = new String[num][4];
        try 
        {
            File accountFile = new File("User_Info.txt");
            Scanner reader = new Scanner(accountFile);
            for (int i=0; reader.hasNextLine() && i<fileParts.length; i++) 
            {
                String [] parts = (reader.nextLine()).split(" ");
                fileParts[i] = parts;
                for(int j=0; j<fileParts[0].length; j++)
                {
                    fileParts[i][j]=fileParts[i][j].replaceAll("_", " ");
                    if(j==2){recipients[i]=(fileParts[i][j]);}//this line is part of the email SAN implementation
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e) 
        {
            System.out.println("File Not Found.");
        }
        
        /*
        Creates Player and Staff objects according to their first letter role in the txt file
        */
        for (String[] element : fileParts) 
        {
            //System.out.println(Arrays.toString(element));
            if(element[0].equals("P"))
            {
                playerList.add(new Player(element[1], element[2], element[3]));//(name, email, password) for Players
            }
            else if(element[0].equals("S"))
            {
                staffList.add(new Staff(element[1], element[2], element[3]));//(name, email, password) for Staff Members
            }
        } 
        //////////////////////////
        System.out.println("Welcome to Recreational Club!!!");
        System.out.println("1. Register (Type 1 or R)");
        System.out.println("2. Player Log In (Type 2 or P)");
        System.out.println("3. Staff Log In (Type 3 or S)");
        System.out.println("4. Quit (Type 4 or Q)");
        //////////////////////////

        /*
        The welcome screen had 4 options to choose from
        */
        Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
        {
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			Scanner commandLine = new Scanner(inputLine);
			String action = commandLine.next();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			else if (action.equalsIgnoreCase("QUIT") || action.equalsIgnoreCase("Q") || action.equals("4")){
				commandLine.close();//quits the program
				return;
			}

			else if (action.equalsIgnoreCase("REGISTER") || action.equalsIgnoreCase("R") || action.equals("1")){
				//register code
			}

            else if (action.equalsIgnoreCase("PLAYER LOG IN") || action.equalsIgnoreCase("P") || action.equals("2"))
			{
                log_in_player();//log in
			}

            else if (action.equalsIgnoreCase("STAFF LOG IN") || action.equalsIgnoreCase("S") || action.equals("3"))
			{
                log_in_staff();//log in

                //1.announcements --> message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
			}

            System.out.print("\n>");
			commandLine.close();
		}
		scanner.close();
    }

    public static void log_in_player(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        for(Player player: playerList){
            if(player.getEmail().equals(email) && player.getPassword().equals(password)){
                //send second authentication number (SAN) to email
                //email_SAN(player.getEmail());
                /*tried to implement a way to send a email that contains a randomly generated number
                which the user would enter and if it matches the random number that was saved
                then they proceed to the the home screen after logging in
                but for now that part will be left for Natasha to figure out since annoucements also
                need the emailing through java part so she can just copy and paste the code and adjust later
                */
                options();
            }
            else{
                System.out.println("You have entered one or more of the following pieces of information incorrectly: username and/or password.");
                System.out.println("Please try again");
                log_in_player();
            }
        }

        scanner.close();
    }

    public static void log_in_staff() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        for (Staff staff : staffList) {
            if (staff.getEmail().equals(email) && staff.getPassword().equals(password)) {
                // send second authentication number (SAN) to email
                // email_SAN(staff.getEmail());
                /*tried to implement a way to send a email that contains a randomly generated number
                which the user would enter and if it matches the random number that was saved
                then they proceed to the the home screen after logging in
                but for now that part will be left for Natasha to figure out since annoucements also
                need the emailing through java part so she can just copy and paste the code and adjust later
                */
                options();
            } else {
                System.out.println(
                        "You have entered one or more of the following pieces of information incorrectly: username and/or password.");
                System.out.println("Please try again");
                log_in_player();
            }
        }

        scanner.close();
    }

    // email secondary authentication number
    public static void email_SAN(String email) {

        // email ID of Recipient.
        String recipient = "recipient@gmail.com";

        // email ID of Sender.
        String sender = "sender@gmail.com";

        // using host as localhost
        String host = "127.0.0.1";

        // Getting system properties
        Properties properties = System.getProperties();

        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);

        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);

        try {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject("This is Subject");

            // set body of the email.
            message.setText("This is a test mail");

            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    //after log in options
    public static void options() {
        System.out.println("Welcome Back!!!");
        System.out.println("1. Announcements (Type 1 or A)");
        System.out.println("2. Finances (Type 2 or F)");
        System.out.println("4. Quit (Type 3 or Q)");
        //////////////////////////
        Scanner scanner = new Scanner(System.in);
		System.out.print(">");
        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            if (inputLine == null || inputLine.equals("")) {
                System.out.print("\n>");
                continue;
            }

            Scanner commandLine = new Scanner(inputLine);
            String action = commandLine.next();

            if (action == null || action.equals("")) {
                System.out.print("\n>");
                continue;
            } else if (action.equalsIgnoreCase("QUIT") || action.equalsIgnoreCase("Q") || action.equals("3")) {
                commandLine.close();// quits the program
                return;
            } else if (action.equalsIgnoreCase("ANNOUNCEMENTS") || action.equalsIgnoreCase("A") || action.equals("1")) {
                // 1.announcements --> message.addRecipient(Message.RecipientType.TO, new
                // InternetAddress(recipients));
            } else if (action.equalsIgnoreCase("FINANCES") || action.equalsIgnoreCase("F") || action.equals("2")) {
                //finances like logs and whatever the player or staff shoulf see
                //payment options for players
                //profit logs, payment history logs for staff
            }

            System.out.print("\n>");
            commandLine.close();
        }
        scanner.close();
    }

}
