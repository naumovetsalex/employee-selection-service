package com.service.select.employee.controller.odata;

import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.security.BasicCredentials;
import com.service.select.employee.vdm.namespaces.bookshop.Books;
import com.service.select.employee.vdm.services.BookshopService;
import com.service.select.employee.vdm.services.DefaultBookshopService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ODataTestController {

    private final BasicCredentials basicCredentials = new BasicCredentials("alice", "");
    private final HttpDestination destination = DefaultHttpDestination.builder("http://localhost:4004").basicCredentials(basicCredentials).build();
    private final BookshopService service = new DefaultBookshopService().withServicePath("/admin");

    @GetMapping(value = "/v2/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getOdata() {
        List<Books> books = service.getAllBooks().execute(destination);
        books.forEach(System.out::println);
    }
}
