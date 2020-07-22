package sg.edu.np.mad_assignment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AchievementViewHolder extends RecyclerView.ViewHolder {
    ImageView imgAchievement;
    TextView txtAchievement;

    public AchievementViewHolder(View itemView){
        super(itemView);
        imgAchievement = itemView.findViewById(R.id.imageView_Achievement);
        txtAchievement = itemView.findViewById(R.id.textView_Achievement);
    }
}
