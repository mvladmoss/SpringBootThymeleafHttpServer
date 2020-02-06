package com.bsuir.second.service.converter;

import org.springframework.lang.Nullable;

public interface Converter<S,T> {
    @Nullable
    T convert(S var1);

    @Nullable
    S unconvert(T var1);
}

