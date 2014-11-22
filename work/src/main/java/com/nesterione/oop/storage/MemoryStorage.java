package com.nesterione.oop.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Реализация хранилища в памяти
 *
 * Created by igor on 22.11.2014.
 */
public class MemoryStorage implements Storage {

    private static List<Phone> phones;

    /*
     Инициализируем список начальными значениями
     */
    static {
        phones = new ArrayList<Phone>();
        phones.add(new Phone("Name 1", "+375 29 112121", Arrays.asList(new String[]{"note1","note2", "note3"})));
        phones.add(new Phone("Name 2", "+375 29 345534", Arrays.asList(new String[]{"note4","note5"})));
        phones.add(new Phone("Name 3", "+375 29 234234", Arrays.asList(new String[]{"note6"})));
        phones.add(new Phone("Name 4", "+375 29 546456", Arrays.asList(new String[]{"note7","note8", "note9"})));
    }


    @Override
    public List<Phone> getAll() {
        return phones;
    }

    @Override
    public Phone get(UUID id) {
        Phone result = null;

        /*Ищем нужную запись по id*/
        for(Phone phone: phones) {
            if(phone.getId().equals(id)) {
                result = phone;
                break;
            }
        }

        return result;
    }

    @Override
    public void add(Phone entity) {
        if(entity!=null) phones.add(entity);
    }

    @Override
    public void delete(Phone entity) {
        for(Phone phone: phones) {
            if(phone.getId().equals(entity.getId())) {
                phones.remove(phone);
                break;
            }
        }
    }

    @Override
    public void update(Phone entity) {
        for(Phone phone: phones) {
            if(phone.getId().equals(entity.getId())) {
                phones.set(phones.indexOf(phone),entity);
                break;
            }
        }
    }
}
