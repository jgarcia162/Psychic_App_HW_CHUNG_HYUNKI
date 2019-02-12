package android.pursuit.org.psychic_app_hw_hyunki_chung;

import android.content.Context;
import android.content.SharedPreferences;
import android.pursuit.org.psychic_app_hw_hyunki_chung.model.Hit;
import android.pursuit.org.psychic_app_hw_hyunki_chung.model.PixabayResponse;
import android.pursuit.org.psychic_app_hw_hyunki_chung.network.PixabayService;
import android.pursuit.org.psychic_app_hw_hyunki_chung.network.ApiServiceFactory;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FragmentInterface {
    //you can also hide your API keys in the gradle.properties file. Don't forget to add it to gitignore. If anyone wants to clone your app and build on it they would need to add the API key to their own gradle.properties file
    private static final String pixabay_apiKey = BuildConfig.KEY_API_PIXABAY;
    private static final String request_image_type = "photo";
    private SharedPreferences sharedPreferences;
    private static final String SharedPreferences_TAG = "network call exists";
    private ResultsDatabase resultsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences(SharedPreferences_TAG, Context.MODE_PRIVATE);

        resultsDatabase = new ResultsDatabase(getApplicationContext());

        PixabayService pixabayService = ApiServiceFactory.getPixabayService();

        //if the API call depends on which option the user selects, no need to create all three.

        if (!sharedPreferences.contains("Man")) {
            pixabayService
                    .getPixabayResponse(pixabay_apiKey, "man", request_image_type)
                    .enqueue(new Callback<PixabayResponse>() {
                        @Override
                        public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
                            final List<Hit> hits = response.body().getHits();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            for (int i = 0; i < 4; i++) {
                                resultsDatabase.addImage("Man", hits.get(i).getWebformatURL());
                                Log.d("danny", hits.get(i).getWebformatURL());
                                editor.putString("Man", hits.get(i).getWebformatURL());
                            }
                            editor.apply();
                        }

                        @Override
                        public void onFailure(Call<PixabayResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }
        if (!sharedPreferences.contains("Bear")) {
            pixabayService
                    .getPixabayResponse(pixabay_apiKey, "bear", request_image_type)
                    .enqueue(new Callback<PixabayResponse>() {

                        @Override
                        public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
                            final List<Hit> hits = response.body().getHits();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            for (int i = 0; i < 4; i++) {
                                resultsDatabase.addImage("Bear", hits.get(i).getWebformatURL());
                                editor.putString("Bear", hits.get(i).getWebformatURL());
                            }
                            editor.apply();
                        }

                        @Override
                        public void onFailure(Call<PixabayResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }
        if (!sharedPreferences.contains("Pig")) {
            pixabayService
                    .getPixabayResponse(pixabay_apiKey, "pig", request_image_type)
                    .enqueue(new Callback<PixabayResponse>() {
                        @Override
                        public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
                            final List<Hit> hits = response.body().getHits();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            for (int i = 0; i < 4; i++) {
                                resultsDatabase.addImage("Pig", hits.get(i).getWebformatURL());
                                editor.putString("Pig", hits.get(i).getWebformatURL());
                            }
                            editor.apply();
                        }

                        @Override
                        public void onFailure(Call<PixabayResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }


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
                .replace(R.id.main_container, choiceFragment)
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
                .replace(R.id.main_container, resultFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void reset() {
        final Fragment mainFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, mainFragment)
                .commit();
    }
}
