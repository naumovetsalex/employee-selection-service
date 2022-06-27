package com.service.select.employee.service.impl;

import com.service.select.employee.DTO.jpa.EmployeeDTO;
import com.service.select.employee.DTO.jpa.ImageDTO;
import com.service.select.employee.mapper.EmployeeMapper;
import com.service.select.employee.mapper.ImageMapper;
import com.service.select.employee.model.jpa.Employee;
import com.service.select.employee.model.jpa.Image;
import com.service.select.employee.repository.jpa.EmployeeRepository;
import com.service.select.employee.service.CRUDService;
import com.service.select.employee.service.util.ComputingPositionMatchService;
import com.service.select.employee.util.Base64Util;
import com.service.select.employee.util.DateTimeUtil;
import com.service.select.employee.util.jpa.specification.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.service.select.employee.util.AppConstants.*;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements CRUDService<EmployeeDTO> {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ImageServiceImpl imageServiceImpl;
    private final ComputingPositionMatchService computingPositionMatchService;
    private final ImageMapper imageMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @SneakyThrows
    public long create(EmployeeDTO employeeDTO) {
        var employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        var content = employeeDTO.getImage();
        var image = imageServiceImpl.createImageFromEncodedContent(content);
        employee.setImages(Set.of(image));
        if (employeeDTO.getCompanyWorkExperience() == null) {
            employee.setCompanyWorkExperience(FORMATTER.parse(UNIX_TIME_START));
        }
        var positionMatch = computingPositionMatchService
                .computePositionMatches(employeeDTO.getCompanyWorkExperience(), employeeDTO.getPosition(), employeeDTO.getSeniorityLevel());
        employee.setPositionMatch(BigDecimal.valueOf(positionMatch));
        var savedEmployee = employeeRepository.save(employee);
        image.setEmployee(savedEmployee);
        imageServiceImpl.saveImage(image);
        return savedEmployee.getId();
    }

    public EmployeeDTO read(long id) {
        var optionalEmployee = employeeRepository.findById(id);
        var employeeDTO = optionalEmployee
                .map(employeeMapper::employeeToEmployeeDTO).orElse(null);
        var images = optionalEmployee.get().getImages();
        if ((images == null) && images.isEmpty()) {
            employeeDTO.setImage(null);
        }
        var imageDTO = imageServiceImpl.read(employeeDTO.getId());
        var positionMatch = optionalEmployee.get().getPositionMatch();
        employeeDTO.setPositionMatch((int) Math.round(positionMatch.doubleValue()));
        employeeDTO.setImage(imageDTO);
        return employeeDTO;
    }

    @SneakyThrows
    public void update(EmployeeDTO employeeDTO, long id) {
        var imageDTO = employeeDTO.getImage();
        var optionalOldEmployee = employeeRepository.findById(id);
        if (optionalOldEmployee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var oldEmployee = optionalOldEmployee.get();
        BigDecimal oldPositionMatch = oldEmployee.getPositionMatch();
        employeeMapper.updateEmployeeFromEmployeeDTO(employeeDTO, oldEmployee);
        if (employeeDTO.getCompanyWorkExperience() == null) {
            oldEmployee.setCompanyWorkExperience(FORMATTER.parse(UNIX_TIME_START));
        }
        var content = imageDTO.getContent();
        if (imageDTO != null) {
            var decodedContent = Base64Util.DECODER.decode(content);
            if (!imageServiceImpl.existsByContent(decodedContent)) {
                if ((oldEmployee.getImages() != null) && !oldEmployee.getImages().stream()
                        .map(Image::getContent).toList().contains(decodedContent)) {
                    var image = imageMapper.imageDTOtoImage(imageDTO);
                    var images = optionalOldEmployee.get().getImages();
                    images.add(image);
                    oldEmployee.setImages(images);
                }
            } else {
                oldEmployee.setImages(optionalOldEmployee.get().getImages());
            }
        }
        if (employeeDTO.getPositionMatch() == MIN_VALUE_POSITION_MATCH) {
            oldEmployee.setPositionMatch(oldPositionMatch);
        }
        oldEmployee.setId(id);
        employeeRepository.save(oldEmployee);
    }

    public void delete(long id) {
        var employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        employeeRepository.deleteById(id);
    }

    public Long updateImageEmployee(ImageDTO imageDTO, long employeeId, long imageId) {
        var optionalEmployee = employeeRepository.findById(employeeId);
        if (employeeRepository.existsById(employeeId)) {
            var employee = optionalEmployee.get();
            if (employee.getImages() != null) {
                if (imageServiceImpl.existsByIdAndEmployeeId(imageId, employeeId)) {
                    imageServiceImpl.update(imageDTO, imageId);
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                return null;
            } else {
                return imageServiceImpl.create(imageDTO);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Iterable<EmployeeDTO> getEmployees(Map<String, String> params) {
        if (params.containsKey(OFFSET_PARAM_PAGINATION)
                || params.containsKey(SORT_PARAM_PAGINATION)
                || params.containsKey(LIMIT_PARAM_PAGINATION)) {
            var offsetValueString = params.get(OFFSET_PARAM_PAGINATION);
            var limitValueString = params.get(LIMIT_PARAM_PAGINATION);
            var sortValueString = params.get(SORT_PARAM_PAGINATION);
            int offset = Integer.parseInt(offsetValueString != null ? offsetValueString : DEFAULT_PAGE_NUMBER);
            int limit = Integer.parseInt(limitValueString != null ? limitValueString : DEFAULT_PAGE_SIZE);
            var sort = sortValueString != null ? sortValueString : EMPTY_STRING;
            return getPageEmployees(offset, limit, sort);
        } else if (params.containsKey(SEARCH_PARAM)) {
            var search = params.get(SEARCH_PARAM);
            return findAllEmployeesBySearchCriteria(search);
        } else if (params.containsKey(FIELDS_PARAM)) {
            var fields = params.get(FIELDS_PARAM);
            return findAllEmployeesByFields(fields != null ? fields : EMPTY_STRING);
        } else {
            return getAllEmployees();
        }
    }

    public List<EmployeeDTO> getAllEmployeesBySearchValue(String searchValue) {
        var employees = employeeRepository.findBySearchValueContains(searchValue);
        return mapToListEmployeesDTOFromListEmployees(employees);
    }

    public long addImageToEmployeeProfile(ImageDTO imageDTO, long employeeId) {
        long savedImageId = imageServiceImpl.create(imageDTO);
        var image = imageServiceImpl.findImageById(savedImageId);
        var employee = getEmployeeById(employeeId);
        image.setEmployee(employee);
        imageServiceImpl.saveImage(image);
        return savedImageId;
    }

    public void deleteImageFromEmployeeProfile(long employeeId, long imageId) {
        var employee = getEmployeeById(employeeId);
        if (employee.getImages().stream().map(Image::getId).toList().contains(imageId)) {
            imageServiceImpl.delete(imageId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public ImageDTO getImageFromEmployeeProfile(long employeeId, long imageId) {
        var employee = getEmployeeById(employeeId);
        var image = employee.getImages()
                .stream()
                .filter(img -> img.getId() == imageId)
                .findFirst()
                .get();
        return imageServiceImpl.mapImageToImageDTO(image);
    }

    public ImageDTO getAvatarFromEmployeeProfile(long employeeId) {
        var employee = getEmployeeById(employeeId);
        var image = imageServiceImpl.findImageById(employee.getAvatarId());
        return imageServiceImpl.mapImageToImageDTO(image);
    }

    public List<ImageDTO> getAllImagesFromEmployeeProfile(long employeeId) {
        var employee = getEmployeeById(employeeId);
        return imageServiceImpl.mapImagesToImageDTOs(employee
                .getImages().stream().toList());
    }

    private List<EmployeeDTO> findAllEmployeesByFields(String fieldsParam) {
        String[] fieldsInRequest = fieldsParam.split(COMMA_SYMBOL);
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (EmployeeDTO employeeDTO : getAllEmployees()) {
            EmployeeDTO newEmployeeDTO = new EmployeeDTO();
            for (String fieldInRequest : fieldsInRequest) {
                for (Field field : employeeDTO.getClass().getDeclaredFields()) {
                    if (field.getName().compareToIgnoreCase(fieldInRequest) == 0) {
                        setValueToConcreteField(employeeDTO, newEmployeeDTO, field);
                        employeeDTOs.add(newEmployeeDTO);
                    }
                }
            }
        }
        return employeeDTOs;
    }

    @SneakyThrows
    private EmployeeDTO setValueToConcreteField(EmployeeDTO fromEmployeeDTO, EmployeeDTO toEmployeeDTO, Field field) {
        Field fieldEmployeeDTO = toEmployeeDTO.getClass().getDeclaredField(field.getName());
        fieldEmployeeDTO.setAccessible(true);
        field.setAccessible(true);
        Object obj = field.get(fromEmployeeDTO);
        fieldEmployeeDTO.set(toEmployeeDTO, obj);
        return toEmployeeDTO;
    }

    private List<EmployeeDTO> getAllEmployees() {
        var employees = employeeRepository.findAll();
        return mapToListEmployeesDTOFromListEmployees(employees);
    }

    private List<EmployeeDTO> findAllEmployeesBySearchCriteria(String search) {
        List<SearchCriteria> params = new ArrayList<>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return mapToListEmployeesDTOFromListEmployees(findAllByCriteriaSearch(params));
    }

    private Employee getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    private Page<EmployeeDTO> getPageEmployees(int offset, int limit, String sort) {
        Sort sortObj = Sort.unsorted();
        String[] params = sort.split(COMMA_SYMBOL);
        for (Field field : Employee.class.getDeclaredFields()) {
            var fieldName = field.getName();
            var patternDescending = Pattern.compile(MINUS_SYMBOL + fieldName);
            var patternAscending = Pattern.compile(SPACE_SYMBOL + fieldName);
            for (String param : params) {
                var matcherAscending = patternAscending.matcher(param);
                var matcherDescending = patternDescending.matcher(param);
                if (matcherAscending.matches()) {
                    sortObj = sortObj.and(Sort.by(param.replace(SPACE_SYMBOL, "")).ascending());
                } else if (matcherDescending.matches()) {
                    sortObj = sortObj.and(Sort.by(param.replace(MINUS_SYMBOL, "")).descending());
                }
            }
        }
        Pageable pageable = PageRequest.of(offset, limit, sortObj);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return mapToPageEmployeeDTOFromPageEmployee(employeePage, pageable);
    }

    private List<Employee> findAllByCriteriaSearch(List<SearchCriteria> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        final Root<Employee> r = query.from(Employee.class);
        Predicate predicate = builder.conjunction();
        EmployeeSearchCriteriaConsumer searchConsumer = new EmployeeSearchCriteriaConsumer(predicate, builder, r);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

    private List<EmployeeDTO> mapToListEmployeesDTOFromListEmployees(List<Employee> employees) {
        if (!employees.isEmpty()) {
            return employees.stream().map(employee -> {
                var employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);
                if (!employee.getImages().isEmpty()) {
                    var image = imageServiceImpl.findImageById(employee.getAvatarId());
                    var imageDTO = imageMapper.imageToImageDTO(image);
                    if (image != null) {
                        if (image.getContent() != null) {
                            var content = image.getContent();
                            imageDTO.setContent(Base64Util.ENCODER.encodeToString(content));
                        } else {
                            imageDTO.setContent(null);
                        }
                        if (image.getName() == null) {
                            imageDTO.setName("image_content_" + DateTimeUtil.getUTCDateFormattedToISO());
                        }
                    }
                    employeeDTO.setImage(imageDTO);
                } else {
                    employeeDTO.setImage(null);
                }
                var positionMatch = employee.getPositionMatch();
                employeeDTO.setPositionMatch((int) Math.round(positionMatch.doubleValue()));
                return employeeDTO;
            }).toList();
        } else {
            return new ArrayList<>();
        }
    }

    private Page<EmployeeDTO> mapToPageEmployeeDTOFromPageEmployee(Page<Employee> employeePage, Pageable pageable) {
        var employees = employeePage.getContent();
        var start = (int) pageable.getOffset();
        var end = Math.min((start + pageable.getPageSize()), employees.size());
        var employeesDTO = mapToListEmployeesDTOFromListEmployees(employees);
        return new PageImpl<>(employeesDTO.subList(start, end), pageable, employees.size());
    }
}
