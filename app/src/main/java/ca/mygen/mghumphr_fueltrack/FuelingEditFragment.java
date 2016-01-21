package ca.mygen.mghumphr_fueltrack;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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


    public FuelingEditFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = FuelUp.MAP.get(getArguments().getString(ARG_ITEM_ID));
        } else {
            mItem = FuelUp.create();
        }

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getDate().toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fueling_edit, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.fueling_edit)).setText(mItem.getTotalCost().toString());
        }

        return rootView;
    }


}
