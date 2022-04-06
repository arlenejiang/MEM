public class MemberBalance {

    String email;
    int balance;
    int numOfPayments;
    int missingPayments;

    // Default constructor method
    public MemberBalance() {
        this.email = "";
        this.balance = 0;
        this.numOfPayments = 0;
        this.missingPayments = 0;
    }

    // Constructor method for a member
    public MemberBalance(String email, int balance, int numOfPayments, int missingPayments) {
        this.email = email;
        this.balance = balance;
        this.numOfPayments = numOfPayments;
        this.missingPayments = missingPayments;
    }

    // Gets the email of the member
    public String getEmail() {
        return this.email;
    }

    // Sets the email of the member
    public void setEmail(String email) {
        this.email = email;
    }

    // Gets the balance of the member
    public int getBalance() {
        return this.balance;
    }

    // Sets the balance of the member
    public void setBalance(int amount) {
        this.balance = amount;
    }

    // Gets the number of payments made by a member
    public int getNumOfPayments() {
        return this.numOfPayments;
    }

    // Sets the number of payments made by a member
    public void setNumOfPayments(int num) {
        this.numOfPayments = num;
    }

    // Gets the number of payments a member missed
    public int getMissingPayment() {
        return this.missingPayments;
    }

    // Sets the number of payments a member missed
    public void setMissingPayment(int num) {
        this.missingPayments = num;
    }

}
