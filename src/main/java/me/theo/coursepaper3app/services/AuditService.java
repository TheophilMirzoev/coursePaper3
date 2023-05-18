package me.theo.coursepaper3app.services;

import me.theo.coursepaper3app.models.Operation;
import me.theo.coursepaper3app.models.Sock;
import me.theo.coursepaper3app.models.TypeOfOperation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class AuditService {
    private static final List<Operation> operations = new ArrayList<>();

   // public void recordAddOperation(Socks socks, int quantity) {
     //  recordOperation(TypeOfOperation.ADD, socks, quantity);
   // }
    public static void recordIssueOperation(Sock sock, int quantity) {
       recordOperation(TypeOfOperation.ISSUE, sock, quantity);
    }
    public static void recordRemoveDefectedOperation(Sock sock, int quantity) {
       recordOperation(TypeOfOperation.REMOVE_DEFECTED, sock, quantity);
    }

    private static void recordOperation(TypeOfOperation type, Sock sock, int quantity) {
        operations.add(new Operation(type, LocalDateTime.now(), quantity, sock));
    }
}
