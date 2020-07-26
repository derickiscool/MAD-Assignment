package sg.edu.np.mad_assignment;

import android.content.ContentResolver;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class wellnessUpload extends AppCompatActivity {

    final String TAG = "Wellness/ Upload post";

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button ButtonChooseImage, ButtonUpload;
    private ImageButton backButton;
    private EditText EditTextCaption;
    private android.widget.ImageView ImageView;
    private ProgressBar progressBar;
    String myUsername;

    private Uri imageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    public String GLOBAL_PREFS = "MyPrefs";
    public String MY_USERNAME= "MyUsername";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellness_upload);

        sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE); //Only accessible to calling application.
        myUsername= sharedPreferences.getString(MY_USERNAME, "");

        ButtonChooseImage = findViewById(R.id.wellnessChooseFile);
        ButtonUpload = findViewById(R.id.wellnessUploadPost);
        backButton = findViewById(R.id.backButtonWellness);
        EditTextCaption = findViewById(R.id.wellnessCaption);
        ImageView = findViewById(R.id.wellnessImageView);
        progressBar = findViewById(R.id.wellnessProgressBar);

        mStorageRef = FirebaseStorage.getInstance().getReference("Posts");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Posts/Wellness");

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
                    Toast.makeText(wellnessUpload.this, "Upload in progress!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Upload in progress");
                }
                else
                {
                    uploadFile();
                    finish();
                    Log.d(TAG,"Back to wellness feed!");
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Log.d(TAG,"Back to wellness feed!");
            }
        });
    }

    private void openFileChooser() // select file
    {
        Intent intent = new Intent();
        intent.setType("image/*"); // select file from image file location
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) // file is selected successfully
        {
            imageUri = data.getData();
            ImageView.setImageURI(imageUri);
            Log.d(TAG,"Image selected!");
        }
    }

    private String getFileExtension(Uri uri) // identify extension of file selected
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
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { // image is placed into firebase storage is successfully
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String downloadUrl = uri.toString();
                                    Post post = new Post(EditTextCaption.getText().toString().trim(),
                                            downloadUrl, String.valueOf(myUsername));

                                    String postId =  mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(postId).setValue(post);

                                    Log.d(TAG,"File uploaded!");

                                }
                            });
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0); // delays the reset of the progress bar
                                }
                            }, 500);

                            Toast.makeText(wellnessUpload.this, "Upload successful!", Toast.LENGTH_LONG).show();
                        }
                    })
                    . addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { // image is not placed into firebase storage is successfully
                            Log.d(TAG,"File not uploaded!");
                            Toast.makeText(wellnessUpload.this, "Upload not successful!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {  // upload progress
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()); // upload progress
                            progressBar.setProgress((int) progress);
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "No file selected!", Toast.LENGTH_SHORT).show();
        }
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