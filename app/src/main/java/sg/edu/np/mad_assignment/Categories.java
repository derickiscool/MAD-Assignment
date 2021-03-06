package sg.edu.np.mad_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Categories extends Fragment {

    private Button foodButton, wellnessButton, healthButton;

    final String TAG = "Categories";

    /*
    * This page contains code for the different categories such as food, wellness
    * and health. Upon clicking any of the buttons, it will lead to one of the
    * category pages and the different codes will be loaded on the other respective
    * categories.
    */

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_categories, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        foodButton = v.findViewById(R.id.category_food);
        wellnessButton = v.findViewById(R.id.category_wellness);
        healthButton = v.findViewById(R.id.category_health);

        foodButton.setBackgroundResource(R.drawable.food);
        wellnessButton.setBackgroundResource(R.drawable.wellness);
        healthButton.setBackgroundResource(R.drawable.health);

        Log.v(TAG, "Finished Dashboard Pre-Initialisation!");
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Proceeding onto Food Feed!");
                foodPage(); // moves to food feed
            }
        });
        wellnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Proceeding onto Wellness Feed!");
                wellnessPage(); // moves to wellness feed
            }
        });
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Proceeding onto Health Feed!");
                healthPage(); // moves to health feed
            }
        });

    }

    private void foodPage(){
        Intent advancePage = new Intent(getActivity(), foodFeed.class);
        startActivity(advancePage);
    }

    private void wellnessPage(){
        Intent advancePage = new Intent(getActivity(), wellnessFeed.class);
        startActivity(advancePage);
    }

    private void healthPage(){
        Intent advancePage = new Intent(getActivity(), healthFeed.class);
        startActivity(advancePage);
    }
}
