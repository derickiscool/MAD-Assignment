package sg.edu.np.mad_assignment;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

public class AchievementViewHolder extends RecyclerView.ViewHolder {
    ImageView imgAchievement;

    public AchievementViewHolder(View itemView){
        super(itemView);
        imgAchievement = itemView.findViewById(R.id.imageView_Achievement);
    }
}
