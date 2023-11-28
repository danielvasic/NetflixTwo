package ba.sum.fpmoz.netflixtwo.models;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String firstname;
    public String lastname;
    public String gender;
    public String dateOfBirth;
    public String city;
    public String country;
    public User() {}
    public User(String firstname, String lastname, String gender, String dateOfBirth, String city, String country) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.country = country;
    }
}
