package android.pursuit.org.psychic_app_hw_hyunki_chung;

import android.content.Context;
import android.os.Bundle;
import android.pursuit.org.psychic_app_hw_hyunki_chung.model.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class ChoiceFragment extends Fragment {


    private FragmentInterface fragmentInterface;
    Random rand = new Random();
    private String userChoice;
    private String computerChoice;
    private ResultsDatabase resultsDatabase;
    private ImageView imageOne;
    private ImageView imageTwo;
    private ImageView imageThree;
    private ImageView imageFour;
    List<Image> imageList;
    String query;

    public static ChoiceFragment newInstance() {
        return new ChoiceFragment();
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
            query = getArguments().getString("query");
            Log.d("query choice frag", query);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        resultsDatabase = ResultsDatabase.getInstance(this.getActivity());
        imageList = resultsDatabase.getImageList(query);

        computerChoice = randomChoice(imageList);

        imageOne = view.findViewById(R.id.imageView_1);
        imageTwo = view.findViewById(R.id.imageView_2);
        imageThree = view.findViewById(R.id.imageView_3);
        imageFour = view.findViewById(R.id.imageView_4);

        Picasso.get().load(imageList.get(0).getImage_url()).fit().centerCrop().into(imageOne);
        Picasso.get().load(imageList.get(1).getImage_url()).fit().centerCrop().into(imageTwo);
        Picasso.get().load(imageList.get(2).getImage_url()).fit().centerCrop().into(imageThree);
        Picasso.get().load(imageList.get(3).getImage_url()).fit().centerCrop().into(imageFour);

        imageOne.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                userChoice = imageList.get(0).getImage_url();
                fragmentInterface.showResultFragment(userChoice,computerChoice);
            }
        });
        imageTwo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                userChoice = imageList.get(1).getImage_url();
                fragmentInterface.showResultFragment(userChoice,computerChoice);
            }
        });
        imageThree.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                userChoice = imageList.get(2).getImage_url();
                fragmentInterface.showResultFragment(userChoice,computerChoice);
            }
        });
        imageFour.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                userChoice = imageList.get(3).getImage_url();
                fragmentInterface.showResultFragment(userChoice,computerChoice);
            }
        });


    }

    public String randomChoice(List<Image> imageList) {
        int max = 3;
        int min = 0;

        int randomNum = rand.nextInt((max - min) + 1);

        return imageList.get(randomNum).getImage_url();
    }




}
