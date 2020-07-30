package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class wellnessFeed extends AppCompatActivity {

    private ImageButton backButton;
    private Button wellnessUpload;
    private RecyclerView mRecyclerView;
    private postAdapter mAdapter;

    private DatabaseReference mDatabaseRef, pDatabaseRef;
    private List<Post> mPosts;
    final String TAG = "Wellness Feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellness_feed);

        backButton = findViewById(R.id.wellnessbackButton);
        wellnessUpload = findViewById(R.id.wellnessUploadbutton);

        mRecyclerView = findViewById(R.id.wellnessRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPosts = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Posts/Wellness");
        pDatabaseRef = FirebaseDatabase.getInstance().getReference("Member");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPosts.clear(); // clear existing data
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Post post = postSnapshot.getValue(Post.class);
                    mPosts.add(post);
                }
                Collections.reverse(mPosts); // get latest post fist

                for (final Post p : mPosts)
                {
                    pDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String tempUsername = p.getUsername();
                            if(dataSnapshot.child(tempUsername).child("name").getValue(String.class) != p.getName())
                            {
                                p.setName(dataSnapshot.child(tempUsername).child("name").getValue(String.class));
                            }
                            if(dataSnapshot.child(tempUsername).child("profilePicture").getValue(String.class) != p.getProfileUrl())
                            {
                                p.setProfileUrl(dataSnapshot.child(tempUsername).child("profilePicture").getValue(String.class));
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG,"Unable to retrieve from database: " + databaseError.getMessage());
                        }
                    });
                }

                mAdapter = new postAdapter(wellnessFeed.this, mPosts);

                mRecyclerView.setAdapter(mAdapter);

                Log.d(TAG, "Wellness Feed!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(wellnessFeed.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Log.d(TAG, "Back to dashboard!");
            }
        });
        wellnessUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent(wellnessFeed.this, wellnessUpload.class);
                startActivity(upload);
                Log.d(TAG, "Proceeding to wellness upload page!");
            }
        });
    }

    protected void onStop(){
        Log.d(TAG,"Stopping application!");
        super.onStop();

    }
    protected void onPause(){
        Log.d(TAG,"Pausing Application!");
        super.onPause();
    }
}