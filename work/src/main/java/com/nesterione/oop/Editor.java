package com.nesterione.oop;

import com.nesterione.oop.storage.*;

import java.util.*;

/**
 * Created by igor on 22.11.2014.
 */
public class Editor {

    private void execute(int whatDoing) {
        switch (whatDoing) {
            case Codes.SHOW_ALL:
                StorageConsolePrinter.print(StorageFactory.getInstance().getAll());
                break;
            case Codes.DELETE:
                deleteOperation();
                break;
            case Codes.DETAILS:
                detailsOperation();
                break;
            case Codes.ADD:
                addOperation();
                break;
            case Codes.UPDATE:
                updateOperation();
                break;
            case Codes.CREATE_BIN_REPOSITORY:
                createBinRepository();
                break;
            case Codes.CREATE_TXT_REPOSITORY:
                createTxtRepository();
                break;
            case Codes.CHANGE_TO_MEM:
                StorageFactory.setInstance(new MemoryStorage());
                break;
            case Codes.CHANGE_TO_BIN:
                changeToBin();
                break;
            case Codes.CHANGE_TO_TXT:
                changeToTxt();
        }
    }

    private void createTxtRepository() {
        System.out.println("Введите имя (путь) нового текстового хранилища");
        String path = Reader.getString();
        Storage<Phone> storage = TextStorage.createNewTextStorage(path);
        if(storage!=null) {
            StorageFactory.setInstance(storage);
        } else  {
            System.out.println("Произошла ошибка при создании репозитория");
        }
    }

    private void createBinRepository() {
        System.out.println("Введите имя (путь) нового бинарного хранилища");
        String path = Reader.getString();
        Storage<Phone> storage = BinaryStorage.createNewBinaryStorage(path);
        if(storage!=null) {
            StorageFactory.setInstance(storage);
        } else  {
            System.out.println("Произошла ошибка при создании репозитория");
        }
    }

    private void changeToTxt() {
        System.out.println("Введите имя (путь) текстового хранилища");
        String path = Reader.getString();
        Storage<Phone> storage = new TextStorage(path);
        if(storage!=null) {
            StorageFactory.setInstance(storage);
        } else  {
            System.out.println("Произошла ошибка при открытия репозитория");
        }
    }

    private void changeToBin() {
        System.out.println("Введите имя (путь) бинарного хранилища");
        String path = Reader.getString();
        Storage<Phone> storage = new BinaryStorage(path);
        if(storage!=null) {
            StorageFactory.setInstance(storage);
        } else  {
            System.out.println("Произошла ошибка при открытия репозитория");
        }
    }

    private void updateOperation() {
        System.out.println("Введите UUID записи (можно скопировать;)");
        String uuid = Reader.getString();
        updatePhone(StorageFactory.getInstance().get(UUID.fromString(uuid)));
    }

    private void updatePhone(Phone phone) {
        System.out.println("Редактирование записи ["+phone.getId()+"]:");
        System.out.println();
        System.out.println("Измените имя ["+phone.getName()+"]: ");
        String name = Reader.getString();
        System.out.println("Измените номер телефона ["+phone.getPhoneNumber()+"]: ");
        String phoneNumber = Reader.getString();
        System.out.println("Измените заметки(через пробел) ["+phone.getNotes()+"]: ");
        String[] notes = Reader.getString().trim().split(" ");

        phone.setName(name);
        phone.setPhoneNumber(phoneNumber);
        phone.setNotes(Arrays.asList(notes));
        StorageFactory.getInstance().update(phone);
    }

    private static class Reader {

        private static Scanner scanner = new Scanner(System.in);
        public  static String getString() {
            String str="";
            while((str=scanner.nextLine()).trim().length()==0);
            return str;
        }

        public  static int getInt() {
            return scanner.nextInt();
        }
    }

    private void addOperation() {
        System.out.println("Добавление новой записи:");
        System.out.println();
        System.out.println("Введите имя: ");
        String name = Reader.getString();
        System.out.println("Введите номер телефона: ");
        String phoneNumber = Reader.getString();
        System.out.println("Введите заметки(через пробел): ");
        String[] notes = Reader.getString().trim().split(" ");
        Phone phone = new Phone(name,phoneNumber, Arrays.asList(notes));
        StorageFactory.getInstance().add(phone);
    }

    private void detailsOperation() {
        System.out.println("Введите UUID записи (можно скопировать;)");
        String uuid = Reader.getString();
        Phone detailsPhone = StorageFactory.getInstance().get(UUID.fromString(uuid));
        StorageConsolePrinter.details(detailsPhone);
    }

    // Обратите внимание, что этот пример обучающий, и тут приходится для удаления вручную вводить UUID
    // что не очень удобно
    private void deleteOperation() {
        System.out.println("Введите UUID удаляемой записи (можно скопировать;)");
        String uuid = Reader.getString();
        Phone deletedRow = StorageFactory.getInstance().get(UUID.fromString(uuid));
        StorageFactory.getInstance().delete(deletedRow);
    }

    public void run() {

        int value = 0;
        do {
            try {
                StorageConsolePrinter.menu();
                value = Reader.getInt();
                execute(value);
            } catch (InputMismatchException ex) {
                //TODO здесь просто пргрлатываются исключения, в курсавой работе вам нужно корректно обрабатывать ошибки
            }
        } while (value!=Codes.EXIT);
    }
}
