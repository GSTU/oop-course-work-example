package com.nesterione.oop.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * Здесь продеманстироваан пример работы с бинарным репозиторием
 * не самым эффективным способом (каждый раз, при выполенинии каких либо
 * действий будет перезаписываться файл хранящий репозиторий
 * для выполнения курсовой работы этого достаточно
 *
 * Created by igor on 22.11.2014.
 */
public class BinaryStorage implements Storage<Phone> {

    String path;

    private static boolean serialize(List<Phone> phones, String path) {
        boolean isOK = false;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(phones);
            oos.flush();
            oos.close();
            isOK = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isOK;
    }

    private static List<Phone> deserialize(String path) {

        List<Phone> phones = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream oin = new ObjectInputStream(fis);
            phones = (List<Phone>) oin.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return phones;
    }

    /**
     * Создает пустой репозиторий
     * @param path
     * @return Возращает ссылку на только что созданный репозиторий
     */
    public static BinaryStorage createNewBinaryStorage(String path) {
        // Создается пустой репозиторий
        List<Phone> phones = new ArrayList<Phone>();
        serialize(phones,path);
        return new BinaryStorage(path);
    }

    public BinaryStorage(String path) {
        // TODO: Для упрощения здесь отсутсвуют проверки существования файла, в курсовой они должны быть
        this.path = path;
    }

    @Override
    public List<Phone> getAll() {
        return deserialize(path);
    }

    @Override
    public Phone get(UUID id) {

        List<Phone> phones = deserialize(path);
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
        List<Phone> phones = deserialize(path);
        if(entity!=null) phones.add(entity);
        serialize(phones,path);
    }

    @Override
    public void delete(Phone entity) {
        List<Phone> phones = deserialize(path);
        for(Phone phone: phones) {
            if(phone.getId().equals(entity.getId())) {
                phones.remove(phone);
                break;
            }
        }
        serialize(phones,path);
    }

    @Override
    public void update(Phone entity) {
        List<Phone> phones = deserialize(path);

        for(Phone phone: phones) {
            if(phone.getId().equals(entity.getId())) {
                phones.set(phones.indexOf(phone),entity);
                break;
            }
        }

        serialize(phones,path);
    }

    @Override
    public String toString() {
        return "Binary Storage: "+ path;
    }
}
