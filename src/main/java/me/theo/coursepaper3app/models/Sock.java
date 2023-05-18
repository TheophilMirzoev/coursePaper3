package me.theo.coursepaper3app.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sock {
    private int cottonPart;//колво хлопка в процентах
    private Size size;
    private Color color;
    private int quantity;
}
