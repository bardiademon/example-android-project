package com.bardiademon.example.data.dto;

import com.google.gson.annotations.SerializedName;

public record UsersDto(
        int id,
        String email,
        @SerializedName("first_name") String firstName,
        @SerializedName("last_name") String lastName,
        @SerializedName("avatar") String uriAvatar
) {
}
