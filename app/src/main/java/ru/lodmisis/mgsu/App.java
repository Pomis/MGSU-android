package ru.lodmisis.mgsu;

import android.app.Application;
import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lodmisis.mgsu.api.Endpoints;


public class App extends Application {

    static private Realm realm;
    static private Endpoints endowmentService;
    static private SettingsPrefs settingsPrefs;


    @Override
    public void onCreate() {
        super.onCreate();
        initRealm(this);
        initRetrofit(this);
    }

    private static void initRetrofit(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        httpClient.cookieJar(cookieJar);

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

}
