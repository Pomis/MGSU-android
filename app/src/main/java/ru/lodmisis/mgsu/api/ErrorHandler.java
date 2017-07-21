package ru.lodmisis.mgsu.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import java.net.ConnectException;
import java.util.Arrays;

import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by romanismagilov on 20.07.17.
 */

public class ErrorHandler {

    static public void handle(Throwable throwable, Context context) {
        Log.d("kek handler", Arrays.toString(throwable.getStackTrace()));
        Log.d("kek handler", throwable.toString());

        if (throwable instanceof HttpException) {
            Toast.makeText(context, "Неверные данные!", Toast.LENGTH_LONG).show();
        } else if (throwable instanceof ConnectException) {
            Toast.makeText(context, "Отсутствует подключение к интернету!", Toast.LENGTH_LONG).show();
        } else if (throwable instanceof IllegalArgumentException) {
            Toast.makeText(context, "Не все поля заполнены!", Toast.LENGTH_LONG).show();
        }
    }
}
