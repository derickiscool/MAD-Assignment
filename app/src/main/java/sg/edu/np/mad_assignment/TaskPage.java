package sg.edu.np.mad_assignment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class TaskPage extends Fragment implements TaskAdaptor.UploadInterface {
    RecyclerView recyclerView;
    TaskAdaptor taskAdaptor;
    private ArrayList<Task> taskArrayList = new ArrayList<Task>();;
    private DatabaseReference reference;
    String myUsername;


    final String TAG = "Task Page";
    public static final int UPLOAD_IMAGE = 1001;
    public static final String POSITION_KEY = "position";
    public String GLOBAL_PREFS = "MyPrefs";
    public String MY_USERNAME= "MyUsername";
    SharedPreferences sharedPreferences;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd", Locale.ENGLISH);


    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_task_page, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        sharedPreferences = this.getActivity().getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE); //Only accessible to calling application.
        myUsername= sharedPreferences.getString(MY_USERNAME, "");
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskAdaptor = new TaskAdaptor(this,taskArrayList);
        recyclerView.setAdapter(taskAdaptor);
        taskArrayList = createListData();



        //createListData();
    }

/* //Unused
    private void createListData(){
        Task t1 = new Task("Read a book");
        taskArrayList.add(t1);
        Task t2 = new Task("Listen to a podcast");
        taskArrayList.add(t2);
        Task t3 = new Task("Watch the news");
        taskArrayList.add(t3);
        Task t4 = new Task("Clean your room");
        taskArrayList.add(t4);
        Task t5 = new Task("Tidy your bed");
        taskArrayList.add(t5);
        Task t6 = new Task("Wash your clothes");
        taskArrayList.add(t6);
        Task t7 = new Task("Fold clothing");
        taskArrayList.add(t7);
        Task t8 = new Task("Organize your belongings");
        taskArrayList.add(t8);
        Task t9 = new Task("Clear the mail");
        taskArrayList.add(t9);
        Task t10 = new Task("Create a meal plan");
        taskArrayList.add(t10);
        Task t11 = new Task("Exercise");
        taskArrayList.add(t11);
        Task t12 = new Task("Meditate");
        taskArrayList.add(t12);
        Task t13 = new Task("Pick up a new skill");
        taskArrayList.add(t13);
        Task t14 = new Task("Learn to cook something new");
        taskArrayList.add(t14);
        Task t15 = new Task("Learn a new language");
        taskArrayList.add(t15);
        Task t16 = new Task("Learn to play a new musical instrument");
        taskArrayList.add(t16);
        Task t17 = new Task("Learn to draw");
        taskArrayList.add(t17);
        Task t18 = new Task("Learn to paint");
        taskArrayList.add(t18);
        Task t19 = new Task("Wash the dishes");
        taskArrayList.add(t19);
        Task t20 = new Task("Compose a song");
        taskArrayList.add(t20);
        Task t21 = new Task("Call a friend");
        taskArrayList.add(t21);
        Task t22 = new Task("Do a reflection on a piece of paper");
        taskArrayList.add(t22);
        Task t23 = new Task("Buy food for the family or yourself");
        taskArrayList.add(t23);
        Task t24 = new Task("Encourage a friend with a Gift");
        taskArrayList.add(t24);
    }*/

    @Override
    public void onUploadSuccessful(Task task, int position) {
        //starts UploadPhoto
        Intent intent = new Intent(getActivity(), UploadPhoto.class);
        intent.putExtra("position", position);
        startActivityForResult(intent,1001);
        Log.v(TAG,"Starting UploadPhoto for result");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==UPLOAD_IMAGE && resultCode==RESULT_OK && data != null)
        {
            int position = data.getIntExtra(POSITION_KEY, -1);
            if(position != -1) {
                String temp = String.valueOf(position);
                final DatabaseReference db = FirebaseDatabase.getInstance().getReference("Member");
                reference = db.child(myUsername).child("tasks").child(temp);
                reference.child("achievement").child("isAchieved").setValue(true);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String taskName = snapshot.child("text").getValue(String.class);
                        String date = dateFormat.format(Calendar.getInstance().getTime());
                        CalendarTask task = new CalendarTask(taskName,date);
                        db.child(myUsername).child("completedTasks").push().setValue(task);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                //Gets task and sets isAchieved to true, achievement is gained
                Log.v(TAG,"Task removed, achievement achieved.");


            }
        }
    }
    private ArrayList<Task> createListData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Member").child(myUsername).child("tasks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String taskName = snapshot.child("text").getValue(String.class);
                    Task task = new Task(taskName);
                    String achievementName = snapshot.child("achievement").child("imageUrl").getValue(String.class);
                    Achievement achievement = new Achievement(achievementName);
                    task.setAchievement(achievement);
                    taskArrayList.add(task);
                    taskAdaptor.notifyDataSetChanged();


                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        taskAdaptor.notifyDataSetChanged();
        return taskArrayList;
    }



}
