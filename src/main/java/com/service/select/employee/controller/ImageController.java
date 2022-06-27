package com.service.select.employee.controller;

import com.service.select.employee.DTO.jpa.ImageDTO;
import com.service.select.employee.service.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageServiceImpl imageServiceImpl;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public long addNewImage(@RequestBody ImageDTO imageDTO) {
        return imageServiceImpl.create(imageDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ImageDTO getImage(@PathVariable long id) {
        return imageServiceImpl.read(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateImage(@RequestBody ImageDTO imageDTO,
                            @PathVariable long id) {
        imageServiceImpl.update(imageDTO, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteImage(@PathVariable long id) {
        imageServiceImpl.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ImageDTO> getAllImages() {
        return imageServiceImpl.getAllImages();
    }
}
