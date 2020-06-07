package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    private EditText newName, newBio;
    private Button updateButton;
    private ImageButton profileButton;
    String myUsername, name, bio;
    final String TAG = "Profile Edit Page";

    public String GLOBAL_PREFS = "MyPrefs";
    public String MY_USERNAME= "MyUsername";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE); //Only accessible to calling application.
        myUsername= sharedPreferences.getString(MY_USERNAME, "");

        newName = findViewById(R.id.updateName);
        newBio = findViewById(R.id.updateBio);
        updateButton = findViewById(R.id.updateButton);
        profileButton = findViewById(R.id.profileButton);

        Log.v(TAG,"Update Page" + String.valueOf(myUsername));

    }

    @Override
    protected void onStart(){
        super.onStart();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = newName.getText().toString();
                bio = newBio.getText().toString();
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
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Member");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = updateName;
                String bio = updateBio;
                if(updateName.isEmpty()){
                    name = dataSnapshot.child(id).child("name").getValue(String.class);
                }
                if(updateBio.isEmpty()){
                    bio = dataSnapshot.child(id).child("bio").getValue(String.class);
                }
                reference.child(id).child("name").setValue(name);
                reference.child(id).child("bio").setValue(bio);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, "Reading from database failed: " + databaseError.getCode());
            }
        });
    }

}
