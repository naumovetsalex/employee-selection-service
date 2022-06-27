package com.service.select.employee.repository.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Repository
public class FileSystemRepository {

    @Value("${spring.file-system.path-to-load}")
    private String pathToLoad;

    public String save(byte[] content, String imageName) throws Exception {
        var loc = new Locale("en", "UK");
        var dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);
        var newFile = Paths.get(pathToLoad + dateFormat.format(new Date()) + "-" + imageName);
//        var newFile = Paths.get(pathToLoad + getUTCDateFormattedToISO() + "-" + imageName);
        Files.createDirectories(newFile.getParent());
        Files.write(newFile, content);
        return newFile.toAbsolutePath().toString();
    }

    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            throw new RuntimeException("File not found");
        }
    }

    public void delete(String location) {
        try {
            Files.delete(Paths.get(location));
        } catch (Exception e) {
            throw new RuntimeException("File not found");
        }
    }
}
