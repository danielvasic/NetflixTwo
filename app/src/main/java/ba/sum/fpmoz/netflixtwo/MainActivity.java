package ba.sum.fpmoz.netflixtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fpmoz.netflixtwo.models.User;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;

    String gender;

    String uuid = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db = FirebaseDatabase.getInstance();

        EditText firstnameTxt = findViewById(R.id.first_name);
        EditText lastnameTxt = findViewById(R.id.last_name);
        RadioGroup genderGroup = findViewById(R.id.gender);
        EditText dateOfBirthTxt = findViewById(R.id.birth_date);
        EditText cityTxt = findViewById(R.id.city);
        EditText countryTxt = findViewById(R.id.country);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedBtn = findViewById(checkedId);
                gender = checkedBtn.getText().toString();
            }
        });

        Button submitBtn = findViewById(R.id.submit_button);
        DatabaseReference usersDbRef = this.db.getReference("users");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = new User(
                        firstnameTxt.getText().toString(),
                        lastnameTxt.getText().toString(),
                        gender,
                        dateOfBirthTxt.getText().toString(),
                        cityTxt.getText().toString(),
                        countryTxt.getText().toString()
                );
                usersDbRef.child(uuid).setValue(u);
            }
        });
    }
}