public class Expense {

    String month;
    String year;
    int amount;
    String feeType;

   
    // Constructor method for a member
    public Expense(String month, String year, int amount, String feeType)
    {
        this.month = month;
        this.year = year;
        this.amount = amount;
        this.feeType = feeType;
    }

    // Gets the first name of the member
    public String getMonth()
    {
        return this.month;
    }


    // Gets the last name of the member
    public String getYear()
    {
        return this.year;
    }

    // Gets the email of the member
    public int getAmount()
    {
        return this.amount;
    }

    public String printType() {
        if (feeType.equals("Rent")){
            return "Hall Rent (" + month + ")";
        }
        return feeType;
    }

    public void toPrint() {
        System.out.printf(month.substring(0,3) + " 1, " + year);
        System.out.printf("\t$%d\t", amount);
        System.out.print(printType() + "\n");
    }
}
