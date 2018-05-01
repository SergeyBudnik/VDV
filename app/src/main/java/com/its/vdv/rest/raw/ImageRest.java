package com.its.vdv.rest.raw;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;

@Rest(converters = {ByteArrayHttpMessageConverter.class})
public interface ImageRest {
    @Get("http://vdv.itsociety.su:4201/vdv/images/{imageLink}")
    byte [] getImage(
            @Path String imageLink
    );
}
