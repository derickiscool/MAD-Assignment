package sg.edu.np.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity {

    private TextView posts, followers, following, username, name, bio;
    private Button editProfile;
    final String TAG = "Profile Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        posts = findViewById(R.id.posts);
        followers = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        bio = findViewById(R.id.bio);
        editProfile = findViewById(R.id.editProfile);

        // Retrieve from data base and set OnCreate
        //posts.setText(String.valueOf());
        //followers.setText(String.valueOf());
        //following.setText(String.valueOf());
        //username.setText(String.valueOf());
        //name.setText(String.valueOf());
        //bio.setText(String.valueOf());

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


    private void EditProfilePage(){
        //Intent advancedPage = new Intent(ProfilePage.this, EditProfile.class);
        //startActivity(advancedPage);
    }


}
