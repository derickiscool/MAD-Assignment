package sg.edu.np.mad_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "StartUpPage";
    ConstraintLayout layout;
    public static ArrayList<Task> taskArrayList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (ConstraintLayout) findViewById(R.id.main_layout);
        Log.d(TAG,"Creating Page Layout");
        taskArrayList = createListData();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Moving to Login Page");
                Intent start = new Intent(MainActivity.this,LoginPage.class ); //Redirecting to Login Page upon click of layout.
                startActivity(start);
            }
        });

    }
    protected void OnStop(){
        Log.d(TAG,"App Stopping!");
        super.onStop();

    }
    protected void OnPause(){
        Log.d(TAG,"App Pausing!");
        super.onPause();
    }
    protected void OnDestroy(){
        Log.d(TAG,"Destroying App!");
        super.onDestroy();
    }
    private ArrayList<Task> createListData() {
        taskArrayList = new ArrayList<Task>();
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceTasks = mDatabase.getReference().child("Tasks");
        taskArrayList = addTasksToList(mReferenceTasks);
        return taskArrayList;

    }
    public ArrayList<Task> addTasksToList(DatabaseReference mReferenceTasks)
    {
        mReferenceTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String taskName = snapshot.child("TaskName").getValue(String.class);
                    Task task = new Task(taskName);
                    String achievementName = snapshot.child("Achievement").getValue(String.class);
                    Achievement achievement = new Achievement(achievementName);
                    task.setAchievement(achievement);
                    taskArrayList.add(task);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return taskArrayList;

    }

}
