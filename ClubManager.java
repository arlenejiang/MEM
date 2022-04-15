import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class ClubManager {

    static Map<String, AMember> members = new TreeMap<String, AMember>();
    static ArrayList<MemberBalance> balancesSort = new ArrayList<MemberBalance>();

    Scanner in = new Scanner(System.in);
    File file = new File("User_Info.txt");
    
    //File file2 = null;
	//static Scanner scanner = null; 

    public ClubManager() throws IOException {
    	//file2 = new File("scheduledClasses.txt");
    	//scanner = new Scanner(file2);
        fromFile(file);
    }

    // Add's a new member to the member's treeMap.
    // Writes out all the members to User_Info.txt
  
    public void registerMember(String first, String last, String phoneNumber, String email, String password, String address) throws IOException {
        AMember person = new AMember(first, last, phoneNumber, email, password, "Member", address);
        members.put(email, person);

        toFile("User_Info.txt");
    }

    // Checks the user's email with the emails of the members already in the club
    // If the email is found, return true, else return false
    public boolean checkEmail(String userEmail) {
        for (String email : members.keySet()) {
            if (email.equals(userEmail)) {
                return true;
            }
        }
        return false;
    }

    public String emailsToString() {
        String allEmails = "";
        for (String email : members.keySet()) {
            allEmails += email + ", ";
        }
        String removeSplice = "";
        if (allEmails != null && allEmails.length() > 1) {
            removeSplice += allEmails.substring(0, allEmails.length() - 2);
        }

        return removeSplice;
    }

    public void printAllNamesEmails(){
        String full = "";
        for (String email : members.keySet()){
            full += members.get(email).getFirstName() + " " + members.get(email).getLastName() + " " + email + "\n";
        }
        System.out.println("Type one of the following emails: \n" + full);
    }

    public static void fromFile(File file) {

        Scanner sc = null;
        String line;
        Scanner word = null;
        try {
            sc = new Scanner(new FileInputStream(file));

            if (file.equals(new File("User_Info.txt"))) {
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    word = new Scanner(line);

                    AMember person = new AMember(word.next(), word.next(), word.next(), word.next(), word.next(),
                        word.next(), word.next());
                    readScheduledDates(person);
                    members.put(person.getEmail(), person);
                    //System.out.println(person.toString());//debugging for testing
                }
                //System.out.println("Import Donefrom1");
            }else if (file.equals(new File("finances.txt"))) {
                String lastUpdate = sc.nextLine().substring(0, 10);

                while ((sc.nextLine()).equals("Unpaid Monthly Rent")) {}
                sc.nextLine();
                // Add previous unpaid months from file to list rentMonths
                line = sc.nextLine();
                while (line.length() > 0) {
                    Finances.rentMonths.add(line);
                    line = sc.nextLine();
                }
                // Add new unpaid months to list rentMonths
                Finances.rentCharge(lastUpdate);

                // Add previous unpaid months from file to list coachFees
                sc.nextLine();
                while (sc.hasNextLine()) {
                    Finances.coachFees.add(sc.nextLine());
                }
                //System.out.println("Import Donefrom2");
            } else if (file.equals(new File("PendingPayments.txt"))) {
                String email;
                int amount;

                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    //System.out.println(line);////////debugging
                    word = new Scanner(line);

                    email = word.next();
                    amount = Integer.parseInt(word.next());
                    
                    ATreasurer.payments.put(email, amount);
                }
                //System.out.println("Import Donefrom3");
            } else if (file.equals(new File("Balances.txt"))) {
                while (sc.hasNextLine()) {

                    line = sc.nextLine();
                    //System.out.println(line);////////debugging
                    word = new Scanner(line);
                    MemberBalance person = new MemberBalance(word.next(), word.nextInt(), word.nextInt(), word.nextInt());

                    ATreasurer.balance.put(person.getEmail(), person);

                    balancesSort.add(person); // adds member balances to the array list
                    // for (int i = 0; i < balancesSort.size(); i++)
                    // System.out.println(balancesSort.get(i));
                    // // System.out.println(person);
                    // System.out.println("\nAfter\n");

                    word.close();
                }
                // System.out.println("Import Donefrom4");
                // System.out.println("Outside\n"); For Debugging
                // for (int i = 0; i < balancesSort.size(); i++)
                // System.out.println(balancesSort.get(i));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
        sc.close();
    }

    public static void toFile(String fileName) throws IOException{

        FileWriter fw = null; 
        BufferedWriter bw = null;
        PrintWriter pw = null;

        FileWriter writer = new FileWriter(fileName);
        writer.close(); 

        try {
            fw = new FileWriter(fileName, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            if(fileName.equals("User_Info.txt")){
                for (Entry<String, AMember> entry : members.entrySet()){
                    AMember member = entry.getValue();
                    pw.println(member.toString());
                }
                //System.out.println("Import Doneto1");
            }else if(fileName.equals("PendingPayments.txt")){
                for (Entry<String, Integer> email : ATreasurer.payments.entrySet()){
                    int amount = email.getValue();
                    pw.println(email.getKey() + " " + String.valueOf(amount));
                }
                //System.out.println("Import Doneto2");
            }
            else if(fileName.equals("Balances.txt")){
                for (Entry<String, MemberBalance> entry : ATreasurer.balance.entrySet()){
                    MemberBalance str = entry.getValue();
                    pw.println(str.toString());
                }
                //System.out.println("Import Doneto3");
            }
            //System.out.println("Data Successfully appended into file");
            pw.flush();
        } finally {
            try {
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }
  
    public void scheduleClass(AMember member, int input, LocalDate date) throws FileNotFoundException
    {
    	for (String email: members.keySet())
        {
    		if (email.equals(member.getEmail()))
    		{
    			AMember mem = members.get(email);
    			if (input == 1)
    	    	{
    				if (mem.getFirstClass() == null)
    				{
    					mem.setFirstClass(date);
    					clearConsole();
    					System.out.println("You have scheduled a class on " + date.getDayOfMonth() + " " +  date.getMonth() + ", " + date.getYear() + ".\n");
    				}
    				else if (mem.getFirstClass().equals(date))
    				{
    					clearConsole();
    					System.out.println("You are already scheduled for this class on " + date.getDayOfMonth() + " " +  date.getMonth() + ", " + date.getYear() + ".\n");
    				}
    	    	}
    	    	else if (input == 2)
    	    	{
    	    		if (mem.getSecondClass() == null)
    				{
    	    			clearConsole();
    					mem.setSecondClass(date);
    					System.out.println("You have scheduled a class for " + date.getDayOfMonth() + " " +  date.getMonth() + ", " + date.getYear() + ".\n");
    				}
    				else if (mem.getSecondClass().equals(date))
    				{
    					clearConsole();
    					System.out.println("You are already scheduled for this class on " + date.getDayOfMonth() + " " +  date.getMonth() + ", " + date.getYear() + ".\n");
    				}
    	    	}
    	    	else if (input == 3)
    	    	{
    	    		if (mem.getThirdClass() == null)
    				{
    	    			clearConsole();
    					mem.setThirdClass(date);
    					System.out.println("You have scheduled a class for " + date.getDayOfMonth() + " " +  date.getMonth() + ", " + date.getYear() + ".\n");
    				}
    				else if (mem.getThirdClass().equals(date))
    				{
    					clearConsole();
    					System.out.println("You are already scheduled for this class on " + date.getDayOfMonth() + " " +  date.getMonth() + ", " + date.getYear() + ".\n");
    				}
    	    	}
    	    	else if (input == 4)
    	    	{
    	    		if (mem.getFourthClass() == null)
    				{
    	    			clearConsole();
    					mem.setFourthClass(date);
    					System.out.println("You have scheduled a class for " + date.getDayOfMonth() + " " +  date.getMonth() + ", " + date.getYear() + ".\n");
    				}
    				else if (mem.getFourthClass().equals(date))
    				{
    					clearConsole();
    					System.out.println("You are already scheduled for this class on " + date.getDayOfMonth() + " " +  date.getMonth() + ", " + date.getYear() + ".\n");
    				}
    	    	}
    		}
        }
    	
    	PrintWriter out = new PrintWriter("scheduledClasses.txt");
        
        for (AMember person: members.values())
        {
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
        out.flush();
        out.close();
    	
    }
    
    public static void readScheduledDates(AMember member) throws IOException 
    {
    	String line = "";
    	String email = "";
    	LocalDate date = null;
    	File file2 = new File("scheduledClasses.txt");
    	Scanner scanner = new Scanner(file2);
    	Scanner word = null;
    	member.setNull();
    	
    	while (scanner.hasNextLine())
    	{
    		line = scanner.nextLine();
    		word = new Scanner (line);
    		word.next();
    		word.next();
    		email = word.next();
    		if (email.equals(member.getEmail()))
    		{
    			if (word.hasNext())
    			{
    				date = LocalDate.parse(word.next());
    				checkValidDate(date, member);
    			}
    			if (word.hasNext())
    			{
    				date = LocalDate.parse(word.next());
    				checkValidDate(date, member);
    			}
    			if (word.hasNext())
    			{
    				date = LocalDate.parse(word.next());
    				checkValidDate(date, member);
    			}
    			if (word.hasNext())
    			{
    				date = LocalDate.parse(word.next());
    				checkValidDate(date, member);
    			}
    		}
    	}
        word.close();
        scanner.close();
    }
    
    public static void checkValidDate(LocalDate date, AMember member)
    {
    	LocalDate dt = LocalDate.now();
    	//LocalDate dt = LocalDate.parse("2022-04-23");
		LocalDate firstFriday = dt.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
		LocalDate secondFriday = firstFriday.plusDays(7);
		LocalDate thirdFriday = secondFriday.plusDays(7);
		LocalDate fourthFriday = thirdFriday.plusDays(7);
		
		if(date.equals(firstFriday))
			member.setFirstClass(date);
		else if (date.equals(secondFriday))
			member.setSecondClass(date);
		else if (date.equals(thirdFriday))
			member.setThirdClass(date);
		else if (date.equals(fourthFriday))
			member.setFourthClass(date);
    }
    
	public static void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

    // for debugging
    public static void print() {
        System.out.println("\nprint from print()\n");
        for (int i = 0; i < balancesSort.size(); i++)
            System.out.println(balancesSort.get(i));
    }

    public static void sortPaid() {
        Collections.sort(balancesSort);
    }

    private static class UnpaidComparator implements Comparator<MemberBalance> {
        public int compare(MemberBalance missingPayment1, MemberBalance missingPayment2) {
            return Integer.compare(missingPayment1.getMissingPayments(), missingPayment2.getMissingPayments());
        }
    }

    public static void sortUnpaid() {
        Collections.sort(balancesSort, new UnpaidComparator());

    }

    public static void printSortedList(String option) {

        // System.out.println("Unsorted List");
        // System.out.println();

        // for (int i = 0; i < balancesSort.size(); i++)
        // System.out.println(balancesSort.get(i));

        if (option.equalsIgnoreCase("P")) {
            sortPaid();
            System.out.println("\tMember\t\t\t\tNumber Of Payments\n");

            for (int i = 0; i < balancesSort.size(); i++)
                System.out.printf("%-50s%-50d\n", balancesSort.get(i).email, balancesSort.get(i).numOfPayments);

        }

        else if (option.equalsIgnoreCase("U")) {
            sortUnpaid();
            System.out.println("\tMember\t\t\t\tNumber Of Missed Payments\n");

            for (int i = 0; i < balancesSort.size(); i++)
                System.out.printf("%-50s%-50d\n", balancesSort.get(i).email, balancesSort.get(i).missingPayments);
        }

        System.out.println();

    }
}