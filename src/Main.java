import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        File dirSrc = new File("C:\\NetologyHW\\Сериализация_V.1\\Games\\src");
        File dirRes = new File("C:\\NetologyHW\\Сериализация_V.1\\Games\\res");
        File dirSave = new File("C:\\NetologyHW\\Сериализация_V.1\\Games\\savegames");
        File dirTemp = new File("C:\\NetologyHW\\Сериализация_V.1\\Games\\temp");
        File childMainSrc = new File(dirSrc, "main");
        File childTestSrc = new File(dirSrc, "test");
        File childDir3 = new File(dirTemp, "test.txt");
        File fileMain = new File("C:\\NetologyHW\\Сериализация_V.1\\Games\\src\\main\\Main.java");
        File fileUtils = new File("C:\\NetologyHW\\Сериализация_V.1\\Games\\src\\main\\Utils.java");
        File childDrawablesRes = new File(dirRes, "drawables");
        File childVectorsRes = new File(dirRes, "vectors");
        File childIconsRes = new File(dirRes, "icons");

        File zipSave = new File(dirSave, "zip_out.zip");

        StringBuilder builder = new StringBuilder();

        createDirectory(dirSrc, builder);
        createDirectory(dirRes, builder);
        createDirectory(dirSave, builder);
        createDirectory(dirTemp, builder);
        createDirectory(childMainSrc, builder);
        createDirectory(childTestSrc, builder);
        createDirectory(childDrawablesRes, builder);
        createDirectory(childVectorsRes, builder);
        createDirectory(childIconsRes, builder);
        createFile(fileMain, builder);
        createFile(fileUtils, builder);
        createFile(childDir3, builder);

        GameProgress characterOne = new GameProgress(100, 12, 45, 1005);
        GameProgress characterTwo = new GameProgress(12, 5, 14, 266);
        GameProgress characterThree = new GameProgress(1050, 40, 999, 10000);

        saveGame("C:\\NetologyHW\\Сериализация_V.1\\Games\\savegames\\save1.dat", characterOne);
        saveGame("C:\\NetologyHW\\Сериализация_V.1\\Games\\savegames\\save2.dat", characterTwo);
        saveGame("C:\\NetologyHW\\Сериализация_V.1\\Games\\savegames\\save3.dat", characterThree);

        methodZipFiles(zipSave, dirSave);
        for (File temp : dirSave.listFiles()) {
            if (temp.getName().contains("save")) {
                temp.delete();
            }
        }

        try (FileWriter writer = new FileWriter(childDir3)) {
            writer.write(builder.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public static void createDirectory(File nameDirectory, StringBuilder stringBuilder) {
        if (nameDirectory.mkdir()) {
            stringBuilder.append("Директория " + nameDirectory.getName() + " создан\n");
        }
    }

    public static void createFile(File nameFile, StringBuilder stringBuilder) {
        try {
            if (nameFile.createNewFile()) {
                stringBuilder.append("Файл " + nameFile.getName() + " создан\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveGame(String name, GameProgress Object) {
        try (FileOutputStream fos = new FileOutputStream(name);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(Object);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void methodZipFiles(File zipFile, File saveDirec) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (File temp : saveDirec.listFiles()) {
                if (temp.getName().contains(".dat")) {
                    FileInputStream fis = new FileInputStream(temp);
                    zos.putNextEntry(new ZipEntry(temp.getName()));
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    fis.close();
                    zos.write(bytes);
                    zos.closeEntry();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

