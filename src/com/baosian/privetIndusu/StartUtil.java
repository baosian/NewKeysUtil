package com.baosian.privetIndusu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartUtil {


    public static void main(String[] args) {
        //set L&F
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //open property file
        JOptionPane.showMessageDialog(null
                , "You are recommended to make copy of the property file and project folder before continuing");
        File selectedPropertyFile = null;
        Map<String, String> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        JFileChooser fileOpen = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Property Files", "txt");
        fileOpen.setFileFilter(fileFilter);
        int ret = fileOpen.showDialog(null, "Open");
        if (ret == JFileChooser.APPROVE_OPTION) {
            selectedPropertyFile = fileOpen.getSelectedFile();
            try {
                FileReader fileReader = new FileReader(selectedPropertyFile);
                ReadPropertyFile readPF = new ReadPropertyFile(fileReader);
                map = readPF.readPropertyFileMetod();
                list = readPF.getOldList();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int yesNo = JOptionPane.showConfirmDialog(null
                    , "Would you like to rewrite existing property file?");

            if (yesNo == 0) {
                WritePropertFile writePF = new WritePropertFile(selectedPropertyFile);
                writePF.writePropertyFile(map);
            }else {
                yesNo = JOptionPane.showConfirmDialog(null
                        , "Would you prefer to create new property file?");
                if (yesNo == 0) {
                    JFileChooser fileSave = new JFileChooser();
                    fileSave.setFileFilter(fileFilter);
                    int retVal = fileSave.showSaveDialog(null);
                    if (retVal == JFileChooser.APPROVE_OPTION) {
                        File newPropertyFile = new File(fileSave.getSelectedFile() + ".txt");
                        WritePropertFile writePF = new WritePropertFile(newPropertyFile);
                        writePF.writePropertyFile(map);
                    }
                }
            }
            replaceKeyInJavaFiles(list);
        }

    }
    //choose directory with java files
    public static void replaceKeyInJavaFiles(List<String> list){
        JOptionPane.showMessageDialog(null, "Choose folder with java files.");
        JFileChooser folderOpen = new JFileChooser();
        folderOpen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retVal = folderOpen.showDialog(null, "Choose");
        String folderName = folderOpen.getSelectedFile().getAbsolutePath();

        if (retVal == JFileChooser.APPROVE_OPTION) {
            File javaDir = new File(folderName);
            processFilesFromFolder(javaDir, list);
        }
        JOptionPane.showMessageDialog(null, "Keys are successfully changed.");
    }

    private static void processFilesFromFolder(File javaDir, List<String> list) {
        File[] folderEntries = javaDir.listFiles();
        for (File entry : folderEntries){
            if (entry.isDirectory()) {
                processFilesFromFolder(entry, list);
                continue;
            }else {
                if (entry.getAbsolutePath().endsWith(".java")){
                    rewriteJavaFile(entry, list);
                }
            }
        }
    }

    private static void rewriteJavaFile(File entry, List<String> list) {
        try {
            FileReader javaFileReader = new FileReader(entry);
            BufferedReader bufferedReader = new BufferedReader(javaFileReader);
            String line = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null){
                for (String str : list){
                    if (line.contains(str)) {
                        String newKey = str.toLowerCase();
                        newKey = newKey.replace('_', '.');
                        line = line.replace(str, newKey);
                    }

                }
                stringBuffer.append(line);
                stringBuffer.append('\n');
            }
            String inputString = stringBuffer.toString();
            bufferedReader.close();
            FileWriter fileWriter = new FileWriter(entry);
            fileWriter.write(inputString);
            fileWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
