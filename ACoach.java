public class ACoach extends AMember{

    
    static String first;
    static String last;
    static String phoneNumber;
    static String email;
    static String password;
    static String role;

    public ACoach() {
        for (java.util.Map.Entry<String, AMember> entry : ClubManager.members.entrySet()) {
            AMember member = entry.getValue();
            if (member.getRole().equals("Coach")) {
                this.first = member.getFirstName();
                this.last = member.getLastName();
                this.phoneNumber = member.getPhoneNumber();
                this.email = member.getEmail();
                this.password = member.getPassword();
                this.role = member.getRole();
            }
        }
    }

    // Gets the first name of the member
    public String getFirstName()
    {
        return this.firstName;
    }

    // Sets the first name of the member
    public void setFirstName(String first)
    {
        this.firstName = first;
    }

    // Gets the last name of the member
    public String getLastName()
    {
        return this.lastName;
    }

    // Sets the last name of the member
    public void setLastName(String last)
    {
        this.lastName = last;
    }

    // Gets the phone number of the member
    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    // Sets the phone number of the member
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    // Gets the email of the member
    public String getEmail()
    {
        return this.email;
    }

    // Sets the email of the member
    public void setEmail(String email)
    {
        this.email = email;
    }

    // Gets the password of the member
    public String getPassword()
    {
        return this.password;
    }

    // Sets the password of the member
    public void setPassword(String password)
    {
        this.password = password;
    }

    // Gets the role of the member
    public String getRole()
    {
        return role;
    }

    // Sets the role of the member
    public void setRole(String role)
    {
        this.role = role;
    }

    // Gets the first name of the member
    public String getAddress()
    {
        return address;
    }

    // Sets the first name of the member
    public void setAddress(String add)
    {
        address = add;
    }

    public String toString(){
        return firstName + " " + lastName + " " + phoneNumber + " " + email + " " + password + " " + role + " " + address;
    }

}
