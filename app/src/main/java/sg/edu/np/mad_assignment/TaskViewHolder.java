package sg.edu.np.mad_assignment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    TextView txtTask;
    Button gotoup;

    public TaskViewHolder (View itemView){
        super(itemView);
        txtTask = itemView.findViewById(R.id.textViewTasks);
        gotoup = itemView.findViewById(R.id.gotouploadbtn);
    }
}
