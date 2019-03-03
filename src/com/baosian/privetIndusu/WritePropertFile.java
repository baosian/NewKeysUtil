package com.baosian.privetIndusu;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WritePropertFile {
    private File propertyFile;

    public WritePropertFile(File propertyFile) {
        this.propertyFile = propertyFile;
    }

    public void writePropertyFile(Map<String, String> map){
        try {
            FileWriter propertyFileWriter = new FileWriter(propertyFile);
            BufferedWriter bufferedWriter = new BufferedWriter(propertyFileWriter);
            for (Map.Entry<String, String> entry : map.entrySet()) {

                bufferedWriter.write(entry.getKey()+entry.getValue());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            JOptionPane.showMessageDialog(null, "Property file is successfully overwritten.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
