public class Expense {

    String month;
    String year;
    String amount;
    String feeType;

   
    // Constructor method for a member
    public Expense(String month, String year, String amount, String feeType)
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
    public String getAmount()
    {
        return this.amount;
    }
}
