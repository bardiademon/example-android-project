package com.bardiademon.example.data.dto;

import com.google.gson.annotations.SerializedName;

public record ResponseDto<T>(
        Integer page,
        @SerializedName("per_page") Integer perPage,
        @SerializedName("total_pages") Integer totalPages,
        T data
) {
}
