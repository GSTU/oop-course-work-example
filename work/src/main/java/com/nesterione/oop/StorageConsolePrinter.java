package com.nesterione.oop;

import com.nesterione.oop.storage.Phone;
import com.nesterione.oop.storage.StorageFactory;

import java.util.List;

/**
 * Created by igor on 22.11.2014.
 */
public class StorageConsolePrinter {

    public static void print(Phone phone) {
        System.out.format("%s    %s     %s\n", phone.getId(), phone.getName(), phone.getPhoneNumber());
    }

    public static void print(List<Phone> phones) {
        for(Phone phone : phones) {
            StorageConsolePrinter.print(phone);
        }
    }

    public static void details(Phone phone) {
        System.out.println("Подробная информация о записи:");
        System.out.println("UUID: "+phone.getId());
        System.out.println("Имя: "+phone.getName());
        System.out.println("Номер телефона: "+phone.getPhoneNumber());
        System.out.println("Последнее изменение: "+phone.getLastChange());
        System.out.println("Заметки: "+ phone.getNotes());
        System.out.println();
    }

    public static void menu() {
        System.out.println("Основное меню: [активный репозиторий: "+ StorageFactory.getInstance().toString()+"]");
        System.out.println("-------------------");
        System.out.println(Codes.SHOW_ALL+" - показать все записи активного репозитория:");
        System.out.println(Codes.DETAILS+" - показать подробно информацию о записи");
        System.out.println(Codes.ADD+" - добавить новую запись:");
        System.out.println(Codes.UPDATE+" - изменить запись:");
        System.out.println(Codes.DELETE+" - удалить запись");
        System.out.println("-------------------");
        System.out.println(Codes.CHANGE_TO_MEM+" - выбрать репозиторий в памяти:");
        System.out.println(Codes.CHANGE_TO_BIN+" - выбрать бинарный репозиторий:");
        System.out.println(Codes.CHANGE_TO_TXT+" - выбрать текстовый репозиторий:");
        System.out.println(Codes.CREATE_TXT_REPOSITORY+" - создать новый текстовый репозиторий:");
        System.out.println(Codes.CREATE_BIN_REPOSITORY+" - создать новый бинарный репозиторий:");
        System.out.println("-------------------");
        System.out.println(Codes.EXIT+" - Завершить работу");
    }
}
