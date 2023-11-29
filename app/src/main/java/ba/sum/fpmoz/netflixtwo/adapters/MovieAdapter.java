package ba.sum.fpmoz.netflixtwo.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ba.sum.fpmoz.netflixtwo.models.*;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import ba.sum.fpmoz.netflixtwo.R;
public class MovieAdapter extends FirebaseRecyclerAdapter<Movie, MovieAdapter.MovieViewHolder> {

    Context ctx;

    public MovieAdapter(@NonNull FirebaseRecyclerOptions<Movie> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MovieViewHolder holder, int position, @NonNull Movie model) {
        holder.movieItemName.setText(model.name);
        holder.movieItemDirector.setText(model.director);
        holder.movieItemYear.setText(model.year.toString());
        holder.movieItemGenere.setText(model.genere);
        Picasso.get().load(model.image).into(holder.movieItemImageView);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.ctx = parent.getContext();
        View view = LayoutInflater.from(this.ctx).inflate(R.layout.movie_item_list_view, parent, false);
        return new MovieViewHolder(view);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieItemImageView;
        TextView movieItemName, movieItemDirector, movieItemYear, movieItemGenere;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            this.movieItemImageView = itemView.findViewById(R.id.movieItemImageView);
            this.movieItemName = itemView.findViewById(R.id.movieItemName);
            this.movieItemDirector = itemView.findViewById(R.id.movieItemDirector);
            this.movieItemGenere = itemView.findViewById(R.id.movieItemGenere);
            this.movieItemYear = itemView.findViewById(R.id.movieItemYear);
        }
    }
}
