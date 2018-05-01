package com.its.vdv.rest.raw;

import com.its.vdv.rest.configuration.RestConfiguration;
import com.its.vdv.rest.mappers.JsonMapper;
import com.its.vdv.rest.request.CreatePostRequest;
import com.its.vdv.rest.response.GetAllPostsResponse;
import com.its.vdv.rest.response.GetPostByIdResponse;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;

import java.util.List;

@Rest(rootUrl = RestConfiguration.BACKEND_ROOT, converters = JsonMapper.class)
public interface PostRest {
    @Get("/following/post")
    @RequiresHeader("authorization")
    GetAllPostsResponse getAllPosts();

    @Get("/post/{postId}")
    @RequiresHeader("authorization")
    GetPostByIdResponse getPost(@Path long postId);

    @Post("/post")
    @RequiresHeader("authorization")
    void createPost(@Body CreatePostRequest request);

    void setHeader(String name, String value);
}
