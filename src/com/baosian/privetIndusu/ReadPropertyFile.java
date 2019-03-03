package com.baosian.privetIndusu;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadPropertyFile {
    private FileReader propertyFile;
    private List<String> oldList;

    ReadPropertyFile(FileReader fileReader){
        this.propertyFile = fileReader;
    }

    public FileReader getPropertyFile() {
        return propertyFile;
    }

    public Map<String, String> readPropertyFileMetod(){
        Map<String, String> map = new HashMap<>();
        oldList = new ArrayList<>();
        try {
            BufferedReader propertyFileReader = new BufferedReader(propertyFile);
            String line = null;
            String key = null;
            String value = null;
            while ((line = propertyFileReader.readLine()) != null){
                if (!line.isEmpty()) {
                    int index = line.indexOf("=");
                    key = line.substring(0, index);
                    oldList.add(key);
                    value = line.substring(index);
                    key = key.toLowerCase();
                    key = key.replace('_', '.');
                    map.put(key, value);

                }
            }
            propertyFileReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<String> getOldList() {
        return oldList;
    }
}
