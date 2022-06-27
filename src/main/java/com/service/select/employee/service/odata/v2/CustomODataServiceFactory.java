package com.service.select.employee.service.odata.v2;


import com.service.select.employee.config.odata.JerseyConfig;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.OnJPAWriteContent;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@Profile("odatav2")
public class CustomODataServiceFactory extends ODataJPAServiceFactory {

    @Autowired
    private OnJPAWriteContent onDBWriteContent;

    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
        ODataJPAContext oDataJPAContext = this.getODataJPAContext();
        ODataContext oDataContext = oDataJPAContext.getODataContext();


        HttpServletRequest request = (HttpServletRequest) oDataContext.getParameter(
                ODataContext.HTTP_SERVLET_REQUEST_OBJECT);
        EntityManager entityManager = (EntityManager) request
                .getAttribute(JerseyConfig.EntityManagerFilter.EM_REQUEST_ATTRIBUTE);
        oDataJPAContext.setEntityManager(entityManager);


        oDataJPAContext.setPersistenceUnitName("default");
        oDataJPAContext.setContainerManaged(true);
        setOnWriteJPAContent(onDBWriteContent);
        return oDataJPAContext;
    }
}
