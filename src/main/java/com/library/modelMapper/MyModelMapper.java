package com.library.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyModelMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <O, D> D convertValue(O origin, Class<D> destination) {

        return modelMapper.map(origin, destination);
    }

    public static <O, D> List<D> convertList(List<O> origins, Class<D> destination) {

        List<D> destinations = new ArrayList<>();

        origins.stream().map(origin -> convertValue(origin, destination)).forEach(destinations::add);
        return destinations;
    }
}
