package com.bardiademon.example.ui.viewmodels;

import android.app.Activity;
import android.util.Log;
import com.bardiademon.example.R;
import com.bardiademon.example.data.dto.ResponseDto;
import com.bardiademon.example.data.dto.UsersDto;
import com.bardiademon.example.data.repository.http.HttpUsersService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public final class UserModelView {

    private HttpUsersService service;

    public UserModelView(final Activity activity) {
        initial(activity);
    }

    private void initial(final Activity activity) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(activity.getString(R.string.reqres_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(HttpUsersService.class);
    }

    public void fetch(final int page, final Function<ResponseDto<List<UsersDto>>, Void> function) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                final Call<ResponseDto<List<UsersDto>>> responseDtoCall = service.fetchAllUsers(page);
                final Response<ResponseDto<List<UsersDto>>> execute = responseDtoCall.execute();

                Log.i("FETCH_USER", "Is successfully: " + execute.isSuccessful());
                Log.i("FETCH_USER", "STATUS_CODE: " + execute.code());
                Log.i("FETCH_USER", "IS_NULL_BODY: " + (execute.body() == null));
                Log.i("FETCH_USER", "BODY: " + execute.body());

                function.apply(execute.isSuccessful() ? execute.body() : null);
            } catch (IOException e) {
                Log.e("FETCH_USERS", "Fail to fetch users", e);
                function.apply(null);
            } finally {
                executorService.shutdown();
            }
        });
    }

}
