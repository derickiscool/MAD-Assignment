package sg.edu.np.mad_assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CalendarTaskRecyclerAdapter extends RecyclerView.Adapter<CalendarTaskRecyclerAdapter.MyViewHolder>  {
    Context context;
    ArrayList<CalendarTask> arrayList;



    public CalendarTaskRecyclerAdapter(Context context, ArrayList<CalendarTask> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_tasks_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CalendarTask task = arrayList.get(position);
        holder.Task.setText(task.getTaskName());
        holder.DateTxt.setText(task.getDateComplete() + " Task Completed!");
        Picasso.get().load(task.getImgUrl()).into(holder.img);




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView DateTxt,Task;
        ImageView img;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            DateTxt = itemView.findViewById(R.id.TaskDate);
            Task = itemView.findViewById(R.id.TaskName);
            img = itemView.findViewById(R.id.taskImage);


        }
    }
}
