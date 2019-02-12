package android.pursuit.org.psychic_app_hw_hyunki_chung;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

public class MainFragment extends Fragment {

    private FragmentInterface fragmentInterface;
    private Button go_button;
    private String query;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInterface) { //does activity implement/contain a FragmentInterface?
            fragmentInterface = (FragmentInterface) context; //if it does we are saving it in a field
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        go_button = view.findViewById(R.id.go_button);

        Spinner spinner = view.findViewById(R.id.spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    query = parent.getItemAtPosition(position).toString();
                    Log.d("query main frag", query);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        //fragment lifecycles can be at any state during the life of your activity so we have to check for a null activity
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        Objects.requireNonNull(getActivity()), R.array.spinner_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);

        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("query go button", query);
                fragmentInterface.showChoiceFragment(query);
            }
        });
    }


}
