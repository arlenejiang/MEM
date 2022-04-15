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
                ACoach.first = member.getFirstName();
                ACoach.last = member.getLastName();
                ACoach.phoneNumber = member.getPhoneNumber();
                ACoach.email = member.getEmail();
                ACoach.password = member.getPassword();
                ACoach.role = member.getRole();
            }
        }
    }

    // Gets the first name of the member
    public String getFirstName()
    {
        return first;
    }

    // Sets the first name of the member
    public void setFirstName(String firstName)
    {
        first = firstName;
    }

    // Gets the last name of the member
    public String getLastName()
    {
        return last;
    }

    // Sets the last name of the member
    public void setLastName(String lastName)
    {
        last = lastName;
    }

    // Gets the phone number of the member
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    // Sets the phone number of the member
    public void setPhoneNumber(String phoneNum)
    {
        phoneNumber = phoneNum;
    }

    // Gets the email of the member
    public String getEmail()
    {
        return email;
    }

    // Sets the email of the member
    public void setEmail(String e)
    {
        email = e;
    }

    // Gets the password of the member
    public String getPassword()
    {
        return password;
    }

    // Sets the password of the member
    public void setPassword(String pass)
    {
        password = pass;
    }

    // Gets the role of the member
    public String getRole()
    {
        return role;
    }

    // Sets the role of the member
    public void setRole(String r)
    {
        role = r;
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
