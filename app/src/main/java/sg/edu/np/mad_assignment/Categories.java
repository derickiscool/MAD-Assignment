package sg.edu.np.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Categories extends AppCompatActivity {

    private Button foodButton, wellnessButton, healthButton;
    final String TAG = "Dashboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        foodButton = findViewById(R.id.category_food);
        wellnessButton = findViewById(R.id.category_wellness);
        healthButton = findViewById(R.id.category_health);

        foodButton.setBackgroundResource(R.drawable.food);
        wellnessButton.setBackgroundResource(R.drawable.wellness);
        healthButton.setBackgroundResource(R.drawable.health);

        Log.v(TAG, "Finished Dashboard Pre-Initialisation!");
    }

    @Override
    protected void onStart(){
        super.onStart();
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodPage();
            }
        });
        wellnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wellnessPage();
            }
        });
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthPage();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Dashboard!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Dashboard!");
        finish();
    }


    private void foodPage(){
        //Intent advancedPage = new Intent(Dashboard.this, foodFeed.class);
        //startActivity(advancedPage);
    }

    private void wellnessPage(){
        //Intent advancedPage = new Intent(Dashboard.this, wellnessFeed.class);
        //startActivity(advancedPage);
    }

    private void healthPage(){
        //Intent advancedPage = new Intent(Dashboard.this, healthFeed.class);
        //startActivity(advancedPage);
    }
}
