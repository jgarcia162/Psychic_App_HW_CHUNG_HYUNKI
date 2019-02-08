package android.pursuit.org.psychic_app_hw_hyunki_chung;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class ResultFragment extends Fragment {
    private FragmentInterface fragmentInterface;
    private ResultsDatabase resultsDatabase;
    private TextView percentView;
    private TextView conclusionView;
    private Button clearButton;
    private String userChoice;
    private String computerChoice;
    private boolean isCorrect;

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInterface) {
            fragmentInterface = (FragmentInterface) context;
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
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultsDatabase = ResultsDatabase.getInstance(this.getActivity());

        clearButton = view.findViewById(R.id.clear_button);
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

        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resultsDatabase.clearResults();
                percentView.setText("Results Cleared!");


                Animation animSlide = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                        R.anim.left_to_right);
                percentView.startAnimation(animSlide);



//                ObjectAnimator animator = ObjectAnimator.ofFloat(percentView,"translationX", -70f,70f);
////                Log.d("danny", Integer.toString(resultsDatabase.getResults()) + "% accuracy");
//                animator.setDuration(1000);
//                animator.start();

            }
        });


    }
}
