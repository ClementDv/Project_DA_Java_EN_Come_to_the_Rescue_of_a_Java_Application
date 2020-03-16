package com.hemebiotech.analytics;

import java.io.*;
import java.util.*;

public class AnalyticsCounter {

    public static void main(String[] args) {
        // Get path from properties file.
        GetPathFile pathFileRead = new ReadPathFromFile();
        // Setup a list from the symptoms file.
        ISymptomReader listSymptom = new ReadSymptomDataFromFile(pathFileRead.getSymptomsFile());
        // Create a map with the number of symptoms.
        Map<String, Integer> mapSymptoms = mapFromList(listSymptom.GetSymptoms());
        // Write in the result file.
        writeFileOut(mapSymptoms, pathFileRead.getResultFile());
    }

    /**
     * Count the number of element in a list and return a HashMap
     **/
    public static Map<String, Integer> mapFromList(List<String> listFull) {
        Map<String, Integer> new_map = new HashMap<>();
        for (String symptom : listFull) {
            new_map.merge(symptom, 1, Integer::sum);
        }
        return new_map;
    }

    /**
     * Write the key of a list and the value associated in a file from a given path
     **/
    public static void writeFileOut(Map<String, Integer> symptomsMap, String outPath) {
        try (Writer wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outPath))))) {
            for (Map.Entry<String, Integer> mSymptom : symptomsMap.entrySet()) {
                wr.write(mSymptom.getKey() + "=" + mSymptom.getValue() + '\n');
            }
        } catch (Exception e) {
            System.err.println("Problème dans la création ou dans l'écriture du fichier de sortie");
        }
    }

}