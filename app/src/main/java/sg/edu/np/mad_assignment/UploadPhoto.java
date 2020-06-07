package sg.edu.np.mad_assignment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
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

public class UploadPhoto extends AppCompatActivity {
    Button cfb, upimg, gotoup;
    ImageView upimgview;
    StorageReference mStorageRef;
    private StorageTask uploadtask;
    public Uri imguri;
    final String TAG = "upload photo page";
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
        StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri)); //give the file a name so it will be easier to retrieve

        uploadtask = Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(UploadPhoto.this,"Image Uploaded Successfully", Toast.LENGTH_LONG).show();
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

    //search from the gallery to select image
    private void Filechooser() //
    {
        Intent intent = new Intent();
        intent.setType("image/'");
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

}