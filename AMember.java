public class AMember {

    String firstName;
    String lastName;
    String email;
    String password;
    String role;

    // Default constructor method
    public AMember()
    {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.role = "Member";
    }

    // Constructor method for a member
    public AMember(String first, String last, String email, String password, String role)
    {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = password;
        this.role = role;
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


}
