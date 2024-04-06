package com.lucashzs.api.storage.dtos;

public class AddAmountDto {

    private int amount;

    public AddAmountDto() {
    }

    public AddAmountDto(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
