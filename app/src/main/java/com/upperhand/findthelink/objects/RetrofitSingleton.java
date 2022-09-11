package com.upperhand.findthelink.objects;

import com.upperhand.findthelink.interfaces.GetData;
import com.upperhand.findthelink.interfaces.PostData;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static RetrofitSingleton instance;
    private static final String apiUrl = "https://restapiforapp.herokuapp.com/";
    private static final String apiBinanceUrl = "https://api.binance.com/api/v3/ticker/" ;

    private GetData getInterface;
    private PostData postInterface;
    private GetData apiPriceInterface;

    private RetrofitSingleton(){
        buildRetrofit();
    }

    public static RetrofitSingleton get(){
        if(instance == null){
            instance = new RetrofitSingleton();
        }
        return instance;
    }

    private void buildRetrofit(){

        OkHttpClient okHttpClient;

        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[] { trustManager }, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        Retrofit retrofit_last_price = new Retrofit.Builder()
                .baseUrl(apiBinanceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        getInterface = retrofit.create(GetData.class);
        postInterface  = retrofit.create(PostData.class);
        apiPriceInterface  = retrofit_last_price.create(GetData.class);
    }

    public GetData getData(){
        return getInterface;
    }

    public GetData getDataPrices(){
        return apiPriceInterface;
    }

    public PostData postData(){
        return postInterface;
    }
}
