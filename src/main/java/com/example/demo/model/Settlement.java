package com.example.demo.model;

public class Settlement {
    private Member from;
    private Member to;
    private int amount;

    public Settlement(Member from, Member to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Member getFrom() {
        return from;
    }

    public Member getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }
}

