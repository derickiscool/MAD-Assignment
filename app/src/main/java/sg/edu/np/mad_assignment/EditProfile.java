package sg.edu.np.mad_assignment;

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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfile extends AppCompatActivity {

    private EditText newName, newBio;
    private Button updateButton;
    private ImageButton profileButton;
    String myUsername, name, bio;
    final String TAG = "Profile Edit Page";
    private static final int PICK_IMAGE = 1;


    private ImageView profilePicture;
    private TextView changePFP;
    private Uri imgURI;
    private StorageTask uploadTask;

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
        profileButton = findViewById(R.id.backProfileButton);
        profilePicture = findViewById(R.id.pfpimageView);
        changePFP = findViewById(R.id.PFPTextView);

        mStorageRef = FirebaseStorage.getInstance().getReference("Member");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Member").child(String.valueOf(myUsername));

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // listening to change in the database
                String currentURL = dataSnapshot.child("profilePicture").getValue(String.class);
                if(currentURL.equals("")){ // no existing pfp in the database
                    profilePicture.setImageResource(R.drawable.profile_icon);
                }
                else // existing pfp in the database
                {
                    Picasso.get().load(currentURL).fit().centerCrop().into(profilePicture);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Reading from database failed: " + databaseError.getCode());
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = newName.getText().toString();
                bio = newBio.getText().toString();
                updateProfile(name, bio);
                if (uploadTask != null && uploadTask.isInProgress()) // upload task in progress, do not want multiple upload of the same image
                {
                    Log.d(TAG, "Upload in progress!");
                    Toast.makeText(EditProfile.this, "Upload in progress!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    UploadImage();
                }
                Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Back to Dashboard!");
                finish();
            }
        });
        changePFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Selecting file!");
                ChooseFile();
            }
        });


        Log.d(TAG,"Update Page" + String.valueOf(myUsername));

    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "Paused Application!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "Stopped Application!");

    }

    private void updateProfile(final String updateName, final String updateBio) {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // listening to change in the database
                String name = updateName;
                String bio = updateBio;
                if(updateName.isEmpty()){ // edit text for name is empty
                    name = dataSnapshot.child("name").getValue(String.class); // take existing name from firebase
                }
                if(updateBio.isEmpty()){ // edit text for bio is empty
                    bio = dataSnapshot.child("bio").getValue(String.class); // take existing bio from firebase
                }
                mDatabaseRef.child("name").setValue(name);
                mDatabaseRef.child("bio").setValue(bio);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, "Reading from database failed: " + databaseError.getCode());
            }
        });
    }

    private String getExtension(Uri uri) // identify file extension
    {
        ContentResolver cr = getContentResolver(); // provide access to your content providers
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void ChooseFile()
    {
        Intent intent = new Intent();
        intent.setType("image/*"); // see only image in file chooser
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData()!= null) // image is selected
        {
            imgURI = data.getData();
            Picasso.get().load(imgURI).fit().centerCrop().into(profilePicture); // place image into pfp image view
            Log.d(TAG,"Image selected!");
        }
    }

    private void UploadImage()
    {
        if(imgURI != null){ //if there is an image selected
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(imgURI)); // unique identifier for image

            uploadTask = fileReference.putFile(imgURI) // place image in firebase storage
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() { // upload successful
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() { // get image url
                                @Override
                                public void onSuccess(Uri uri) {

                                    final String downloadUrl = uri.toString();
                                    mDatabaseRef.child("profilePicture").setValue(downloadUrl); // place image url into firebase database
                                    Log.d(TAG, "Uploading image to database!");
                                }
                            });
                        }
                    })
                    . addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { // upload not successful
                            Log.d(TAG, "Uploading of image failed");
                            Toast.makeText(EditProfile.this, "Upload not successful!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
