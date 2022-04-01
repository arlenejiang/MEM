import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class mem {
    
    static ArrayList<Player> studentList = new ArrayList<Player>();
    static ArrayList<Staff> staffList = new ArrayList<Staff>();

    public static void main(String[] args) {
        
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Enter Number of Students")
        // int num = scanner.nextint();
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
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e) 
        {
            System.out.println("File Not Found.");
        }
        
        //Add the accounts from txt file to arraylist of Players and Staff
        for (String[] element : fileParts) 
        {
            //System.out.println(Arrays.toString(element));
            if(element[0].equals("P"))
            {
                studentList.add(new Player(element[1], element[2], element[3]));//(name, email, password) for Players
            }
            else if(element[0].equals("S"))
            {
                staffList.add(new Staff(element[1], element[2], element[3]));//(name, email, password) for Staff Members
            }
        } 
        //////////////////////////
        System.out.println("Welcome to Recreational Club!!!");
        System.out.println("1. Register (Type 1 or R)");
        System.out.println("2. {Player Log In (Type 2 or P)");
        System.out.println("3. Staff Log In (Type 3 or S)");
        System.out.println("4. Quit (Type 4 or Q)");
        //////////////////////////
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

			else if (action.equalsIgnoreCase("QUIT") || action.equalsIgnoreCase("Q") || action.equals("3")){
				commandLine.close();//quits the program
				return;
			}

			else if (action.equalsIgnoreCase("REGISTER") || action.equalsIgnoreCase("R") || action.equals("1")){
				//register code
			}

            else if (action.equalsIgnoreCase("PLAYER LOG IN") || action.equalsIgnoreCase("P") || action.equals("2"))
			{
                log_in();//log in
			}

            else if (action.equalsIgnoreCase("STAFF LOG IN") || action.equalsIgnoreCase("S") || action.equals("2"))
			{
                log_in();//log in
			}

            System.out.print("\n>");
			commandLine.close();
		}
		scanner.close();
    }

    public static void log_in(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();


    }
}
