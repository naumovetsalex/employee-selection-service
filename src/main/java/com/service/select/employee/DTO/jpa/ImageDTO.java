package com.service.select.employee.DTO.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.select.employee.model.jpa.Employee;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;

import static com.service.select.employee.util.RandomUtil.RANDOM;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO extends RepresentationModel<Employee> implements DTOModel, Serializable {

    private Long id;
    @JsonProperty("name")
    @Pattern(regexp = "[a-zA-Z\\s=_,.$#@!|:?><{})(%^&*+-]+")
    private String name;
    @JsonProperty("content")
    private String content;

    @JsonIgnore
    @Serial
    private static final long serialVersionUID = RANDOM.nextLong();

    public ImageDTO(String content) {
        this.content = content;
    }
}
