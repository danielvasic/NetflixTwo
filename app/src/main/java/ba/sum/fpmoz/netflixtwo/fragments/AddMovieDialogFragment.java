package ba.sum.fpmoz.netflixtwo.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

import ba.sum.fpmoz.netflixtwo.R;
import ba.sum.fpmoz.netflixtwo.models.Movie;

public class AddMovieDialogFragment extends Fragment {

    FirebaseStorage storage;
    StorageReference storageReference;

    Uri filePath;

    String movieImageUrl;

    private static final int IMAGE_PICK_REQUEST = 22;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.storage = FirebaseStorage.getInstance();
        this.storageReference = this.storage.getReference();
        View view = inflater.inflate(R.layout.movie_item_dalog_view, container, false);
        Button selectImageButton = view.findViewById(R.id.buttonSelectImage);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(i, "Odaberite sliku filma"), 22
                );
            }
        });

        Button insertMovieButton = view.findViewById(R.id.buttonInsertMovie);
        insertMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from EditTexts
                String name = ((EditText) view.findViewById(R.id.editTextMovieName)).getText().toString();
                String genre = ((EditText) view.findViewById(R.id.editTextGenre)).getText().toString();
                String yearString = ((EditText) view.findViewById(R.id.editTextYear)).getText().toString();
                String director = ((EditText) view.findViewById(R.id.editTextDirector)).getText().toString();

                // Validate Inputs
                if (!validateInputs(name, genre, yearString, director) || movieImageUrl == null) {
                    Toast.makeText(getContext(), "Please upload an image and fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Long year = Long.parseLong(yearString);

                // Add movie to database
                addMovieToDatabase(name, genre, year, director, movieImageUrl);
                resetForm(view);
            }
        });


        return view;
    }

    private void resetForm(View view) {
        ((EditText) view.findViewById(R.id.editTextMovieName)).setText("");
        ((EditText) view.findViewById(R.id.editTextGenre)).setText("");
        ((EditText) view.findViewById(R.id.editTextYear)).setText("");
        ((EditText) view.findViewById(R.id.editTextDirector)).setText("");
        movieImageUrl = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            uploadImage(); // Call uploadImage method here
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Učitavam sliku");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Slika je učitana na server", Toast.LENGTH_LONG).show();
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    movieImageUrl = task.getResult().toString();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Greška pri učitavanju slike: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void addMovieToDatabase(String name, String genre, Long year, String director, String imageUrl) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference moviesRef = database.getReference("movies");

        String movieId = moviesRef.push().getKey();
        Movie movie = new Movie(name, genre, year, director, imageUrl, new HashMap<>());
        moviesRef.child(movieId).setValue(movie)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Movie added successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to add movie: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private boolean validateInputs(String name, String genre, String year, String director) {
        if (name.isEmpty() || genre.isEmpty() || year.isEmpty() || director.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
