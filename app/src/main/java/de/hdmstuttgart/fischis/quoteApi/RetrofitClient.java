package de.hdmstuttgart.fischis.quoteApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Initializes Retrofit and creates static instance
 * set up the base url
 */
public class RetrofitClient {
    private final String url = "https://api.quotable.io/";
    private static RetrofitClient INSTANCE;
    private final Retrofit retrofit;

    private RetrofitClient() {

        Gson gsonBuilder = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .build();
    }


    /**
     * @return a Retrofit instance
     */
    public static synchronized RetrofitClient getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new RetrofitClient();
        }
        return INSTANCE;
    }


    public RandomQuoteApi getApi() {
        return retrofit.create(RandomQuoteApi.class);
    }

}

