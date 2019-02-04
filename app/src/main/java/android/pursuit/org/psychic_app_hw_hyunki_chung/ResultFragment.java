package android.pursuit.org.psychic_app_hw_hyunki_chung;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultFragment extends Fragment {
    private FragmentInterface fragmentInterface;
    private ResultsDatabase resultsDatabase;
    private TextView percentView;
    private TextView conclusionView;
    private String userChoice;
    private String computerChoice;
    private boolean isCorrect;

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInterface) { //does activity implement/contain a FragmentInterface?
            fragmentInterface = (FragmentInterface) context; //if it does we are saving it in a field
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userChoice = getArguments().getString("userChoice");
            computerChoice = getArguments().getString("computerChoice");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultsDatabase = ResultsDatabase.getInstance(this.getActivity());

        percentView = view.findViewById(R.id.percent_textView);
        conclusionView = view.findViewById(R.id.conclusion_textView);

        if (userChoice.equals(computerChoice)){
            isCorrect = true;
        }
        resultsDatabase.updateResults(isCorrect);

        if(isCorrect){
            conclusionView.setText("Correct");
        }else{
            conclusionView.setText("Incorrect");
        }

        percentView.setText(Integer.toString(resultsDatabase.getResults()) + "% accuracy");


    }
}
