package ru.lodmisis.mgsu.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import java.net.ConnectException;
import java.util.Arrays;

import es.dmoral.toasty.Toasty;
import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by romanismagilov on 20.07.17.
 */

public class ErrorHandler {

    static public void handle(Throwable throwable, Context context) {
        Log.d("kek handler", Arrays.toString(throwable.getStackTrace()));
        Log.d("kek handler", throwable.toString());

        if (throwable instanceof HttpException) {
            switch (((HttpException)throwable).code()) {
                case 502:
                    Toasty.error(context, "Сервер недоступен!").show();
                    break;
                case 404:
                    Toasty.error(context, "Неверные данные!").show();
                    break;
                case 500:
                    Toasty.error(context, "Внутренняя ошибка сервера.").show();
                    break;
                default:
                    Toasty.error(context, "Ошибка соединения с сервером.").show();
                    break;
            }
        } else if (throwable instanceof ConnectException) {
            Toasty.error(context, "Отсутствует подключение к интернету!", Toast.LENGTH_LONG).show();
        } else if (throwable instanceof IllegalArgumentException) {
            Toasty.error(context, "Не все поля заполнены!", Toast.LENGTH_LONG).show();
        }
    }
}
