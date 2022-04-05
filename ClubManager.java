import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ClubManager 
{

    Map <String, AMember> members = new TreeMap<String, AMember>();

    public ClubManager() throws IOException
    {
        File input = new File("User_Info.txt");
        Scanner in = new Scanner(input);
        
        String member = "";
        String firstName = "";
        String lastName = "";
        String phoneNumber = "";
        String email = "";
        String password = "";
        String role = "";

        // Reads each member's info from the User_Info.txt file.
        // Adds the member's email and the member to the treeMap
        while (in.hasNextLine())
        {
            member = in.nextLine();
            Scanner word = new Scanner(member);

            firstName = word.next();
            lastName = word.next();
            phoneNumber = word.next();
            email = word.next();
            password = word.next();
            role = word.next();

            AMember person = new AMember(firstName, lastName, phoneNumber, email, password, role);
            members.put(email, person);
            word.close();
        }

        in.close();
    }

    // Add's a new member to the member's treeMap.
    // Writes out all the members to User_Info.txt
    public void registerMember(String first, String last, String phoneNumber, String email, String password) throws FileNotFoundException
    {
        AMember person = new AMember(first, last, phoneNumber, email, password, "Member");
        members.put(email, person);

        PrintWriter out = new PrintWriter("User_Info.txt");
        
        for (AMember member: members.values())
        {
            out.println(member.getFirstName() + " " + member.getLastName() + " " + member.getPhoneNumber() + " " + member.getEmail() + " " + member.getPassword() + " " + member.getRole());
        }

        out.close();
    }

    // Checks the user's email with the emails of the members already in the club
    // If the email is found, return true, else return false
    public boolean checkEmail(String userEmail)
    {
        for (String email: members.keySet())
        {
            if (email.equals(userEmail))
            {
                return true;
            }
        }
        return false;
    }
    
}
