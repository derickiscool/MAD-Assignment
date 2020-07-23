package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class FoodFeed extends AppCompatActivity {

    private ImageButton backButton;
    private Button foodUpload;
    final String TAG = "Food Feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed);

        backButton = findViewById(R.id.foodbackButton);
        foodUpload = findViewById(R.id.foodUploadbutton);
    }

    @Override
    protected void onStart(){
        super.onStart();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(FoodFeed.this, Categories.class);
                startActivity(back);
            }
        });
        foodUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent(FoodFeed.this, foodUpload.class);
                startActivity(upload);
            }
        });
    }

}