package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {

    private EditText newName, newBio;
    private Button updateButton;
    private ImageButton profileButton;
    String myUsername, name, bio;
    final String TAG = "Profile Edit Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        myUsername = getIntent().getExtras().getString("editUser");

        newName = findViewById(R.id.updateName);
        newBio = findViewById(R.id.updateBio);
        updateButton = findViewById(R.id.updateButton);
        profileButton = findViewById(R.id.profileButton);

        name = newName.getText().toString().trim();
        bio = newBio.getText().toString().trim();

        Log.v(TAG,"Update Page" + String.valueOf(myUsername));

    }

    @Override
    protected void onStart(){
        super.onStart();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.isEmpty())
                {
                    name = getIntent().getExtras().getString("editName");
                }
                if(bio.isEmpty())
                {
                    bio = getIntent().getExtras().getString("editBio");
                }
                updateProfile(myUsername, name, bio);
                Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                Intent profilePage = new Intent(EditProfile.this, ProfilePage.class);
                startActivity(profilePage);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePage = new Intent(EditProfile.this, ProfilePage.class);
                startActivity(profilePage);
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Edit Profile Page!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Edit Profile Page!");
        finish();
    }

    private void updateProfile(final String id, final String updateName, final String updateBio) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Member");
        Member m = new Member(updateName, updateBio);
        databaseReference.child(id).setValue(m);
    }

}
