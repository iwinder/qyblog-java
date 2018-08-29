package com.windcoder.qycms.utils;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Type;
import java.util.List;

public class ModelMapperUtils {
    public static final ModelMapper modelMapper = new ModelMapper();

    public static <D> D map(Object source, Class<D> destinationType)  {
        return modelMapper.map(source,destinationType);
    }

    public static <D> D map(Object source, Type destinationType) {
        return modelMapper.map(source,destinationType);
    }

    public static <D> Page<D> map(Page<?> source, Type type, Pageable pageable) {
        List<D> list = map(source.getContent(), type, getTypeMappingName());

        return new PageImpl<D>(list, pageable, source.getTotalElements());
    }
}
