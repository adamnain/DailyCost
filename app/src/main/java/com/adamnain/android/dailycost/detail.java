package com.adamnain.android.dailycost;

/**
 * Created by adamnain on 30/05/2017.
 */

public class detail {
    private int id;
    private String date;
    private String income;
    private String outcome;
    private String note;

    public detail(int id, String date, String income, String outcome, String note) {
        this.id = id;
        this.date = date;
        this.income = income;
        this.outcome = outcome;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
