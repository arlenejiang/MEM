public class AMember {

    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    String password;
    String role;
    String address;

    // Default constructor method
    public AMember() {
        this.firstName = "";
        this.lastName = "";
        this.phoneNumber = "";
        this.email = "";
        this.password = "";
        this.role = "Member";
        this.address = "";
    }

    // Constructor method for a member
    public AMember(String first, String last, String phoneNumber, String email, String password, String role,
            String address) {
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
        this.address = address;
    }

    // Gets the first name of the member
    public String getFirstName() {
        return this.firstName;
    }

    // Sets the first name of the member
    public void setFirstName(String first) {
        this.firstName = first;
    }

    // Gets the last name of the member
    public String getLastName() {
        return this.lastName;
    }

    // Sets the last name of the member
    public void setLastName(String last) {
        this.lastName = last;
    }

    // Gets the phone number of the member
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    // Sets the phone number of the member
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Gets the email of the member
    public String getEmail() {
        return this.email;
    }

    // Sets the email of the member
    public void setEmail(String email) {
        this.email = email;
    }

    // Gets the password of the member
    public String getPassword() {
        return this.password;
    }

    // Sets the password of the member
    public void setPassword(String password) {
        this.password = password;
    }

    // Gets the role of the member
    public String getRole() {
        return role;
    }

    // Sets the role of the member
    public void setRole(String role) {
        this.role = role;
    }

    // Gets the first name of the member
    public String getAddress() {
        return address;
    }

    // Sets the first name of the member
    public void setAddress(String add) {
        address = add;
    }

    public String toString() {
        return firstName + " " + lastName + " " + phoneNumber + " " + email + " " + password + " " + role + " "
                + address;
    }

}