package com.its.vdv.rest.raw;

import com.its.vdv.rest.mappers.JsonMapper;
import com.its.vdv.rest.request.CreateUserRequest;
import com.its.vdv.rest.response.GetUserByIdResponse;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;

import java.util.List;

@Rest(converters = JsonMapper.class)
public interface UserRest {
    @Post("{rootEndpoint}/user")
    @RequiresHeader("authorization")
    void createUser(
            @Path String rootEndpoint,
            @Body CreateUserRequest request
    );

    @Get("{rootEndpoint}/user/{userId}")
    @RequiresHeader("authorization")
    List<GetUserByIdResponse> getUserInfo(
            @Path String rootEndpoint,
            @Path long userId
    );

    @Get("{rootEndpoint}/user/me")
    @RequiresHeader("authorization")
    List<GetUserByIdResponse> getYourInfo(
            @Path String rootEndpoint
    );

    void setHeader(String name, String value);
}
