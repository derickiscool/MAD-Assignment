package sg.edu.np.mad_assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AchievementAdaptor extends RecyclerView.Adapter<AchievementViewHolder> {
    private ArrayList<Achievement> achievementList;
    public AchievementAdaptor(ArrayList<Achievement> achievementList){
        this.achievementList = achievementList;
    }

    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_layout, parent, false);
        return new AchievementViewHolder(view);
    }

    public void onBindViewHolder(AchievementViewHolder holder, int position){
        Achievement list_items = achievementList.get(position);
        holder.imgAchievement.setImageResource(list_items.getImageID());
    }

    @Override
    public int getItemCount(){
        return achievementList.size();
    }
}
