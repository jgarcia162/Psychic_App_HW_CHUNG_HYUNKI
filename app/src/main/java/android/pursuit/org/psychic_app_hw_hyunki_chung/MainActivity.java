package android.pursuit.org.psychic_app_hw_hyunki_chung;

import android.pursuit.org.psychic_app_hw_hyunki_chung.model.Hit;
import android.pursuit.org.psychic_app_hw_hyunki_chung.model.PixabayResponse;
import android.pursuit.org.psychic_app_hw_hyunki_chung.model.Result;
import android.pursuit.org.psychic_app_hw_hyunki_chung.network.PixabayService;
import android.pursuit.org.psychic_app_hw_hyunki_chung.network.RetrofitInstance;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements FragmentInterface {
    public static final String pixabay_apiKey = "11491264-e9a60979d4074889ac3e00286";
    public static final String request_image_type = "photo";
    ResultsDatabase resultsDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitInstance.getInstance();
        resultsDatabase = new ResultsDatabase(getApplicationContext());

        PixabayService pixabayService = retrofit.create(PixabayService.class);

        Call<PixabayResponse> responseCats =
                pixabayService.getPixabayResponse(pixabay_apiKey,"wild cat",request_image_type);
        Call<PixabayResponse> responseDogs =
                pixabayService.getPixabayResponse(pixabay_apiKey,"wolf",request_image_type);
        Call<PixabayResponse> responseDragons =
                pixabayService.getPixabayResponse(pixabay_apiKey,"komodo dragon",request_image_type);

        responseCats.enqueue(new Callback<PixabayResponse>() {

            @Override
            public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
                final List<Hit> hits = response.body().getHits();
                for (int i = 0; i < 4; i++) {
                    resultsDatabase.addImage("Cats",hits.get(i).getWebformatURL());
                    Log.d("danny",hits.get(i).getWebformatURL());
                }
            }

            @Override
            public void onFailure(Call<PixabayResponse> call, Throwable t) {

            }
        });

        responseDogs.enqueue(new Callback<PixabayResponse>() {

            @Override
            public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
                final List<Hit> hits = response.body().getHits();
                for (int i = 0; i < 4; i++) {
                    resultsDatabase.addImage("Dogs",hits.get(i).getWebformatURL());
                }

            }

            @Override
            public void onFailure(Call<PixabayResponse> call, Throwable t) {

            }
        });

        responseDragons.enqueue(new Callback<PixabayResponse>() {

            @Override
            public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
                final List<Hit> hits = response.body().getHits();
                for (int i = 0; i < 4; i++) {
                    resultsDatabase.addImage("Dragons",hits.get(i).getWebformatURL());
                }
            }

            @Override
            public void onFailure(Call<PixabayResponse> call, Throwable t) {

            }
        });


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void showChoiceFragment(String query) {
        ChoiceFragment choiceFragment = ChoiceFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        choiceFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container,choiceFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showResultFragment(String userChoice, String computerChoice) {
        ResultFragment resultFragment = ResultFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("userChoice", userChoice);
        bundle.putString("computerChoice", computerChoice);
        resultFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container,resultFragment)
                .addToBackStack(null)
                .commit();
    }
}
