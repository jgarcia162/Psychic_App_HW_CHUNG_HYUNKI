package android.pursuit.org.psychic_app_hw_hyunki_chung;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class ResultFragment extends Fragment {
    private FragmentInterface fragmentInterface;
    private ResultsDatabase resultsDatabase;
    private TextView percentView;
    private TextView conclusionView;
    private ImageView resultImageView;
    private Button clearButton;
    private Button resetButton;
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
        //component related objects can be initialized in onCreate.
        resultsDatabase = ResultsDatabase.getInstance(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //all these views, heres a perfect opportunity to use ButterKnife.
        resetButton = view.findViewById(R.id.reset_button);
        clearButton = view.findViewById(R.id.clear_button);
        percentView = view.findViewById(R.id.percent_textView);
        resultImageView = view.findViewById(R.id.result_imageView);
        conclusionView = view.findViewById(R.id.conclusion_textView);

        if (userChoice.equals(computerChoice)){
            isCorrect = true;
        }
        resultsDatabase.updateResults(isCorrect);

        if(isCorrect){

            Glide.with(getActivity().getApplicationContext())
                    .load("https://media.giphy.com/media/l2SpRSfoMQrOF00sE/giphy.gif")
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(new GlideDrawableImageViewTarget(resultImageView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);

                            //check isRefreshing
                        }
                    });
            conclusionView.setText("Correct");
        }else{
            Glide.with(getActivity().getApplicationContext())
                    .load("https://media.giphy.com/media/26ufdzuMVeOnnrj68/giphy.gif")
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(new GlideDrawableImageViewTarget(resultImageView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);

                            //check isRefreshing
                        }
                    });
            conclusionView.setText("Incorrect");
        }

        percentView.setText(Integer.toString(resultsDatabase.getResults()) + "% accuracy");

        Animation scaling = AnimationUtils.loadAnimation(getContext(), R.anim.scaling);
        
        conclusionView.startAnimation(scaling);


        final Animation animSlide = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.left_to_right);
        final Animation spin = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.rotate_center);

        spin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                conclusionView.setVisibility(View.INVISIBLE);
                clearButton.setEnabled(false);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resultsDatabase.clearResults();
                percentView.setText("Results Cleared!");

                percentView.startAnimation(animSlide);

                conclusionView.clearAnimation();
                conclusionView.setAnimation(spin);

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fragmentInterface.reset();
            }
        });

    }
}
