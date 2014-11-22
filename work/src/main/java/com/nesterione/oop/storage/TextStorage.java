package com.nesterione.oop.storage;

import java.io.*;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * Здесь продеманстироваан пример работы с бинарным репозиторием
 * не самым эффективным способом (каждый раз, при выполенинии каких либо
 * действий будет перезаписываться файл хранящий репозиторий
 * для выполнения курсовой работы этого достаточно
 *
 * Created by igor on 22.11.2014.
 */
public class TextStorage implements Storage<Phone> {
    String path;

    private static String listToStringLine(List<String> notes) {
        StringBuffer str = new StringBuffer();
        for(String note :notes) {
            str.append("#");
            str.append(note);
        }

        return str.toString();
    }
    private static Phone textlineToPhone(String line) {
        //TODO в курсовой нужно выполнять проверку на правильность строк
        String[] items = line.trim().split("#");

        Phone phone = new Phone();
        phone.setId(UUID.fromString(items[0].trim()));
        phone.setName(items[1].trim());
        phone.setPhoneNumber(items[2].trim());

        List<String> notes = new ArrayList<>();
        for(int i = 4; i< items.length;i++) {
            notes.add(items[i].trim());
        }
        phone.setNotes(notes);

        // Должна быть последней, чтобы дата последнего изменения отображалась корректаня
        phone.setLastChange(new Date(Long.parseLong(items[3])));
        return phone;
    }

    private static boolean writeToFile(List<Phone> phones, String path) {
        boolean isOK = false;
        String pattern = "%s#%s#%s#%s%s\n";
        try (FileWriter writer = new FileWriter(path)) {
            for(Phone phone: phones) {
                writer.write(String.format(pattern,
                        phone.getId(),
                        phone.getName(),
                        phone.getPhoneNumber(),
                        phone.getLastChange().getTime(),
                        listToStringLine(phone.getNotes())
                ));
            }
            writer.flush();
            isOK = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isOK;
    }
    private static List<Phone> readFromFile(String path) {

        List<Phone> phones = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().length()>0) {
                    phones.add(textlineToPhone(line));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return phones;
    }

    public TextStorage(String path) {
        this.path = path;
    }

    /**
     * Создает пустой репозиторий
     * @param path
     * @return Возращает ссылку на только что созданный репозиторий
     */
    public static TextStorage createNewTextStorage(String path) {
        // Создается пустой репозиторий
        List<Phone> phones = new ArrayList<Phone>();
        writeToFile(phones, path);
        return new TextStorage(path);
    }

    @Override
    public List<Phone> getAll() {
        return readFromFile(path);
    }

    @Override
    public Phone get(UUID id) {

        List<Phone> phones = readFromFile(path);
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
        List<Phone> phones = readFromFile(path);
        if(entity!=null) phones.add(entity);
        writeToFile(phones, path);
    }

    @Override
    public void delete(Phone entity) {
        List<Phone> phones = readFromFile(path);
        for(Phone phone: phones) {
            if(phone.getId().equals(entity.getId())) {
                phones.remove(phone);
                break;
            }
        }
        writeToFile(phones, path);
    }

    @Override
    public void update(Phone entity) {
        List<Phone> phones = readFromFile(path);

        for(Phone phone: phones) {
            if(phone.getId().equals(entity.getId())) {
                phones.set(phones.indexOf(phone),entity);
                break;
            }
        }

        writeToFile(phones, path);
    }

    @Override
    public String toString() {
        return "Text Storage: "+ path;
    }
}
