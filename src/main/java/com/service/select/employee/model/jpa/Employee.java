package com.service.select.employee.model.jpa;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.service.select.employee.service.util.serialization.xml.LocalDateAdapter;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static com.service.select.employee.util.RandomUtil.RANDOM;

@JacksonXmlRootElement(localName = "employee")
@Table(name = "employees")
@Entity(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"image"})
public class Employee extends RepresentationModel<Employee> implements JPAModel, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Pattern(regexp = "[a-zA-Z\\s,.]+")
    @Column(name = "name")
    private String name;
    @Pattern(regexp = "[a-zA-Z\\d\\s,.]+")
    @Column(name = "position")
    private String position;
    @Pattern(regexp = "[a-zA-Z\\d\\s,.]+")
    @Column(name = "department")
    private String department;
    @Pattern(regexp = "[a-zA-Z\\d\\s,.]+")
    @Column(name = "seniority_level")
    private String seniorityLevel;
    @Pattern(regexp = "[a-zA-Z\\s,.]+")
    @Column(name = "location")
    private String location;
    @XmlElement(name = "companyWorkExperience", required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @Column(name = "company_work_experience")
    private Date companyWorkExperience;
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "position_match")
    private BigDecimal positionMatch;
    @Column(name = "avatar_id")
    private Long avatarId;

    @XmlElement(name="images")
//    @JacksonXmlElementWrapper(useWrapping = false)
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Image> images;

    @Serial
    private static final long serialVersionUID = RANDOM.nextLong();
}
