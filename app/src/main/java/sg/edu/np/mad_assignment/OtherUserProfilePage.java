package sg.edu.np.mad_assignment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OtherUserProfilePage extends AppCompatActivity {

    /*
     * This page contains code for viewing other peoples' profile when user clicked
     * on the pfp from the feeds.
     */

    private TextView username, name, bio;
    private ImageButton backButton;
    private ImageView profilePic;
    String mUsername;
    final String TAG = "Other Profile Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile_page);

        mUsername = getIntent().getStringExtra("Username");


        username = findViewById(R.id.otherUsername);
        name = findViewById(R.id.otherName);
        bio = findViewById(R.id.otherBio);
        profilePic = findViewById(R.id.otherPFPimage);
        backButton = findViewById(R.id.otherBackButton);

        // Retrieve from database and set OnCreate
        username.setText(String.valueOf("@" + mUsername));
        setProfile(String.valueOf(mUsername));


        Log.v(TAG, "Finished Profile Pre-Initialisation!");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "Paused Profile Page!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "Stopped Profile Page!");
        finish();
    }

    private void setProfile(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Member");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // listen for change in database
                String nameFromDB = dataSnapshot.child(id).child("name").getValue(String.class);
                String bioFromDB = dataSnapshot.child(id).child("bio").getValue(String.class);
                String currentURL = dataSnapshot.child(id).child("profilePicture").getValue(String.class);
                if(currentURL.equals(""))
                {
                    profilePic.setImageResource(R.drawable.profile_icon);
                }
                else
                {
                    Picasso.get().load(currentURL).fit().centerCrop().into(profilePic);
                }
                name.setText(String.valueOf(nameFromDB));
                bio.setText(String.valueOf(bioFromDB));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Reading from database failed: " + databaseError.getCode());
            }
        });
    }
}