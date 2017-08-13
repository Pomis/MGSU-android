package ru.lodmisis.mgsu;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lodmisis.mgsu.activities.SwipeableActivity;
import ru.lodmisis.mgsu.api.Endpoints;
import ru.lodmisis.mgsu.fragments.ErrorFragment;


public class App extends Application {

    static private Realm realm;
    static private Endpoints endowmentService;
    static private SettingsPrefs settingsPrefs;


    @Override
    public void onCreate() {
        super.onCreate();
        initPrefs(this);
        initRealm(this);
        initRetrofit(this);
        initErrorHandler(this);
    }

    private static void initRetrofit(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.cookieJar(new CookieJar() {
            String cookieString = null;

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null) {
                    settingsPrefs.setCookie(cookies.get(0).toString());
                    Log.d("kek cuki saived", url.toString() + ": " + cookies.get(0).toString());
                } else
                    Log.d("kek cuki", url.toString() + ": empti cucki");

            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                Log.d("kek cukiES", url.toString());
                ArrayList<Cookie> cookies = new ArrayList<Cookie>();
                if (cookieString == null) {
                    cookieString = settingsPrefs.getCookie();
                }
                Log.d("kek cuki loadet", "cuckie: " + cookieString);
                if (cookieString != null) {
                    Cookie cookie = Cookie.parse(url, cookieString);
                    cookies.add(cookie);
                }

                return cookies;
            }
        });
//        ClearableCookieJar cookieJar =
//                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
//        httpClient.cookieJar(cookieJar);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(new JSONConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Settings.BASE_URL)
                .client(httpClient.build())
                .build();

        endowmentService = retrofit.create(Endpoints.class);
    }

    private static void initRealm(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("telenote.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    private static void initErrorHandler(Context context) {
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) ->
                SwipeableActivity.start(context, ErrorFragment.class, ex));
    }

    private static void initPrefs(Context context) {
        settingsPrefs = SettingsPrefs.create(context);
    }

    static public Realm getRealmInstance(Context context) {
        if (realm == null) initRealm(context);
        return realm;
    }

    static public SettingsPrefs getPrefsInstance(Context context) {
        if (settingsPrefs == null) initPrefs(context);
        return settingsPrefs;
    }

    static public Endpoints getEndowmentService(Context context) {
        if (endowmentService == null) initRetrofit(context);
        return endowmentService;
    }

    public static int dpFromPx(final Context context, final float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    public static int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }


}
