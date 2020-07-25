package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

public class foodFeed extends AppCompatActivity {

    private ImageButton backButton;
    private Button foodUpload;
    private RecyclerView mRecyclerView;
    private postAdapter mAdapter;

    private DatabaseReference mDatabaseRef;
    private List<Post> mPosts;
    final String TAG = "Food Feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed);

        backButton = findViewById(R.id.foodbackButton);
        foodUpload = findViewById(R.id.foodUploadbutton);

        mRecyclerView = findViewById(R.id.foodRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPosts = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Posts/Food");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPosts.clear(); // clear existing data
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Post post = postSnapshot.getValue(Post.class);
                    mPosts.add(post);
                }

                mAdapter = new postAdapter(foodFeed.this, mPosts);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(foodFeed.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
            }
        });
        foodUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent(foodFeed.this, foodUpload.class);
                startActivity(upload);
            }
        });
    }
}