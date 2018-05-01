package com.its.vdv.data;

import lombok.Getter;
import lombok.experimental.Builder;

@Builder
@Getter
public class Court {
    private long id;
    private String name;
    private double lat = 59.8944444;
    private double lon = 30.2641667;
}
