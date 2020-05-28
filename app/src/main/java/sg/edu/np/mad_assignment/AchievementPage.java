package sg.edu.np.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AchievementPage extends AppCompatActivity {
    RecyclerView recyclerView;
    AchievementAdaptor achievementAdaptor;
    ArrayList<Achievement> achievementArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_page);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewAchievement);
        GridLayoutManager glm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(glm);
        achievementArrayList = new ArrayList<>();
        achievementAdaptor = new AchievementAdaptor(achievementArrayList);
        recyclerView.setAdapter(achievementAdaptor);

        createListData();
    }

    private void createListData(){
        //Add achievement images here
    }
}
