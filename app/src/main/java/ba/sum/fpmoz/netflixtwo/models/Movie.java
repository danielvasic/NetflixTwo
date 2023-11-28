package ba.sum.fpmoz.netflixtwo.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Movie {
    public String name;
    public String director;
    public String release_date;

    public String image;

    public Movie() {
    }

    public Movie(String name, String director, String release_date, String image) {
        this.name = name;
        this.director = director;
        this.release_date = release_date;
        this.image = image;
    }
}
