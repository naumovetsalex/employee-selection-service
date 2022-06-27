package com.service.select.employee.mapper;

import com.service.select.employee.DTO.jpa.ImageDTO;
import com.service.select.employee.model.jpa.Image;
import com.service.select.employee.service.impl.EmployeeServiceImpl;
import com.service.select.employee.service.impl.ImageServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EmployeeServiceImpl.class, ImageServiceImpl.class})
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

//    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "content", ignore = true)
    Image imageDTOtoImage(ImageDTO imageDTO);

//    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "content", ignore = true)
    ImageDTO imageToImageDTO(Image image);
}
