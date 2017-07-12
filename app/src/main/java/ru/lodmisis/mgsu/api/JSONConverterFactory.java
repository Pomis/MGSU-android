package ru.lodmisis.mgsu.api;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by romanismagilov on 06.06.17.
 */

public class JSONConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return JsonConverter.INSTANCE;
    }

    final static class JsonConverter implements Converter<ResponseBody, JSONObject> {
        static final JsonConverter INSTANCE = new JsonConverter();

        @Override
        public JSONObject convert(@NonNull ResponseBody responseBody) throws IOException {
            try {
                String kek = responseBody.string();
                JSONObject obj = new JSONObject(kek);
                return obj;//.getJSONObject("result");
            } catch (JSONException e) {
                throw new IOException("Failed to parse JSON", e);
            }
        }
    }
}