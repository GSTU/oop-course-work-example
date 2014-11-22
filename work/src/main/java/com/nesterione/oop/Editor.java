package com.nesterione.oop;

import com.nesterione.oop.storage.Phone;
import com.nesterione.oop.storage.StorageFactory;

import java.util.*;

/**
 * Created by igor on 22.11.2014.
 */
public class Editor {

    public static void showMainMenu() {
        System.out.println("Злавное меню:");
        System.out.println(Codes.CHANGE_REPOSITORY+" - выбрать репозиторий:");
        System.out.println(Codes.SHOW_ALL+" - показать все записи активного репозитория:");
        System.out.println(Codes.DETAILS+" - показать подробно информацию о записи");
        System.out.println(Codes.ADD+" - добавить новую запись:");
        System.out.println(Codes.UPDATE+" - изменить запись:");
        System.out.println(Codes.DELETE+" - удалить запись");
        System.out.println(Codes.EXIT+" - Завершить работу");
    }

    private void execute(int whatDoing) {
        switch (whatDoing) {
            case Codes.SHOW_ALL:
                printList(StorageFactory.getInstance().getAll());
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
        StorageFactory.getInstance().add(phone);
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
        showDetails(detailsPhone);
    }

    private void showDetails(Phone phone) {
        System.out.println("Подробная информация о записи:");
        System.out.println("UUID: "+phone.getId());
        System.out.println("Имя: "+phone.getName());
        System.out.println("Номер телефона: "+phone.getPhoneNumber());
        System.out.println("Последнее изменение: "+phone.getLastChange());
        System.out.println("Заметки: "+ phone.getNotes());
        System.out.println();
    }

    // Обратите внимание, что этот пример обучающий, и тут приходится для удаления вручную вводить UUID
    // что не очень удобно
    private void deleteOperation() {
        System.out.println("Введите UUID удаляемой записи (можно скопировать;)");
        String uuid = Reader.getString();
        Phone deletedRow = StorageFactory.getInstance().get(UUID.fromString(uuid));
        StorageFactory.getInstance().delete(deletedRow);
    }

    public void printList(List<Phone> phones) {
        for(Phone phone : phones) {
            printPhone(phone);
        }
    }

    private void printPhone(Phone phone) {
        System.out.format("%s    %s     %s\n", phone.getId(), phone.getName(), phone.getPhoneNumber());
    }

    public void run() {

        int value = 0;
        do {
            try {
                showMainMenu();
                value = Reader.getInt();
                execute(value);
            } catch (InputMismatchException ex) {
                //TODO здесь просто пргрлатываются исключения, в курсавой работе вам нужно корректно обрабатывать ошибки
            }
        } while (value!=Codes.EXIT);
    }
}
