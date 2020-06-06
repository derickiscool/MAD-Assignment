package sg.edu.np.mad_assignment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class TaskPage extends AppCompatActivity {
    RecyclerView recyclerView;
    TaskAdaptor taskAdaptor;
    ArrayList<Task> taskArrayList;
    Button cfb, upimg;
    ImageView upimgview;
    //StorageReference mStorageRef;
    public Uri imguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskArrayList = new ArrayList<>();
        taskAdaptor = new TaskAdaptor(taskArrayList);
        recyclerView.setAdapter(taskAdaptor);

        createListData();

        //Start of Upload Image section


        cfb = (Button)findViewById(R.id.choosefilebtn); //button for choose file
        upimg = (Button)findViewById(R.id.uploadimgbtn);//button for upload image
        upimgview = (ImageView)findViewById(R.id.imageView);//image view for upload image
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
                Fileuploader();
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
    public void Fileuploader()
    {

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
        if(requestCode==1 && resultCode==RESULT_OK && data!=null)
        {
            imguri=data.getData();
            upimgview.setImageURI(imguri);
        }
    }

    private void createListData(){
        Task t1 = new Task("Read a book");
        taskArrayList.add(t1);
        Task t2 = new Task("Listen to a podcast");
        taskArrayList.add(t2);
        Task t3 = new Task("Watch the news");
        taskArrayList.add(t3);
    }
}
