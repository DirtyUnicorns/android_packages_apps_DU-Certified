package com.dirtyunicorns.certified.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ItemDownloader {

    private static volatile ThemesApi themesApi;

    public static ThemesApi getThemesApi() {
        if (themesApi == null) {
            synchronized (ItemDownloader.class) {
                if (themesApi == null) {
                    themesApi = new Retrofit.Builder()
                            .baseUrl("https://raw.githubusercontent.com/DirtyUnicorns/android_packages_apps_DU-Certified/README/jsons/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ThemesApi.class);
                }
            }
        }

        return themesApi;

    }

    public interface ThemesApi {

        @GET("lightthemes.json")
        Call<List<Theme>> getLightThemes();

        @GET("darkthemes.json")
        Call<List<Theme>> getDarkThemes();

        @GET("free_light_themes")
        Call<List<Theme>> getFreeLightThemes();

        @GET("free_dark_themes")
        Call<List<Theme>> getFreeDarkThemes();
    }
}
