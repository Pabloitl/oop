package retoExam.files;

/*
    Periodo: enero-junio (2019)
    Alumno: Pablo Vargas Bermúdez
    Semestre: 2
    Profesor: Carpio Flores Jose Gerardo
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.BiConsumer;
import retoExam.entities.Student;
import retoExam.entities.User;

public class FileManager {

    final static public String TYPE_TEXT = ".txt",
            TYPE_IMG = ".png",
            PATH_FILES = "src/retoExam/files/",
            PATH_IMGS = "src/retoExam/imgs/",
            SHADOW_FILE = "shadow",
            USERS_FILE = "users",
            MENU_FILE = "menu",
            STOCK_FILE = "stock",
            IN_FILE = "in",
            OUT_FILE = "out",
            SHADOW = PATH_FILES + SHADOW_FILE + TYPE_TEXT,
            USERS = PATH_FILES + USERS_FILE + TYPE_TEXT,
            MENU = PATH_FILES + MENU_FILE + TYPE_TEXT,
            STOCK = PATH_FILES + STOCK_FILE + TYPE_TEXT,
            IN = PATH_FILES + IN_FILE + TYPE_TEXT,
            OUT = PATH_FILES + OUT_FILE + TYPE_TEXT,
            SEPARATOR = ":";

    static enum IN_FIELDS {

        NAME, MONEY, DATE
    };

    static enum OUT_FIELDS {

        NAME, MONEY, DATE
    };

    private static void writeTemplate(File file,
            BiConsumer<FileWriter, String> lambda, String args) {
        try {
            FileWriter fw = new FileWriter(file, true);
            lambda.accept(fw, args);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static String[] getLines(String file) {
        return getLines(new File(file));
    }

    public static void append(String file, String toAppend) {

        writeTemplate(new File(file),
                (fw, buffer) -> {
                    try {
                        fw.write("\n" + buffer);
                        fw.close();
                    } catch (IOException ex) {
                        ex.getMessage();
                    }
                }, toAppend);
    }

    public static String[] getLines(File file) {
        ArrayList<String> buff = new ArrayList();

        try {
            Scanner in = new Scanner(new FileInputStream(file));
            while (in.hasNext()) {
                buff.add(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        }

        String[] arr = new String[buff.size()];
        return buff.toArray(arr);
    }

    public static void append(File file, String toAppend) {
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write("\n" + toAppend);
            fw.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void overWrite(String file, String buffer) {
        overWrite(new File(file), buffer);
    }

    public static void overWrite(File file, String buffer) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(buffer);
            fw.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void exitOnNull(String... args) {
        for (String eval : args) {
            if (eval == null) {
                System.exit(0);
            }
        }
    }

    public static void replace(String file, User user, User newUser) {
        replace(new File(file), user, newUser);
    }

    public static void replace(File file, User user, User newUser) {
        String[] buffer = getLines(SHADOW);
        StringBuilder sb = new StringBuilder();

        for (String s : buffer) {
            if (s.split(SEPARATOR)[User.USER_FIELD].equals(user.getName())) {
                sb.append(User.format(newUser.getType(), newUser.getName(),
                        newUser.getPassword()));

                if (newUser instanceof Student) {
                    sb.append(":").append(
                            ((Student) newUser).getParent());
                }

                sb.append("\n");
                continue;
            }
            sb.append(s).append("\n");
        }

        overWrite(file, sb.substring(0, sb.length() - 1));
    }
}
