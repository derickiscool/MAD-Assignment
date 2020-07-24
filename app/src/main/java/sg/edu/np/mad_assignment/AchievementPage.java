package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AchievementPage extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton backButton;
    AchievementAdaptor achievementAdaptor;
    ArrayList<Achievement> achievementArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_page);

        backButton = findViewById(R.id.achievementbackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AchievementPage.this, Dashboard.class);
                startActivity(back);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewAchievement);
        GridLayoutManager glm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(glm);
        //achievementArrayList = new ArrayList<>();
        ArrayList<Task> taskList = Dashboard.taskArrayList;
        achievementAdaptor = new AchievementAdaptor(taskList);
        recyclerView.setAdapter(achievementAdaptor);
        //createListData();



    }

    private void createListData(){
        //Add achievement images here
        Achievement ac1 = new Achievement(R.drawable.badge_readabook, "");
        achievementArrayList.add(ac1);
        Achievement ac2 = new Achievement(R.drawable.badge_podcast,"");
        achievementArrayList.add(ac2);
        Achievement ac3 = new Achievement(R.drawable.badge_news,"");
        achievementArrayList.add(ac3);
        Achievement ac4 = new Achievement(R.drawable.badge_cleanroom,"");
        achievementArrayList.add(ac4);
        Achievement ac5 = new Achievement(R.drawable.badge_cleanbed,"");
        achievementArrayList.add(ac5);
        Achievement ac6 = new Achievement(R.drawable.badge_washclothes,"");
        achievementArrayList.add(ac6);
        Achievement ac7 = new Achievement(R.drawable.badge_foldclothes,"");
        achievementArrayList.add(ac7);
        Achievement ac8 = new Achievement(R.drawable.badge_organizethings,"");
        achievementArrayList.add(ac8);
        Achievement ac9 = new Achievement(R.drawable.badge_clearmail,"");
        achievementArrayList.add(ac9);
        Achievement ac10 = new Achievement(R.drawable.badge_mealplan,"");
        achievementArrayList.add(ac10);
        Achievement ac11 = new Achievement(R.drawable.badge_exercise,"");
        achievementArrayList.add(ac11);
        Achievement ac12 = new Achievement(R.drawable.badge_meditate,"");
        achievementArrayList.add(ac12);
        Achievement ac13 = new Achievement(R.drawable.badge_newskill,"");
        achievementArrayList.add(ac13);
        Achievement ac14 = new Achievement(R.drawable.badge_cookanewdish,"");
        achievementArrayList.add(ac14);
        Achievement ac15 = new Achievement(R.drawable.badge_newlanguage,"");
        achievementArrayList.add(ac15);
        Achievement ac16 = new Achievement(R.drawable.badge_newinstrument,"");
        achievementArrayList.add(ac16);
        Achievement ac17 = new Achievement(R.drawable.badge_learntodraw,"");
        achievementArrayList.add(ac17);
        Achievement ac18 = new Achievement(R.drawable.badge_learntopaint,"");
        achievementArrayList.add(ac18);
        Achievement ac19 = new Achievement(R.drawable.badge_washdishes,"");
        achievementArrayList.add(ac19);
        Achievement ac20 = new Achievement(R.drawable.badge_composesong,"");
        achievementArrayList.add(ac20);
        Achievement ac21 = new Achievement(R.drawable.badge_callfriend,"");
        achievementArrayList.add(ac21);
        Achievement ac22 = new Achievement(R.drawable.badge_reflection,"");
        achievementArrayList.add(ac22);
        Achievement ac23 = new Achievement(R.drawable.badge_buyfood,"");
        achievementArrayList.add(ac23);
        Achievement ac24 = new Achievement(R.drawable.badge_encouragefriend,"");
        achievementArrayList.add(ac24);


    }


}
