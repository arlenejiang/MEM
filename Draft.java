import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Map;

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
        System.out.println("\n\n");
    }

    public static void displayScheduleLog() throws FileNotFoundException {

        Map<String, Integer[]> tempLog = new TreeMap<String, Integer[]>();
        Map<String, Integer> tempMoneyLog = new TreeMap<String, Integer>();

        // Get scheduled classes
        for (Entry<String, AMember> entry : ClubManager.members.entrySet()) {
            AMember member = entry.getValue();
            if (member.getRole().equals("Member")) {
                Integer [] cl = new Integer[] {0, 0, 0, 0};
                if (member.getFirstClass()!=null){
                    cl[0] = 1;
                }
                if (member.getSecondClass()!=null){
                    cl[1] = 1;
                }if (member.getThirdClass()!=null){
                    cl[2] = 1;
                }if (member.getFourthClass()!=null){
                    cl[3] = 1;
                }
                tempLog.put(member.getEmail(), cl);
            }
        }

        // Get each member's funds
        for (String emailAdd : ATreasurer.balance.keySet()) {
            tempMoneyLog.put(emailAdd, ATreasurer.balance.get(emailAdd).getBalance());
        }
        
        // Output
        System.out.println("\n\nSCHEDULED PARTICIPANTS");

        LocalDate dt;
        LocalDate friday = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        for (int i = 0; i < 4; i++) {
            dt = friday.plusDays(i*7);
            System.out.println("\n"+dt);

            for (String emailAdd : tempLog.keySet()) {
                if (tempLog.get(emailAdd)[i] == 1) {
                    AMember m = ClubManager.members.get(emailAdd);
                    System.out.print(m.getFirstName() + " "+ m.getLastName() + " " + 
                    m.getPhoneNumber() + " " + m.getAddress());
                    
                    Integer money = tempMoneyLog.get(emailAdd) - 10;
                    tempMoneyLog.put(emailAdd, money);

                    if (tempMoneyLog.get(emailAdd) > 0 ){
                        System.out.print("\t PAID\n");
                    }
                    else {
                        System.out.print("\t UNPAID\n");
                    }

                }
            }
            System.out.println("\n");
        }
    }
            

        


    public static void writeAttendanceLog(LocalDate classToLog) throws IOException{
        if ((readAttendanceLog()).equals(classToLog)) {
            System.out.println("Already took today's attendance");
            return;
        }
        // Check time for a classToLog
        String pastAttendance;
        MemberBalance mb;

        System.out.println("\n\nPress X next to present members. Otherwise hit enter.");
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
                    
                    // Output to coach/treasurer
                    System.out.print(member.getFirstName() + " " + member.getLastName() + "\t");
                    if (MEM.in.nextLine().equalsIgnoreCase("X")) {
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
                }
                else {
                    member.setAttendance(pastAttendance + "0");   
                }
            }

        }
        ClubManager.toFile("Balances.txt");
        writeFile();
        

    }

    public static void writeFile() throws FileNotFoundException{
        new File("atttendance.txt");
    
        PrintWriter out = new PrintWriter("attendance.txt");
        out.println(LocalDate.now());
        
        for (AMember person: ClubManager.members.values())
        {
            if (person.getRole().equals("Member")) {
            out.print(person.getFirstName() + " " + person.getLastName() + " " + person.getEmail() + " ");
            out.print(person.getAttendance());
            out.println();
        }
        }
        out.flush();
        out.close();
    }
}

