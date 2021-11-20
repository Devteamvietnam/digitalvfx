package com.devteam.module.base.convert;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BaseConverter {

    public static Converter<String, UUID> uuidConverter = ctx -> ctx.getSource()!=null? UUID.fromString(ctx.getSource()) : null;
    protected ModelMapper mapper= new ModelMapper();

    public <T> T map(Object src, Class<T> className) {
        return mapId(src, mapper.map(src, className));
    }

    @SuppressWarnings("unchecked")
    protected <T> T mapId(Object src, Object dest) {
        return (T) dest;
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }
}
