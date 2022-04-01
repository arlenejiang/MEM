public class Staff {
    String Name;
    String Email;
    String Password;
    
    public Staff(String name, String email, String password){
        this.Name = name;
        this.Email = email;
        this.Password = password;
    }

    public String getName(){
        return Name;
    }

    public String getEmail(){
        return Email;
    }

    public String getPassword(){
        return Password;
    }
}
