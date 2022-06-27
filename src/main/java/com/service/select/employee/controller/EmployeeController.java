package com.service.select.employee.controller;

import com.service.select.employee.DTO.jpa.EmployeeDTO;
import com.service.select.employee.DTO.jpa.ImageDTO;
import com.service.select.employee.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.service.select.employee.util.AppConstants.FIELDS_PARAM;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@RestController
@RequestMapping("/v1/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public long addNewEmployee(@RequestBody(required = false) @Validated EmployeeDTO employeeDTO) {
        return employeeServiceImpl.create(employeeDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Link link = linkTo(methodOn(EmployeeController.class)
                .getEmployee(employeeId)).withSelfRel();
        EmployeeDTO employeeDTO = employeeServiceImpl.read(employeeId);
        employeeDTO.add(link);
        return employeeDTO;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{employeeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateEmployee(@RequestBody(required = false) @Validated EmployeeDTO employeeDTO,
                               @PathVariable long employeeId) {
        employeeServiceImpl.update(employeeDTO, employeeId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{employeeId}")
    public void deleteEmployee(@PathVariable long employeeId) {
        employeeServiceImpl.delete(employeeId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<EmployeeDTO> getEmployees(@RequestParam Map<String, String> params) {
        Iterable<EmployeeDTO> employeeDTOs = employeeServiceImpl.getEmployees(params);
        if(!params.containsKey(FIELDS_PARAM)) {
            employeeDTOs.forEach(employeeDTO -> {
                Link link = linkTo(methodOn(EmployeeController.class)
                        .getEmployees(params)).withSelfRel();
                employeeDTO.add(link);
            });
        }
        return employeeServiceImpl.getEmployees(params);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDTO> searchEmployee(@RequestParam(value = "q") String q) {
        List<EmployeeDTO> employeeDTOs = employeeServiceImpl.getAllEmployeesBySearchValue(q);
        employeeDTOs.forEach(employeeDTO -> {
            Link link = linkTo(methodOn(EmployeeController.class)
                    .searchEmployee(q)).withSelfRel();
            employeeDTO.add(link);
        });
        return employeeDTOs;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{employeeId}/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public long addNewImage(@RequestBody ImageDTO imageDTO,
                            @PathVariable long employeeId) {
        return employeeServiceImpl.addImageToEmployeeProfile(imageDTO, employeeId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{employeeId}/image/{imageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ImageDTO getImageEmployee(@PathVariable long employeeId,
                             @PathVariable long imageId) {
        ImageDTO imageDTO = employeeServiceImpl.getImageFromEmployeeProfile(employeeId, imageId);
        Link link = linkTo(methodOn(EmployeeController.class)
                .getImageEmployee(employeeId, imageId)).withSelfRel();
        imageDTO.add(link);
        return imageDTO;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{employeeId}/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public ImageDTO getAvatarEmployee(@PathVariable long employeeId){
        ImageDTO imageDTO = employeeServiceImpl.getAvatarFromEmployeeProfile(employeeId);
        Link link = linkTo(methodOn(EmployeeController.class)
                .getAvatarEmployee(employeeId)).withSelfRel();
        imageDTO.add(link);
        return imageDTO;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(path = "/{employeeId}/image/{imageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long updateImageEmployee(@RequestBody ImageDTO imageDTO,
                                    @PathVariable long employeeId,
                                    @PathVariable long imageId) {
        return employeeServiceImpl.updateImageEmployee(imageDTO, employeeId, imageId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{employeeId}/image/{imageId}")
    public void deleteImageEmployee(@PathVariable long employeeId,
                            @PathVariable long imageId) {
        employeeServiceImpl.deleteImageFromEmployeeProfile(employeeId, imageId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{employeeId}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ImageDTO> getAllImagesEmployee(@PathVariable long employeeId) {
        List<ImageDTO> imageDTOs = employeeServiceImpl.getAllImagesFromEmployeeProfile(employeeId);
        imageDTOs.forEach(imageDTO -> {
            Link link = linkTo(methodOn(EmployeeController.class)
                    .getAllImagesEmployee(employeeId)).withSelfRel();
            imageDTO.add(link);
        });
        return imageDTOs;
    }
}
