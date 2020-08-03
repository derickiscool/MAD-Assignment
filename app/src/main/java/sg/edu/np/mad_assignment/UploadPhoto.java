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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

/*
* This page contains the code for the upload feature for the task in the tasks page
* upon pressing the upload button on a single task, it will load this page along with the code
* once they press choose photo, it will direct them to either their gallery or drive
* upon choosing and uploading the image, it will successfully upload into the database
* and the task will be cleared while the achievement will show "obtained!"
*/

public class UploadPhoto extends AppCompatActivity {
    Button cfb, upimg, gotoup;
    ImageButton backButton;
    ImageView upimgview;
    StorageReference mStorageRef;
    private StorageTask uploadtask;
    public Uri imguri;
    final String TAG = "upload photo page";
    public String GLOBAL_PREFS = "MyPrefs";
    public String imgUrl= "imgUrl";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        //Start of Upload Image section
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        gotoup = (Button)findViewById(R.id.gotouploadbtn); //go to the upload page
        cfb = (Button)findViewById(R.id.choosefilebtn); //button for choose file
        upimg = (Button)findViewById(R.id.uploadimgbtn);//button for upload image
        upimgview = (ImageView)findViewById(R.id.uploadimgview);//image view for upload image
        backButton = (ImageButton) findViewById(R.id.uploadBackButton); //button back to the tasks
        cfb.setOnClickListener(new View.OnClickListener() { //set button for choose file popup
            @Override
            public void onClick(View v) {
                Filechooser(); //calling method for choosing the file
            }
        });
        upimg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //validation for if button is pressed more than once
                if(uploadtask != null && uploadtask.isInProgress()){
                    Toast.makeText(UploadPhoto.this, "Upload is in progress",Toast.LENGTH_LONG).show();
                }
                else {
                    Fileuploader();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //search from the gallery to select image
    private void Filechooser() //
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imguri=data.getData();
            upimgview.setImageURI(imguri);
            Log.v(TAG, "Successfully Run");
        }
    }
    //private string to get the extension for the storage to upload
    private String getExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    //method to upload file
    public void Fileuploader()
    {
        if (imguri != null)
        {
            StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri)); //give the file a name so it will be easier to retrieve

            uploadtask = Ref.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText(UploadPhoto.this,"Image Uploaded Successfully", Toast.LENGTH_LONG).show();

                            int position = getIntent().getIntExtra("position",-1);
                            Intent intent = new Intent();
                            intent.putExtra("position", position);
                            setResult(RESULT_OK, intent);
                            sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(imgUrl, taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            editor.apply();


                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(UploadPhoto.this,"Image failed to upload, please try again.", Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else
        {
            Toast.makeText(UploadPhoto.this, "No file selected!", Toast.LENGTH_LONG).show();
        }

    }

}