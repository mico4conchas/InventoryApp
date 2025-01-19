package edu.utsa.cs3443.inventoryapp;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {

    private static final String INVENTORY_FILE_NAME = "inventory.json";

    // Save inventory to file
    public static void saveInventoryToFile(Context context, ArrayList<InventoryItem> inventoryList) {
        Gson gson = new Gson();
        String json = gson.toJson(inventoryList);

        File file = new File(context.getFilesDir(), INVENTORY_FILE_NAME);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load inventory from file
    public static ArrayList<InventoryItem> loadInventoryFromFile(Context context) {
        File file = new File(context.getFilesDir(), INVENTORY_FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<InventoryItem>>() {}.getType();
            return gson.fromJson(json.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
