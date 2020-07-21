package sg.edu.np.mad_assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdaptor extends RecyclerView.Adapter<TaskViewHolder> {
    public ArrayList<Task> taskList;
    private UploadInterface uploadInterface;

    public TaskAdaptor(UploadInterface uploadInterface, ArrayList<Task> taskList){
        this.uploadInterface = uploadInterface;
        this.taskList = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new TaskViewHolder(view);
    }

    public void onBindViewHolder(TaskViewHolder holder, final int position){
        Task list_items = taskList.get(position);
        holder.txtTask.setText(list_items.getText());

        holder.gotoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadInterface.onUploadSuccessful(taskList.get(position), position);

            }
        });
    }

    @Override
    public int getItemCount(){
        return taskList.size();
    }

    public interface UploadInterface {
        void onUploadSuccessful(Task task, int position);
    }

}
