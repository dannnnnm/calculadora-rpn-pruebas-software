package org.example.stack;

public class NumberType {
    Number value;
    TypeTag numberType;

    public NumberType(int value) {
        this.value = value;
        this.numberType=TypeTag.INTEGER;
    }

    public NumberType(double value) {
        this.value = value;
        this.numberType=TypeTag.FLOAT;
    }

    public Number getValue() {
        return value;
    }
}
