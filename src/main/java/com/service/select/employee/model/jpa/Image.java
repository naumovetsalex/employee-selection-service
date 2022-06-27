package com.service.select.employee.model.jpa;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.service.select.employee.model.jpa.util.BinaryTypeConverter;
import com.service.select.employee.service.util.serialization.xml.BinaryTypeAdapter;
import com.service.select.employee.service.util.serialization.xml.LocalDateAdapter;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;

import static com.service.select.employee.util.RandomUtil.RANDOM;

@JacksonXmlRootElement(localName = "image")
@Table(name = "images")
@Entity(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "content"})
@ToString(exclude = {"employee"})
public class Image extends RepresentationModel<Image> implements JPAModel, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Lob
//    @XmlElement(required = true, type = String.class)
//    @XmlJavaTypeAdapter(BinaryTypeAdapter.class)
//    @XmlSchemaType(name = "base64Binary")
//    @JacksonXmlProperty(localName = "content")
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "content")
//    @Convert(converter = BinaryTypeConverter.class)
    private byte[] content;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Serial
    private static final long serialVersionUID = RANDOM.nextLong();

    public Image(byte[] content, String name) {
        this.content = content;
        this.name = name;
    }
}
