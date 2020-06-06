package sg.edu.np.mad_assignment;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskPage extends AppCompatActivity {
    RecyclerView recyclerView;
    TaskAdaptor taskAdaptor;
    ArrayList<Task> taskArrayList;

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
    }


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
    }
}
