package sg.edu.np.mad_assignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;

public class wellnessFeed extends AppCompatActivity implements postAdapter.OnItemClickListener {

    private ImageButton backButton;
    private Button wellnessUpload;
    private RecyclerView mRecyclerView;
    private postAdapter mAdapter;

    private FirebaseStorage mStorage; // get references of image in our firebase storage
    private DatabaseReference mDatabaseRef, pDatabaseRef;
    private ArrayList<Post> mPosts = new ArrayList<>();
    final String TAG = "Wellness Feed";

    String myUsername;
    public String GLOBAL_PREFS = "MyPrefs";
    public String MY_USERNAME = "MyUsername";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellness_feed);

        sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE); //Only accessible to calling application.
        myUsername = sharedPreferences.getString(MY_USERNAME, "");

        backButton = findViewById(R.id.wellnessbackButton);
        wellnessUpload = findViewById(R.id.wellnessUploadbutton);

        mRecyclerView = findViewById(R.id.wellnessRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new postAdapter(wellnessFeed.this, mPosts);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(wellnessFeed.this);

        mStorage = FirebaseStorage.getInstance();
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

                updateList(mPosts); // see if users update name or pfp



                Log.d(TAG, "Wellness Feed!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(wellnessFeed.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onItemClickListener(int position) {
        final Post selectedItem = mPosts.get(position);
        final String selectedKey = selectedItem.getKey();

        if (myUsername.equals(selectedItem.getUsername())) { // post belongs to user
            AlertDialog.Builder alert = new AlertDialog.Builder(wellnessFeed.this);
            alert.setTitle("Delete Post");
            alert.setMessage("Are you sure you want to delete this post?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
                    imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() { // able to delete picture from firebase storage
                        @Override
                        public void onSuccess(Void aVoid) {
                            mDatabaseRef.child(selectedKey).removeValue();
                            Toast.makeText(wellnessFeed.this, "Item deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.d(TAG, "Item Deleted!");
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Log.d(TAG, "Request Declined!");
                }
            });
            alert.show();
        }
        else{
            Toast.makeText(wellnessFeed.this, "You are not allowed to delete this post!", Toast.LENGTH_LONG).show();
        }
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

    private ArrayList<Post> updateList(final ArrayList<Post> pList)
    {
        for (final Post p : pList)
        {
            pDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String tempUsername = p.getUsername();
                    if(dataSnapshot.child(tempUsername).child("name").getValue(String.class) != p.getName())
                    {
                        p.setName(dataSnapshot.child(tempUsername).child("name").getValue(String.class));
                        mAdapter.notifyDataSetChanged();
                    }
                    if(dataSnapshot.child(tempUsername).child("profilePicture").getValue(String.class) != p.getProfileUrl())
                    {
                        p.setProfileUrl(dataSnapshot.child(tempUsername).child("profilePicture").getValue(String.class));
                        mAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG,"Unable to retrieve from database: " + databaseError.getMessage());
                }
            });
        }

        return pList;
    }
}