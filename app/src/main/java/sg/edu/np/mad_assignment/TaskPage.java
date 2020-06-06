package sg.edu.np.mad_assignment;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;


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
    }
}
