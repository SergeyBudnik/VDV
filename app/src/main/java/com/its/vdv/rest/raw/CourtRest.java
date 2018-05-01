package com.its.vdv.rest.raw;

import com.its.vdv.rest.configuration.RestConfiguration;
import com.its.vdv.rest.mappers.JsonMapper;
import com.its.vdv.rest.response.GetAllCourtsResponse;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;

import java.util.List;


@Rest(rootUrl = RestConfiguration.BACKEND_ROOT, converters = JsonMapper.class)
public interface CourtRest {
    @Get("/court/all")
    @RequiresHeader("authorization")
    List<GetAllCourtsResponse> getAllCourts();

    void setHeader(String name, String value);
}
