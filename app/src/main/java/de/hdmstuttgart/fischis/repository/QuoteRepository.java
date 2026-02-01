package de.hdmstuttgart.fischis.repository;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import de.hdmstuttgart.fischis.quoteApi.QuoteResponse;
import de.hdmstuttgart.fischis.quoteApi.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * class that combines view model and Data Source(quote from API)
 */
public class QuoteRepository {
    private MutableLiveData<String> quote = new MutableLiveData<>();
    private final Executor executor;

    public QuoteRepository(Application application, Executor executor) {
        this.executor = executor;
    }


    public MutableLiveData<String> getQuote() {
        executor.execute(() -> {
            Call<QuoteResponse> searchCall = RetrofitClient.getINSTANCE().getApi().getRandomQuote();
            searchCall.enqueue(new Callback<QuoteResponse>() {
                @Override
                public void onResponse(@NotNull Call<QuoteResponse> searchCall, @NotNull Response<QuoteResponse> response) {
                    QuoteResponse randomQuote = response.body();
                    Log.d("QuoteRepository", "onResponse: " + randomQuote.getContent());
                    quote.setValue(randomQuote.getContent());

                }

                @Override
                public void onFailure(Call<QuoteResponse> call, Throwable t) {
                    Log.d("QuoteRepository","Unable to get quote " +  t.getMessage());
                }
            });
        });
        return quote;
    }


}
