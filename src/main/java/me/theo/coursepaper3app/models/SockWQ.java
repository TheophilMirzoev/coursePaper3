package me.theo.coursepaper3app.models;

import java.util.Objects;

public class SockWQ {   // класс такой же как носок, но с добавлением количества
    private int cottonPart;//колво хлопка в процентах
    private Size size;
    private Color color;
    private int quantity;


    public int getCottonPart() {
        return cottonPart;
    }

    public Size getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setCottonPart(int cottonPart) {
        this.cottonPart = cottonPart;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SockWQ sockWQ = (SockWQ) o;
        return cottonPart == sockWQ.cottonPart && quantity == sockWQ.quantity && size == sockWQ.size && color == sockWQ.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cottonPart, size, color, quantity);
    }
}
