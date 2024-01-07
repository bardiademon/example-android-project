package com.bardiademon.example.data.repository.http;

import com.bardiademon.example.data.dto.ResponseDto;
import com.bardiademon.example.data.dto.UsersDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface HttpUsersService {

    @GET("/api/users")
    Call<ResponseDto<List<UsersDto>>> fetchAllUsers(@Query("page") int page);
}
