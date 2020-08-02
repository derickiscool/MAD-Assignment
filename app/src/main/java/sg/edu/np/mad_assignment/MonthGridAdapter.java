package sg.edu.np.mad_assignment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MonthGridAdapter extends ArrayAdapter {
    ArrayList<Date> dates;
    Calendar currentDate;
    ArrayList<Events> events;
    LayoutInflater inflater;


    public MonthGridAdapter(@NonNull Context context, ArrayList<Date> dates, Calendar currentDate , ArrayList<Events> events) {

        super(context, R.layout.single_cell_layout);
        this.dates = dates;
        this.currentDate = currentDate;
        this.events = events;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH) ;
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int currentMonth = currentDate.get(Calendar.MONTH)+1;


        View view =convertView;
        if (view == null)
        {
            view = inflater.inflate(R.layout.single_cell_layout,parent,false) ;

        }
        if (displayMonth == currentMonth)
        {
            view.setBackgroundColor(Color.parseColor("#f7dff6"));
        }
        else{
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }

        TextView Day_Number = view.findViewById(R.id.calendar_day);
        TextView EventNumber = view.findViewById(R.id.events_id);
        Day_Number.setText(String.valueOf(dayNo));
        Calendar eventCalender = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i =0;i< events.size();++i){
            eventCalender.setTime(ConvertStringToDate(events.get(i).getDATE()));
            if (dayNo == eventCalender.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalender.get(Calendar.MONTH)+1)
            {
                arrayList.add(events.get(i).getEVENT());
                EventNumber.setText(arrayList.size()+ " Events");

            }



        }

        return view;
    }

    private Date ConvertStringToDate(String eventDate)
    {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(eventDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
