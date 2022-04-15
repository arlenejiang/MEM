import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;
import java.util.Map.Entry;

public class Draft {

    public static LocalDate readAttendanceLog() throws FileNotFoundException {
        File attendanceFile = new File("attendance.txt");
    	Scanner scanner = new Scanner(attendanceFile);
        String line;
        String email;

        
        LocalDate dt = LocalDate.parse(scanner.next());
        scanner.nextLine();
        while (scanner.hasNextLine())
    	{
    		line = scanner.nextLine();
    		Scanner word = new Scanner (line);
    		word.next();
    		word.next();
    		email = word.next();
            if (ClubManager.members.containsKey(email))
    		{
    			if (word.hasNext())
    			{
    				ClubManager.members.get(email).setAttendance(word.next());
    			}
    	    }
            word.close();
        }
        scanner.close();

        return dt;
    }
    
    public static void displayAttendanceLog() throws FileNotFoundException {

        MEM.clearConsole();
        
        // Calculate previous Fridays 
        LocalDate dt = readAttendanceLog();
		LocalDate friday = dt.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY)); //previousOrSame

        // Output attendance log
        for (int weeksAgo = 11; weeksAgo >= 0; weeksAgo--) {
            System.out.println("\n\n" + friday.minusDays(weeksAgo * 7));
            for (String emailAdd : ClubManager.members.keySet()) {
                AMember m = ClubManager.members.get(emailAdd);
                if (m.getAttendance() != null && 
                m.getAttendance().substring(11-weeksAgo, 12-weeksAgo).equals("1")) {
                    System.out.println(m.getFirstName() + " "+ m.getLastName() + " " + 
                    m.getPhoneNumber() + " " + m.getAddress() + "\tPAID");
                }
                if (m.getAttendance() != null && 
                m.getAttendance().substring(11-weeksAgo, 12-weeksAgo).equals("2")) {
                    System.out.println(m.getFirstName() + " "+ m.getLastName() + " " + 
                    m.getPhoneNumber() + " " + m.getAddress()+ "\tUNPAID");
                }
            }
        }
    }
    public static void writeAttendanceLog(LocalDate classToLog) throws IOException{
        readAttendanceLog();
        // Check time for a classToLog
        String pastAttendance;
        MemberBalance mb;
        for (Entry<String, AMember> entry : ClubManager.members.entrySet()) {
            AMember member = entry.getValue();
            if (member.getRole().equals("Member")) {
                if ( member.getAttendance() == null) {
                    pastAttendance = "00000000000";
                }
                else{
                    pastAttendance = member.getAttendance().substring(1);
                }
                
                if (member.getFirstClass() != null && member.getFirstClass().equals(classToLog)){
                    mb = ATreasurer.balance.get(member.getEmail());
                    mb.updateBalance(-10);
                    if (mb.getBalance() >= 0) {
                        member.setAttendance(pastAttendance + "1");
                    }
                    else {
                        member.setAttendance(pastAttendance + "2");
                        mb.updateMissingPayments();
                    }
                }
                else {
                    member.setAttendance(pastAttendance + "0");   
                }
            }
            ClubManager.toFile("Balances.txt");
            writeFile();
        }

    }

    public static void writeFile() throws FileNotFoundException{
        new File("atttendance.txt");
    
        PrintWriter out = new PrintWriter("attendance.txt");
        
        for (AMember person: ClubManager.members.values())
        {
            if (person.getRole().equals("Member")) {
            out.print(person.getFirstName() + " " + person.getLastName() + " " + person.getEmail() + " ");
            //System.out.print(person.getFirstName() + "," + person.getFirstClass() + " ");
            if (person.getFirstClass() != null)
            {
            	out.print(person.getFirstClass() + " ");
            }
            if (person.getSecondClass() != null)
            {
            	out.print(person.getSecondClass() + " ");
            }
            if (person.getThirdClass() != null)
            {
            	out.print(person.getThirdClass() + " ");
            }
            if (person.getFourthClass() != null)
            {
            	out.print(person.getFourthClass());
            }
            out.println();
        }
        }
        out.flush();
        out.close();
    }
}

