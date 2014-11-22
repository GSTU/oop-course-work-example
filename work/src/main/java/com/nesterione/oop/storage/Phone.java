package com.nesterione.oop.storage;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Класс для хранения номеров телефонов
 *
 * Created by igor on 22.11.2014.
 */
public class Phone {
    private UUID id;
    private String name;
    private String phoneNumber;
    private Date lastChange;
    private List<String> notes;

    public Phone(String name, String phoneNumber, List<String> notes) {
        id = UUID.randomUUID();
        setName(name);
        setPhoneNumber(phoneNumber);
        setNotes(notes);
    }

    /**
     * Обновить дату поледнего изменения
     */
    private void updateLastChange(){
        lastChange = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
        updateLastChange();
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
        updateLastChange();
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
        updateLastChange();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        updateLastChange();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateLastChange();
    }
}
