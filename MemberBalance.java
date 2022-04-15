public class MemberBalance implements Comparable<MemberBalance> {

    String email;
    int balance;
    int numOfPayments;
    int missingPayments;

    // Constructor method for a member
    public MemberBalance(String email, int balance, int numOfPayments, int missingPayments) {
        this.email = email;
        this.balance = balance;
        this.numOfPayments = numOfPayments;
        this.missingPayments = missingPayments;
    }

    // Gets the email of the member
    public String getEmail() {
        return email;
    }

    // Sets the email of the member
    public void setEmail(String str) {
        email = str;
    }

    // Gets the balance of the member
    public int getBalance() {
        return balance;
    }

    // Sets the balance of the member
    public void setBalance(int amount) {
        balance = amount;
    }

    // Gets the number of payments made by a member
    public int getNumOfPayments() {
        return numOfPayments;
    }

    // Sets the number of payments made by a member
    public void setNumOfPayments(int num) {
        numOfPayments = num;
    }

    // Gets the number of payments a member missed
    public int getMissingPayments() {
        return missingPayments;
    }

    // Sets the number of payments a member missed
    public void setMissingPayments(int num) {
        missingPayments = num;
    }

    public void updateBalance(int amount) {
        balance += amount;
    }

    public void updateNumOfPayments() {
        numOfPayments += 1;
    }

    public void updateMissingPayments() {
        missingPayments += 1;
    }

    public String toString() {
        return email + " " + String.valueOf(balance) + " " + String.valueOf(numOfPayments) + " "
                + String.valueOf(missingPayments);
    }

    public int compareTo(MemberBalance other) {
        return this.numOfPayments - other.numOfPayments;
    }
}
