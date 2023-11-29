package ba.sum.fpmoz.netflixtwo.models;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Movie {
    public String name;
    public String genere;
    public Long year;
    public String director;
    public String image;

    public Movie() {}

    public Movie(String name, String genere, Long year, String director, String image) {
        this.name = name;
        this.genere = genere;
        this.year = year;
        this.director = director;
        this.image = image;
    }
}
