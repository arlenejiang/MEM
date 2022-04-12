public class MemberBalance {

    String email;
    String balance;
    String numOfPayments;
    String missingPayments;

    // Default constructor method
    public MemberBalance() {
        this.email = "";
        this.balance = "";
        this.numOfPayments = "";
        this.missingPayments = "";
    }

    // Constructor method for a member
    public MemberBalance(String email, String balance, String numOfPayments, String missingPayments) {
        this.email = email;
        this.balance = balance;
        this.numOfPayments = numOfPayments;
        this.missingPayments = missingPayments;
    }

    public MemberBalance(String email){
        this.email = email;
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
    public String getBalance() {
        return this.balance;
    }

    // Sets the balance of the member
    public void setBalance(String amount) {
        this.balance = amount;
    }

    // Gets the number of payments made by a member
    public String getNumOfPayments() {
        return this.numOfPayments;
    }

    // Sets the number of payments made by a member
    public void setNumOfPayments(String num) {
        this.numOfPayments = num;
    }

    // Gets the number of payments a member missed
    public String getMissingPayments() {
        return this.missingPayments;
    }

    // Sets the number of payments a member missed
    public void setMissingPayments(String num) {
        this.missingPayments = num;
    }

    public String updateBalance(int amount) {
        return Integer.toString(Integer.parseInt(this.balance) + amount);
    }

    public String updateNumOfPayments() {
        return Integer.toString(Integer.parseInt(this.numOfPayments) + 1);
    }

    public String updateMissingPayments() {
        return Integer.toString(Integer.parseInt(this.missingPayments) + 1);
    }
}
