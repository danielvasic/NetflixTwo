package ba.sum.fpmoz.netflixtwo.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fpmoz.netflixtwo.R;
import ba.sum.fpmoz.netflixtwo.adapters.MovieAdapter;
import ba.sum.fpmoz.netflixtwo.fragments.AddMovieDialogFragment;
import ba.sum.fpmoz.netflixtwo.models.Movie;
import ba.sum.fpmoz.netflixtwo.R;

public class MovieFragment extends Fragment implements MovieAdapter.OnRatingChangedListener {

    FirebaseDatabase movieDatabase;
    MovieAdapter movieAdapter;
    RecyclerView movieRecyclerView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_movie, container, false);
        this.movieDatabase = FirebaseDatabase.getInstance();
        this.movieRecyclerView = view.findViewById(R.id.movieListView);
        this.movieRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );
        FirebaseRecyclerOptions<Movie> options = new FirebaseRecyclerOptions.Builder<Movie>().setQuery(
                this.movieDatabase.getReference("movies"),
                Movie.class
        ).build();

        this.movieAdapter = new MovieAdapter(options, this);
        this.movieRecyclerView.setAdapter(this.movieAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.movieAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.movieAdapter.stopListening();
    }

    @Override
    public void onRatingChanged(String movieId, float rating) {
        // Update the rating in Firebase
        DatabaseReference movieDbRef = FirebaseDatabase.getInstance().getReference("movies").child(movieId);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        movieDbRef.child("ratings").child(userId).setValue(rating);
    }
}
