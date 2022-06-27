package com.service.select.employee.DTO.jpa;

import com.fasterxml.jackson.annotation.*;
import com.service.select.employee.model.jpa.Employee;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import static com.service.select.employee.util.RandomUtil.RANDOM;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO extends RepresentationModel<Employee> implements DTOModel, Serializable {

    private Long id;
    @JsonProperty("name")
    @Pattern(regexp = "[a-zA-Z\\s,.]+")
    private String name;
    @JsonProperty("position")
    @Pattern(regexp = "[a-zA-Z\\d\\s,.]+")
    private String position;
    @JsonProperty("department")
    @Pattern(regexp = "[a-zA-Z\\d\\s,.]+")
    private String department;
    @JsonProperty("seniorityLevel")
    @Pattern(regexp = "[a-zA-Z\\d\\s,.]+")
    private String seniorityLevel;
    @JsonProperty("location")
    @Pattern(regexp = "[a-zA-Z\\s,.]+")
    private String location;
    @JsonProperty("companyWorkExperience")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate companyWorkExperience;
    @JsonProperty("positionMatch")
    @Min(value = 0)
    @Max(value = 100)
    private Integer positionMatch;
    @JsonProperty("image")
    private ImageDTO image;

    @JsonIgnore
    @Serial
    private static final long serialVersionUID = RANDOM.nextLong();
}
