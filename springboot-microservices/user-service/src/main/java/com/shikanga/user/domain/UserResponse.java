package com.shikanga.user.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.shikanga.user.entity.User;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = UserResponse.UserResponseBuilder.class)
public class UserResponse {

    User user;
    Department department;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserResponseBuilder {
    }
}
