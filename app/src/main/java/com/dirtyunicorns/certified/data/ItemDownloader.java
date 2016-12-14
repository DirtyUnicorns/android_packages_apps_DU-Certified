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
                            .baseUrl("https://raw.githubusercontent.com/DirtyUnicorns/android_packages_apps_DU-Certified/n7x/jsons/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ThemesApi.class);
                }
            }
        }

        return themesApi;

    }

    public interface ThemesApi {

        @GET("light.json")
        Call<List<Theme>> getLightThemes();

        @GET("dark.json")
        Call<List<Theme>> getDarkThemes();

        @GET("multicolor.json")
        Call<List<Theme>> getMultiColorThemes();

        @GET("FREE_ONLY_light.json")
        Call<List<Theme>> getFreeLightThemes();

        @GET("FREE_ONLY_dark.json")
        Call<List<Theme>> getFreeDarkThemes();

        @GET("FREE_ONLY_multicolor.json")
        Call<List<Theme>> getFreeMultiColorThemes();
    }
}
