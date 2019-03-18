package com.matrangola.school.domain;

public class RegistrationItem implements RegistrationItemInterface {
    protected int id;

    public RegistrationItem() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
