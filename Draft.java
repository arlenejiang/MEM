import java.io.File;
import java.io.FileNotFoundException;
import java.time.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;

public class Draft {
    
    public static void displayAttendanceLog() throws FileNotFoundException {

        File attendanceFile = new File("attendance.txt");
    	Scanner scanner = new Scanner(attendanceFile);
        String line;
        String email;

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
        }

        // Calculate 12 Fridays ago
        LocalDate dt = LocalDate.now();
		LocalDate friday = dt.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)); //previousOrSame

        for (int weeksAgo = 12; weeksAgo > 0; weeksAgo--) {
            System.out.println("\n\n" + friday.minusDays(weeksAgo * 7));
            for (String emailAdd : ClubManager.members.keySet()) {
                AMember m = ClubManager.members.get(emailAdd);
                if (m.getAttendance() != null && 
                m.getAttendance().substring(12-weeksAgo, 13-weeksAgo).equals("1")) {
                    System.out.println(m.getFirstName() + " "+ m.getLastName() + " " + 
                    m.getPhoneNumber() + "PAID " + m.getAddress());
                }
                if (m.getAttendance() != null && 
                m.getAttendance().substring(12-weeksAgo, 13-weeksAgo).equals("2")) {
                    System.out.println(m.getFirstName() + " "+ m.getLastName() + " " + 
                    m.getPhoneNumber() + " UNPAID " + m.getAddress());
                }
            }
        }


        // Read from dates attendance.txt into members

        // Output logs by date 

            // Calculate and print the date of Friday (4th, 3rd, 2nd, 1st most recent)
            // Output subject if one of the getClass variables==date
            // if (m.getFirstClass().equals(date) ||...


    }
}
