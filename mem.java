import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class mem {
    
    static ArrayList<Player> studentList = new ArrayList<Player>();
    static ArrayList<Staff> staffList = new ArrayList<Staff>();

    public static void main(String[] args) {
        
        String email;
        String password;
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Enter Number of Students")
        // int num = scanner.nextint();
        int num = 4;
        String[][] fileParts = new String[num][4];
        try {
            File accountFile = new File("User_Info.txt");
            Scanner reader = new Scanner(accountFile);
            for (int i=0; reader.hasNextLine() && i<fileParts.length; i++) {
                String [] parts = (reader.nextLine()).split(" ");
                fileParts[i] = parts;
                for(int j=0; j<fileParts[0].length; j++){
                    fileParts[i][j]=fileParts[i][j].replaceAll("_", " ");
                }
            }
            reader.close();
          }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
          }
          // Add the accounts from txt file to arraylist of Players and Staff
          for(int i=0; ){
              if(){
                studentList.add(new Player(email, password));
              }
              else if(){
                  staffList.add(new Staff(email, password));
              }
          }
    }
}
