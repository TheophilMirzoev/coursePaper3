package me.theo.coursepaper3app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.theo.coursepaper3app.exception.InSufficientSockQuantityException;
import me.theo.coursepaper3app.exception.InvalidSockRequestException;
import me.theo.coursepaper3app.models.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class SockService {
    private Map<Sock, Integer> socks = new HashMap<>(); //ключ это носок, значение это количество носков
    private final FileService fileService;

    public SockService(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct()
    private void init() {
        try {
            fileService.readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socks);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        String json = fileService.readFromFile();
        try {
            socks = new ObjectMapper().readValue(json, new TypeReference<HashMap<Sock, Integer>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkingСonditions(Sock sock) { //проверка на правильные значения
        if (sock.getColor() == null || sock.getSize() == null) {
            throw new InvalidSockRequestException("все поля должны быть заполнены");
        }
        if (sock.getCottonPart() < 0 || sock.getCottonPart() > 100) {
            throw new InvalidSockRequestException("значения должны быть от 0 до 100");
        }
        if (sock.getQuantity() <= 0) {
            throw new InvalidSockRequestException("количество должно быть больше 0");
        }
    }

    public boolean addSock(Sock sock) {
        checkingСonditions(sock);
        socks.put(sock, sock.getQuantity());// здесь не надо добавлять все поля, так как ломбок сам это сделает
        saveToFile();
        if (socks.containsKey(sock)) {
            return true;
        } else{
            return false;
        }
    }

    public boolean issueFromStock(Sock sock) { // выдача носков
        checkingСonditions(sock);
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            if (       entry.getKey().getSize().equals(sock.getSize())
                    && entry.getKey().getCottonPart() == sock.getCottonPart()
                    && entry.getKey().getColor().equals(sock.getColor()) ) {
                if (entry.getKey().getQuantity() >= sock.getQuantity()) {
                    entry.setValue(entry.getKey().getQuantity() - sock.getQuantity());
                    AuditService.recordIssueOperation(sock, sock.getQuantity());
                    return  true;
                }
            }else {
                throw new InSufficientSockQuantityException("нет носков");
            }

        }
        return false;
    }

    public boolean decreaseSockQuantity(Sock sock) { // списание испорченных носков
        checkingСonditions(sock);
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            if (       entry.getKey().getSize().equals(sock.getSize())
                    && entry.getKey().getCottonPart() == sock.getCottonPart()
                    && entry.getKey().getColor().equals(sock.getColor()) ) {
                if (entry.getKey().getQuantity() >= sock.getQuantity()) {
                    entry.setValue(entry.getKey().getQuantity() - sock.getQuantity());
                    AuditService.recordRemoveDefectedOperation(sock, sock.getQuantity());
                    return true;
                }
            }else {
                throw new InSufficientSockQuantityException("нет носков");
            }
        }
        return false;
    }


    public int getSockQuantity(Color color, Size size, Integer cottonMin, Integer cottonMax) { // общее количество носков
        int total = 0;
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            if (color != null && !entry.getKey().getColor().equals(color)) {
                continue;
            }
            if (size != null && !entry.getKey().getSize().equals(size)) {
                continue;
            }
            if (cottonMin != null && entry.getKey().getCottonPart() < cottonMin) {
                continue;
            }
            if (cottonMax != null && entry.getKey().getCottonPart() > cottonMax) {
                continue;
            }
            total += entry.getValue();
        }
        return  total;
    }
}
