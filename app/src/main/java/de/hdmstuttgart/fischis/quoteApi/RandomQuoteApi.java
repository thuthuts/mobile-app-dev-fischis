package de.hdmstuttgart.fischis.quoteApi;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit Interface defining endpoint and declare method to get random Quote from API
 */
public interface RandomQuoteApi {

    @GET("/random")
    Call<QuoteResponse> getRandomQuote();
}
