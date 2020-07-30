package sg.edu.np.mad_assignment;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class foodUpload extends AppCompatActivity {

    final String TAG = "Food/ Upload post";

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button ButtonChooseImage, ButtonUpload;
    private ImageButton backButton;
    private EditText EditTextCaption;
    private ImageView ImageView;
    private ProgressBar progressBar;
    String myUsername;

    private Uri imageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef, tDatabaseRef;

    private StorageTask mUploadTask;

    public String GLOBAL_PREFS = "MyPrefs";
    public String MY_USERNAME= "MyUsername";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_upload);

        sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE); //Only accessible to calling application.
        myUsername= sharedPreferences.getString(MY_USERNAME, "");

        ButtonChooseImage = findViewById(R.id.foodChooseFile);
        ButtonUpload = findViewById(R.id.foodUploadPost);
        backButton = findViewById(R.id.foodbackButton2);
        EditTextCaption = findViewById(R.id.foodCaption);
        ImageView = findViewById(R.id.foodImageView);
        progressBar = findViewById(R.id.foodProgressBar);

        mStorageRef = FirebaseStorage.getInstance().getReference("Posts");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Posts/Food");
        tDatabaseRef = FirebaseDatabase.getInstance().getReference("Member");

        ButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Selecting file...");
                openFileChooser();
            }
        });

        ButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) // upload task in progress, do not want multiple upload of the same image
                {
                    Toast.makeText(foodUpload.this, "Upload in progress!", Toast.LENGTH_SHORT).show();
                }
                else if (imageUri == null)
                {
                    Toast.makeText(foodUpload.this, "No file selected!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(foodUpload.this);
                    alert.setTitle("Upload Post");
                    alert.setMessage("Are you sure you want to post? You will not be able to delete your post!");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uploadFile();
                            finish();
                            Log.d(TAG,"Upload Completed!");
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Log.d(TAG, "Upload Declined!");
                        }
                    });
                    alert.show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Back to food feed!");
                finish();
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

    private void openFileChooser() // select image function
    {
        Intent intent = new Intent();
        intent.setType("image/*"); // access images file location
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) // image selected from images
        {
            imageUri = data.getData();
            ImageView.setImageURI(imageUri);
            Log.d(TAG,"Image selected!");
        }
    }

    private String getFileExtension(Uri uri) // identify file extension
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile()
    {
        if(imageUri != null){
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            mUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {  // listens if image is successfully placed into firebase storage
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) { // get url for image
                                    final String downloadUrl = uri.toString();
                                    final String currentName = getCurrentName();
                                    final String currentPFP = getCurrentPFP();
                                    Post post = new Post(EditTextCaption.getText().toString().trim(),
                                            downloadUrl, String.valueOf(myUsername), currentName, currentPFP);

                                    String postId =  mDatabaseRef.push().getKey(); // unique key for post
                                    mDatabaseRef.child(postId).setValue(post);

                                }
                            });
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0); // delays the reset of the progress bar
                                }
                            }, 500);
                            Log.d(TAG,"Upload successful!");
                            Toast.makeText(foodUpload.this, "Upload successful!", Toast.LENGTH_LONG).show();
                        }
                    })
                    . addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { // image not successfully placed into firebase storage
                            Log.d(TAG,"Upload not successful!");
                            Toast.makeText(foodUpload.this, "Upload not successful!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {  // upload progress
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()); // progress of image being uploaded
                            progressBar.setProgress((int) progress);
                        }
                    });
        }
    }

    private String getCurrentName()
    {
        final String[] tempName = {""};
        tDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tempName[0] = dataSnapshot.child(myUsername).child("name").getValue(String.class);
                Log.d(TAG,"Name: " + tempName[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,"Unable to retrieve from database: " + databaseError.getMessage());
            }
        });
        return tempName[0];
    }

    private String getCurrentPFP()
    {
        final String[] tempPFP = {""};
        tDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tempPFP[0] = dataSnapshot.child(myUsername).child("profilePicture").getValue(String.class);
                Log.d(TAG,"PFP: " + tempPFP[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,"Unable to retrieve from database: " + databaseError.getMessage());
            }
        });
        return tempPFP[0];
    }

}