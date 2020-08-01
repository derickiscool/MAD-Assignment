package sg.edu.np.mad_assignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class Logout extends Fragment {

    public String GLOBAL_PREFS = "MyPrefs";
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_logout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View v = getView();

        sharedPreferences = this.getActivity().getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE); //Only accessible to calling application.


        AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());
        alert.setTitle("Logout");
        alert.setMessage("Are you sure you want to logout?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.clear();
                e.apply();

                Intent logout = new Intent(v.getContext(), MainActivity.class);
                startActivity(logout);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }
}