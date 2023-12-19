package ba.sum.fpmoz.netflixtwo.models;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Movie {
    public String name;
    public String genere;
    public Long year;
    public String director;
    public String image;

    public Map<String, Float> ratings;

    public Movie() {}

    public Movie(String name, String genere, Long year, String director, String image, HashMap<String, Float> ratings){
        this.name = name;
        this.genere = genere;
        this.year = year;
        this.director = director;
        this.image = image;
        this.ratings = ratings;
    }

    public float getAverageRating() {
        if (ratings == null || ratings.isEmpty()) {
            return 0;
        }
        float sum = 0;
        for (Float rating : ratings.values()) {
            sum += rating;
        }
        return sum / ratings.size();
    }

}
