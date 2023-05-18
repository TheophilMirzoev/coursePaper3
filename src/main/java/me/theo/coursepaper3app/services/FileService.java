package me.theo.coursepaper3app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class FileService {
    @Value("src/main/resources")
    private String filePath;

    @Value("socks.json")
    private String fileName;

    public boolean saveToFile(String json) {
        cleanDataFile();
        try {
            Files.writeString(Path.of(filePath, fileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String readFromFile() {
        try {
            return Files.readString(Path.of(filePath, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean cleanDataFile() {
        try {
            Path path = Path.of(filePath, fileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
