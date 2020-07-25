package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {

    private TextView username, name, bio;
    private Button editProfile;
    private ImageButton dashboard;
    private ImageView profilePic;
    String myUsername;
    final String TAG = "Profile Page";

    public String GLOBAL_PREFS = "MyPrefs";
    public String MY_USERNAME= "MyUsername";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE); //Only accessible to calling application.
        myUsername= sharedPreferences.getString(MY_USERNAME, "");


        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        bio = findViewById(R.id.bio);
        profilePic = findViewById(R.id.profileImage);
        editProfile = findViewById(R.id.editProfile);
        dashboard = findViewById(R.id.backButton);



        // Retrieve from data base and set OnCreate
        username.setText(String.valueOf("@" + myUsername));
        setProfile(String.valueOf(myUsername));


        Log.v(TAG, "Finished Profile Pre-Initialisation!");

    }

    @Override
    protected void onStart(){
        super.onStart();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfilePage();
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //backDashboard();
                finish();
            }
        });

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Profile Page!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Profile Page!");
        finish();
    }

    /*private void backDashboard(){
        Intent dashboardPage = new Intent(ProfilePage.this, Dashboard.class);
        startActivity(dashboardPage);
    }*/


    private void EditProfilePage(){
        Intent editPage = new Intent(ProfilePage.this, EditProfile.class);
        startActivity(editPage);
    }

    private void setProfile(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Member");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nameFromDB = dataSnapshot.child(id).child("name").getValue(String.class);
                String bioFromDB = dataSnapshot.child(id).child("bio").getValue(String.class);
                String currentURL = dataSnapshot.child(id).child("profilePicture").getValue(String.class);
                if(currentURL.equals(""))
                {
                    profilePic.setImageResource(R.drawable.profile_icon);
                }
                else
                {
                    Glide.with(ProfilePage.this).load(currentURL).into(profilePic);
                }
                name.setText(String.valueOf(nameFromDB));
                bio.setText(String.valueOf(bioFromDB));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, "Reading from database failed: " + databaseError.getCode());
            }
        });
    }
}
