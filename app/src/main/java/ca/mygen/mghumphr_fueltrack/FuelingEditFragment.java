package ca.mygen.mghumphr_fueltrack;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FuelingEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FuelingEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FuelingEditFragment extends Fragment {

    public static final String ARG_ITEM_ID = "date_str";

    private FuelUp.Fueling mItem;
    private Boolean isNew;


    public FuelingEditFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = FuelUp.MAP.get(getArguments().getString(ARG_ITEM_ID));
            isNew = false;
        } else {
            mItem = FuelUp.create();
            isNew = true;
        }
        FuelUp.current = mItem;

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            if(isNew){
                appBarLayout.setTitle("New Entry");
            } else {
                appBarLayout.setTitle(mItem.getShortDate());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fueling_edit, container, false);

        updateDisplay(rootView);
        wireUp(rootView);

        return rootView;
    }


    private void updateDisplay(View rootView){
        ((EditText) rootView.findViewById(R.id.editDate)).setText(mItem.getShortDate());
        if(!isNew) {
            ((EditText) rootView.findViewById(R.id.editStation)).setText(mItem.getStation());
            ((EditText) rootView.findViewById(R.id.editOdometer)).setText(mItem.getOdometer().toString());
            ((EditText) rootView.findViewById(R.id.editGrade)).setText(mItem.getGrade());
            ((EditText) rootView.findViewById(R.id.editAmount)).setText(mItem.getAmount().toString());
            ((EditText) rootView.findViewById(R.id.editUnitCost)).setText(mItem.getUnitCost().toString());
        }
    }

    private void wireUp(View rootView){
        // wire up date picker
        EditText dateEditText = (EditText) rootView.findViewById(R.id.editDate);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        // wire up the rest of the change listeners

        final EditText stationEdit = (EditText) rootView.findViewById(R.id.editStation);
        stationEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mItem.setStation(stationEdit.getText().toString());
            }
        });

        final EditText odometerEdit = (EditText) rootView.findViewById(R.id.editOdometer);
        odometerEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                try {
                    mItem.setOdometer(Float.valueOf(odometerEdit.getText().toString()));
                } catch (Exception e){}
            }
        });

        final EditText gradeEdit = (EditText) rootView.findViewById(R.id.editGrade);
        gradeEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mItem.setGrade(gradeEdit.getText().toString());
            }
        });

        final EditText amountEdit = (EditText) rootView.findViewById(R.id.editAmount);
        amountEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    mItem.setAmount(Float.valueOf(amountEdit.getText().toString()));
                } catch (Exception e) {}
            }
        });

        final EditText unitCostEdit = (EditText) rootView.findViewById(R.id.editUnitCost);
        unitCostEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    mItem.setUnitCost(Float.valueOf(unitCostEdit.getText().toString()));
                } catch (Exception e) {}
            }
        });

        final Button doneButton = (Button) rootView.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNew) {
                    FuelUp.add(mItem);
                }
                FuelUp.save(getContext());


                FuelingDetailFragment fragment = new FuelingDetailFragment();

                Bundle arguments = new Bundle();
                arguments.putString(ARG_ITEM_ID, mItem.getId());
                fragment.setArguments(arguments);

                if (getActivity() instanceof FuelingEditActivity) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fueling_edit_container, fragment)
                            .commit();
                } else {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fueling_detail_container, fragment)
                            .commit();
                }
            }
        });

    }

    // http://developer.android.com/guide/topics/ui/controls/pickers.html
    public void showDatePickerDialog(View v) {
        DialogFragment datePicker = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, mItem.getDate().toString());
        datePicker.setArguments(args);
        datePicker.show(getFragmentManager(), "datePicker");
    }


    // http://developer.android.com/guide/topics/ui/controls/pickers.html
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private FuelUp.Fueling mItem;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            mItem = FuelUp.current;
            Calendar cal = Calendar.getInstance();
            cal.setTime(mItem.getDate());
            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            datePicker.setCancelable(false);
            datePicker.setTitle("Select the date");
            return datePicker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            Date date = cal.getTime();
            Date now = new Date(System.currentTimeMillis());
            date.setHours(now.getHours());
            date.setMinutes(now.getMinutes());
            date.setSeconds(now.getSeconds());
            mItem.setDate(date);

            EditText editDate = (EditText) getActivity().findViewById(R.id.editDate);
            editDate.setText(mItem.getShortDate());

        }
    }


}
