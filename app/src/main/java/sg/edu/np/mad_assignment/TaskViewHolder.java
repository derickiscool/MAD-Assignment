package sg.edu.np.mad_assignment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    TextView txtTask;

    public TaskViewHolder (View itemView){
        super(itemView);
        txtTask = itemView.findViewById(R.id.textViewTasks);
    }
}
