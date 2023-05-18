package me.theo.coursepaper3app.models;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class Operation {
    private final TypeOfOperation typeOfOperation;
    private final LocalDateTime localDateTime;
    private final  int quantity;
    private Sock sock;

    public Operation(TypeOfOperation typeOfOperation, LocalDateTime localDateTime, int quantity, Sock sock) {
        this.typeOfOperation = typeOfOperation;
        this.localDateTime = localDateTime;
        this.quantity = quantity;
        this.sock = sock;
    }

    public TypeOfOperation getTypeOfOperation() {
        return typeOfOperation;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public Sock getSock() {
        return sock;
    }
}
