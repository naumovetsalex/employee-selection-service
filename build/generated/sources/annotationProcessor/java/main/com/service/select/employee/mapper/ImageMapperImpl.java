package com.service.select.employee.mapper;

import com.service.select.employee.DTO.jpa.ImageDTO;
import com.service.select.employee.model.jpa.Image;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-31T19:36:22+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public Image imageDTOtoImage(ImageDTO imageDTO) {
        if ( imageDTO == null ) {
            return null;
        }

        Image image = new Image();

        if ( imageDTO.getId() != null ) {
            image.setId( imageDTO.getId() );
        }
        image.setName( imageDTO.getName() );

        return image;
    }

    @Override
    public ImageDTO imageToImageDTO(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageDTO imageDTO = new ImageDTO();

        imageDTO.setId( image.getId() );
        imageDTO.setName( image.getName() );

        return imageDTO;
    }
}
