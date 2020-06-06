package sg.edu.np.mad_assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdaptor extends RecyclerView.Adapter<TaskViewHolder> {
    private ArrayList<Task> taskList;
    public TaskAdaptor(ArrayList<Task> taskList){
        this.taskList = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new TaskViewHolder(view);
    }

    public void onBindViewHolder(TaskViewHolder holder, int position){
        Task list_items = taskList.get(position);
        holder.txtTask.setText(list_items.getText());
    }

    @Override
    public int getItemCount(){
        return  taskList.size();
    }

    //add goto upload page btn
}
