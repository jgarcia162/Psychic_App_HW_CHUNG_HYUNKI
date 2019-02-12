package android.pursuit.org.psychic_app_hw_hyunki_chung.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Consider these changes. This adds more flexibility to your app and allows you to effortlessly implement new APIs. By creating singleton service objects we can create new Retrofit objects for each one. Previously your Retrofit object was tightly coupled to this one specific API.
 * */
public class ApiServiceFactory {
    private static PixabayService pixabayApi;

    public static PixabayService getPixabayService(){
        if(pixabayApi == null){
            pixabayApi = getRetrofit("https://pixabay.com").create(PixabayService.class);
        }
        return pixabayApi;
    }

    public static Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private ApiServiceFactory() { }
}