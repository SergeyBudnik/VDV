package com.its.vdv.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Builder;

@Builder
@Getter
public class User {
    @NonNull private Long id;
    @NonNull private String name;
    private String avatarPath;
    @NonNull private String position;
    @NonNull private Long followersAmount;
    @NonNull private Long followingAmount;
}
