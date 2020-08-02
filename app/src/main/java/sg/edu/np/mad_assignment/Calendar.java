package sg.edu.np.mad_assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class Calendar extends Fragment {
    private AlertDialog alertDialog;
    private Spinner monthSpinner;
    private GridView monthGrid;
    private DBOpenHelper dbOpenHelper;
    private static final int MAX_CALENDAR_DAYS = 42;
    java.util.Calendar calendar = java.util.Calendar.getInstance(Locale.ENGLISH);
    private MonthGridAdapter monthGridAdapter;
    private ArrayList<Date> dates = new ArrayList<>();
    private ArrayList<Events>eventList = new ArrayList<>();
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd",Locale.ENGLISH);
    private static final String TAG = "Calendar";
    DatabaseReference db =FirebaseDatabase.getInstance().getReference("Member");




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_calendar, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final View v = getView();
        monthSpinner = (Spinner) v.findViewById(R.id.month_Spinner);
        monthGrid = v.findViewById(R.id.month_grid);
        SetUpCalendar();
        int indexOfMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        monthSpinner.setSelection(indexOfMonth);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                calendar.set(java.util.Calendar.MONTH,position);
                SetUpCalendar();





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }

        });
        monthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_new_event,null);
                final EditText eventName = (EditText) addView.findViewById(R.id.et_Task);
                Button addEvent = (Button) addView.findViewById(R.id.addEventButton);
                CheckBox notifyMe = (CheckBox) addView.findViewById(R.id.notifyMe);
                final String date = dateFormat.format(dates.get(position));
                final String month = monthFormat.format(dates.get(position));
                addEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SaveEvent(eventName.getText().toString(),date,month);
                        SetUpCalendar();
                        alertDialog.dismiss();



                    }
                });
                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        monthGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               String date = dateFormat.format(dates.get(position));
               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setCancelable(true);
               View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.show_events_tasks_layout,parent,false);
               RecyclerView eventsRecycler = view1.findViewById(R.id.EventsRV);
               EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(getActivity(),CollectEventByDate(date));
               eventsRecycler.setAdapter(eventRecyclerAdapter);
               GridLayoutManager glm = new GridLayoutManager(getActivity(), 3);
               eventsRecycler.setLayoutManager(glm);
               eventsRecycler.setHasFixedSize(true);
               eventRecyclerAdapter.notifyDataSetChanged();

               builder.setView(view1);
               alertDialog = builder.create();
               alertDialog.show();
               alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                   @Override
                   public void onCancel(DialogInterface dialog) {
                       SetUpCalendar();
                   }
               });

               return true;

            }
        });
    }
   /* private ArrayList<CalendarTask> CollectTaskByDate (String datee)
    {
        ArrayList<CalendarTask> arrayList = new ArrayList<>();

        DatabaseReference ref = db.child("completedTasks");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    snapshot1.child("dateComplete").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/




    private ArrayList<Events> CollectEventByDate (String datee){
        ArrayList<Events> arrayList = new ArrayList<>();
        dbOpenHelper = new DBOpenHelper(getActivity());
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor =dbOpenHelper.ReadEvents(datee,database);
        while (cursor.moveToNext())
        {
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            Events events = new Events(event,date,month);
            arrayList.add(events);


        }
        cursor.close();
        dbOpenHelper.close();
        return arrayList;
    }
    public void SetUpCalendar(){
        dates.clear();
        java.util.Calendar monthCalendar = (java.util.Calendar) calendar.clone();
        monthCalendar.set(java.util.Calendar.DAY_OF_MONTH,1);
        int firstDayOfMonth = monthCalendar.get(java.util.Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(java.util.Calendar.DAY_OF_MONTH, -firstDayOfMonth);
        CollectEvents(monthFormat.format(calendar.getTime()));
        while (dates.size()< MAX_CALENDAR_DAYS)
        {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(java.util.Calendar.DAY_OF_MONTH,1);

        }
        monthGridAdapter = new MonthGridAdapter(getActivity(),dates,calendar,eventList);
        monthGrid.setAdapter(monthGridAdapter);
    }
    private void SaveEvent(String event,String date,String month)
    {
        dbOpenHelper = new DBOpenHelper(getActivity());
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveEvent(event,date,month,database);
        dbOpenHelper.close();
        Toast.makeText(getActivity(), "Event Added", Toast.LENGTH_SHORT).show();


    }
    private void CollectEvents(String cursorMonth){
        eventList.clear();
        dbOpenHelper = new DBOpenHelper(getActivity());
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEventsperMonth(cursorMonth,database);
        while (cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            Events events = new Events(event,date,month);
            eventList.add(events);

        }
        cursor.close();

    }



}