package com.its.vdv.rest.wrapper;

import android.util.Log;

import com.annimon.stream.Stream;
import com.crashlytics.android.Crashlytics;
import com.its.vdv.data.FeedItem;
import com.its.vdv.data.GeoTag;
import com.its.vdv.data.ProfileInfo;
import com.its.vdv.data.User;
import com.its.vdv.data.UserInfo;
import com.its.vdv.rest.raw.UserRest;
import com.its.vdv.rest.response.GetUserByIdResponse;
import com.its.vdv.service.AuthService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.its.vdv.rest.configuration.RestConfiguration.BACKEND_ROOT;

@EBean
public class ProfileRestWrapper {
    @RestService
    UserRest userRest;

    @Bean
    AuthService authService;

    @Background
    public void getProfileByUserId(Long userId, RestListener<ProfileInfo> listener) {
        try {
            listener.onStart();

            userRest.setHeader("authorization", "Bearer " + authService.getAuthToken().orElseThrow(RuntimeException::new));

            GetUserByIdResponse response = userId == null ?
                    userRest.getYourInfo(BACKEND_ROOT).get(0) :
                    userRest.getUserInfo(BACKEND_ROOT, userId).get(0);

            ProfileInfo profileInfo = ProfileInfo
                    .builder()
                    .user(parseUser(response))
                    .feedItems(parseFeedItems(response))
                    .build();

            listener.onSuccess(profileInfo);
        } catch (Exception e) {
            Crashlytics.logException(e);

            listener.onFailure(e);
        }
    }

    private User parseUser(GetUserByIdResponse response) {
        return User
                .builder()
                .id(Long.parseLong(response.getVdvid()))
                .name(response.getName())
                .avatarPath(response.getAvatar().isEmpty() ? null : response.getAvatar().get(0).getUrl().split("/")[3])
                .position("UX Designer")
                .followingAmount(1L)
                .followersAmount(1L)
                .build();
    }

    private List<FeedItem> parseFeedItems(GetUserByIdResponse response) {
        return Stream
                .of(response.getPost())
                .map(it -> parseFeedItem(
                        response, it
                ))
                .toList();
    }

    private FeedItem parseFeedItem(GetUserByIdResponse response, GetUserByIdResponse.Post post) {
        if (post.getMedia().isEmpty()) {
            throw new RuntimeException("Media is empty for post " + 1L);
        }

        List<String> urls = Stream
                .of(post.getMedia())
                .map(it -> it.getUrl().split("/")[3])
                .toList();

        return FeedItem
                .builder()
                .id(1L) // ToDo
                .user(UserInfo
                        .builder()
                        .id(Long.parseLong(response.getVdvid()))
                        .name(response.getName())
                        .avatarUrl(response.getAvatar().isEmpty() ? null : response.getAvatar().get(0).getUrl().split("/")[3])
                        .build()
                )
                .imagePaths(urls)
                .comments(new ArrayList<>())
                .likes(Collections.emptyList())
                .text("")
                .geoTag(GeoTag.builder().name("").lat(0.0).lon(0.0).build())
                .build();
    }
}
