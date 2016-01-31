package ca.mygen.mghumphr_fueltrack;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A fragment representing a single Fueling detail screen.
 * This fragment is either contained in a {@link FuelingListActivity}
 * in two-pane mode (on tablets) or a {@link FuelingDetailActivity}
 * on handsets.
 */
public class FuelingDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "date_str";

    /**
     * The dummy content this fragment is presenting.
     */
    private FuelUp.Fueling mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FuelingDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = FuelUp.MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getShortDate());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fueling_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.date)).setText(mItem.getShortDate());
            ((TextView) rootView.findViewById(R.id.station)).setText(mItem.getStation());
            ((TextView) rootView.findViewById(R.id.odometer)).setText(mItem.getOdometer());
            ((TextView) rootView.findViewById(R.id.grade)).setText(mItem.getGrade());
            ((TextView) rootView.findViewById(R.id.amount)).setText(mItem.getAmount() + "L");
            ((TextView) rootView.findViewById(R.id.unitCost)).setText("$" + mItem.getUnitCost() + "/L");
            ((TextView) rootView.findViewById(R.id.cost)).setText("$" + mItem.getTotalCost() + "");

        }

        wireUp(rootView);

        return rootView;
    }

    private void wireUp(View rootView){
        final Button editButton = (Button) rootView.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FuelingEditFragment fragment = new FuelingEditFragment();

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
}
