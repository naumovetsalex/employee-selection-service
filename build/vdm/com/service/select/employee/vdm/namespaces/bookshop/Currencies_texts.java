
/*
 * Generated by OData VDM code generator of SAP Cloud SDK in version 3.68.0
 */

package com.service.select.employee.vdm.namespaces.bookshop;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Maps;
import com.google.gson.annotations.JsonAdapter;
import com.sap.cloud.sdk.datamodel.odata.client.request.ODataEntityKey;
import com.sap.cloud.sdk.datamodel.odatav4.core.SimpleProperty;
import com.sap.cloud.sdk.datamodel.odatav4.core.VdmEntity;
import com.sap.cloud.sdk.datamodel.odatav4.core.VdmEntitySet;
import com.sap.cloud.sdk.result.ElementName;
import com.service.select.employee.vdm.services.BookshopService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * <p>Original entity name from the Odata EDM: <b>Currencies_texts</b></p>
 * 
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true, callSuper = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = true)
@JsonAdapter(com.sap.cloud.sdk.datamodel.odatav4.adapter.GsonVdmAdapterFactory.class)
@JsonSerialize(using = com.sap.cloud.sdk.datamodel.odatav4.adapter.JacksonVdmObjectSerializer.class)
@JsonDeserialize(using = com.sap.cloud.sdk.datamodel.odatav4.adapter.JacksonVdmObjectDeserializer.class)
public class Currencies_texts
    extends VdmEntity<Currencies_texts>
    implements VdmEntitySet
{

    @Getter
    private final java.lang.String odataType = "AdminService.Currencies_texts";
    /**
     * Selector for all available fields of Currencies_texts.
     * 
     */
    public final static SimpleProperty<Currencies_texts> ALL_FIELDS = all();
    /**
     * (Key Field) Constraints: Not nullable, Maximum length: 14 <p>Original property name from the Odata EDM: <b>locale</b></p>
     * 
     * @return
     *     The locale contained in this {@link VdmEntity}.
     */
    @Nullable
    @ElementName("locale")
    private java.lang.String locale;
    public final static SimpleProperty.String<Currencies_texts> LOCALE = new SimpleProperty.String<Currencies_texts>(Currencies_texts.class, "locale");
    /**
     * Constraints: Nullable, Maximum length: 255 <p>Original property name from the Odata EDM: <b>name</b></p>
     * 
     * @return
     *     Name
     */
    @Nullable
    @ElementName("name")
    private java.lang.String name;
    public final static SimpleProperty.String<Currencies_texts> NAME = new SimpleProperty.String<Currencies_texts>(Currencies_texts.class, "name");
    /**
     * Constraints: Nullable, Maximum length: 1000 <p>Original property name from the Odata EDM: <b>descr</b></p>
     * 
     * @return
     *     Description
     */
    @Nullable
    @ElementName("descr")
    private java.lang.String descr;
    public final static SimpleProperty.String<Currencies_texts> DESCR = new SimpleProperty.String<Currencies_texts>(Currencies_texts.class, "descr");
    /**
     * (Key Field) Constraints: Not nullable, Maximum length: 3 <p>Original property name from the Odata EDM: <b>code</b></p>
     * 
     * @return
     *     Currency Code
     */
    @Nullable
    @ElementName("code")
    private java.lang.String code;
    public final static SimpleProperty.String<Currencies_texts> CODE = new SimpleProperty.String<Currencies_texts>(Currencies_texts.class, "code");

    @Nonnull
    @Override
    public Class<Currencies_texts> getType() {
        return Currencies_texts.class;
    }

    /**
     * (Key Field) Constraints: Not nullable, Maximum length: 14 <p>Original property name from the Odata EDM: <b>locale</b></p>
     * 
     * @param locale
     *     The locale to set.
     */
    public void setLocale(
        @Nullable
        final java.lang.String locale) {
        rememberChangedField("locale", this.locale);
        this.locale = locale;
    }

    /**
     * Constraints: Nullable, Maximum length: 255 <p>Original property name from the Odata EDM: <b>name</b></p>
     * 
     * @param name
     *     Name
     */
    public void setName(
        @Nullable
        final java.lang.String name) {
        rememberChangedField("name", this.name);
        this.name = name;
    }

    /**
     * Constraints: Nullable, Maximum length: 1000 <p>Original property name from the Odata EDM: <b>descr</b></p>
     * 
     * @param descr
     *     Description
     */
    public void setDescr(
        @Nullable
        final java.lang.String descr) {
        rememberChangedField("descr", this.descr);
        this.descr = descr;
    }

    /**
     * (Key Field) Constraints: Not nullable, Maximum length: 3 <p>Original property name from the Odata EDM: <b>code</b></p>
     * 
     * @param code
     *     Currency Code
     */
    public void setCode(
        @Nullable
        final java.lang.String code) {
        rememberChangedField("code", this.code);
        this.code = code;
    }

    @Override
    protected java.lang.String getEntityCollection() {
        return "Currencies_texts";
    }

    @Nonnull
    @Override
    protected ODataEntityKey getKey() {
        final ODataEntityKey entityKey = super.getKey();
        entityKey.addKeyProperty("locale", getLocale());
        entityKey.addKeyProperty("code", getCode());
        return entityKey;
    }

    @Nonnull
    @Override
    protected Map<java.lang.String, Object> toMapOfFields() {
        final Map<java.lang.String, Object> values = super.toMapOfFields();
        values.put("locale", getLocale());
        values.put("name", getName());
        values.put("descr", getDescr());
        values.put("code", getCode());
        return values;
    }

    @Override
    protected void fromMap(final Map<java.lang.String, Object> inputValues) {
        final Map<java.lang.String, Object> values = Maps.newHashMap(inputValues);
        // simple properties
        {
            if (values.containsKey("locale")) {
                final Object value = values.remove("locale");
                if ((value == null)||(!value.equals(getLocale()))) {
                    setLocale(((java.lang.String) value));
                }
            }
            if (values.containsKey("name")) {
                final Object value = values.remove("name");
                if ((value == null)||(!value.equals(getName()))) {
                    setName(((java.lang.String) value));
                }
            }
            if (values.containsKey("descr")) {
                final Object value = values.remove("descr");
                if ((value == null)||(!value.equals(getDescr()))) {
                    setDescr(((java.lang.String) value));
                }
            }
            if (values.containsKey("code")) {
                final Object value = values.remove("code");
                if ((value == null)||(!value.equals(getCode()))) {
                    setCode(((java.lang.String) value));
                }
            }
        }
        // structured properties
        {
        }
        // navigation properties
        {
        }
        super.fromMap(values);
    }

    @Override
    protected java.lang.String getDefaultServicePath() {
        return BookshopService.DEFAULT_SERVICE_PATH;
    }

}
