package com.service.select.employee.service.impl;

import com.service.select.employee.DTO.jpa.ImageDTO;
import com.service.select.employee.mapper.ImageMapper;
import com.service.select.employee.model.jpa.Image;
import com.service.select.employee.repository.file.FileSystemRepository;
import com.service.select.employee.repository.jpa.ImageRepository;
import com.service.select.employee.service.CRUDService;
import com.service.select.employee.util.Base64Util;
import com.service.select.employee.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements CRUDService<ImageDTO> {

    private static final boolean IS_AVATAR = true;

    private final ImageRepository imageRepository;
    private final FileSystemRepository fileSystemRepository;
    private final ImageMapper imageMapper;

    @SneakyThrows
    public long create(ImageDTO imageDTO) {
        var image = imageMapper.imageDTOtoImage(imageDTO);
        var optionalContent = imageDTO.getContent();
        if (optionalContent != null) {
            image.setContent(Base64Util.DECODER.decode(optionalContent));
            if (image.getName() == null) {
                image.setName("image_content_" + DateTimeUtil.getUTCDateFormattedToISO());
            }
        }
        var savedImage = imageRepository.save(image);
        return savedImage.getId();
    }

    @SneakyThrows
    public ImageDTO read(long id) {
        var optionalImage = imageRepository.findById(id);
        var image = optionalImage
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapImageToImageDTO(image);
    }

    @SneakyThrows
    public void update(ImageDTO imageDTO, long id) {
        var optionalImage = imageRepository.findById(id);
        optionalImage
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var image = imageMapper.imageDTOtoImage(imageDTO);
        var optionalContent = imageDTO.getContent();
        if (optionalContent != null) {
            image.setContent(Base64Util.DECODER.decode(optionalContent));
        } else {
            image.setContent(optionalImage.get().getContent());
        }
        if (imageDTO.getName() == null) {
            image.setName(optionalImage.get().getName());
        }
        image.setId(id);
        imageRepository.save(image);
    }

    public void delete(long id) {
        var optionalImage = imageRepository.findById(id);
        optionalImage
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        imageRepository.deleteById(id);
    }

    public List<ImageDTO> getAllImages() {
        var images = imageRepository.findAll();
        return mapImagesToImageDTOs(images);
    }

    ImageDTO mapImageToImageDTO(Image image) {
        var imageDTO = imageMapper.imageToImageDTO(image);
        var content = image.getContent();
        imageDTO.setContent(content != null ?
                Base64Util.ENCODER.encodeToString(content) : null);
        return imageDTO;
    }

    List<ImageDTO> mapImagesToImageDTOs(List<Image> images) {
        return images.stream().map(image -> {
                    ImageDTO imageDTO = imageMapper.imageToImageDTO(image);
                    if (image.getContent() != null) {
                        var content = image.getContent();
                        imageDTO.setContent(Base64Util.ENCODER.encodeToString(content));
                    } else {
                        imageDTO.setContent(null);
                    }
                    return imageDTO;
                })
                .toList();
    }

    Image createImageFromEncodedContent(ImageDTO imageDTO) {
        var name = "image_content_" + DateTimeUtil.getUTCDateFormattedToISO();
        byte[] content;
        if (imageDTO != null) {
            var optionalContent = imageDTO.getContent();
            if (optionalContent != null) {
                if (imageDTO.getName() != null) {
                    name = imageDTO.getName();
                }
                content = Base64Util.DECODER.decode(optionalContent);
            } else {
                return null;
            }
            return new Image(content, name);
        } else {
            return null;
        }
    }

    boolean existsByContent(byte[] content) {
        return imageRepository.existsByContent(content);
    }

    boolean existsByIdAndEmployeeId(long imageId, long employeeId) {
        return imageRepository.existsByIdAndEmployeeId(imageId, employeeId);
    }

    Image findImageById(long id) {
        return imageRepository.findById(id).orElse(null);
    }

    void saveImage(Image image) {
        imageRepository.save(image);
    }
}
