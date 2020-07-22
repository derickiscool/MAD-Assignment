package sg.edu.np.mad_assignment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfile extends AppCompatActivity {

    private EditText newName, newBio;
    private Button updateButton;
    private ImageButton profileButton;
    String myUsername, name, bio;
    final String TAG = "Profile Edit Page";

    private static final int PICK_IMAGE_REQUEST = 1; // identification for image request

    private ImageView profilePicture;
    private TextView changePFP;
    private Uri imgURI;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

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
        profilePicture = findViewById(R.id.profileImage);
        changePFP = findViewById(R.id.PFPTextView);

        mStorageRef = FirebaseStorage.getInstance().getReference("Member").child(String.valueOf(myUsername)); // save it in a folder under Member with username
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Member").child(String.valueOf(myUsername));

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("profile picture").getValue().equals("")){
                    profilePicture.setImageResource(R.drawable.profile_icon);
                }
                else
                {
                    String currentURI = dataSnapshot.child("profile picture").getValue(String.class);
                    Uri tempURI = Uri.parse(currentURI);
                    profilePicture.setImageURI(tempURI);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, "Reading from database failed: " + databaseError.getCode());
            }
        });


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
                updateProfile(String.valueOf(myUsername), name, bio);
                UploadImage();
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
        changePFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseFile();
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

    private String getExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver(); // provide access to your content providers
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void UploadImage()
    {
        if (imgURI != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(imgURI));

            fileReference.putFile(imgURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @SuppressLint("ShowToast")
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(EditProfile.this, "Upload Successful!", Toast.LENGTH_SHORT);
                            mDatabaseRef.child("profile picture").setValue(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString()); // store url link in database
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @SuppressLint("ShowToast")
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, "Upload Unsuccessful!", Toast.LENGTH_SHORT);
                        }
                    });
        }
    }


    private void ChooseFile()
    {
        Intent intent = new Intent();
        intent.setType("image/*"); // see only images in file chooser
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!= null)
        {
            imgURI = data.getData();

            profilePicture.setImageURI(imgURI);
        }
    }

}
