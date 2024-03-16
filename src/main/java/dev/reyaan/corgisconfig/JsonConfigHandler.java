package dev.reyaan.corgisconfig;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;


public class JsonConfigHandler<T> extends ConfigHandler<T> {
    public static Gson defaultGson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .serializeNulls()
            .create();

    public Gson gson;

    public JsonConfigHandler(File file) {
        this(file, defaultGson);
    }

    public JsonConfigHandler(File file, Gson gson) {
        super(file);
        this.gson = gson;
    }

    public T read(Class<T> clazz) throws IOException {
        String result = Files.readString(file.toPath());
        return new Gson().fromJson(result, clazz);
    }

    public void write(T object) throws IOException {
        String result = gson.toJson(object);
        if(!file.exists()) file.createNewFile();
        var out = new FileOutputStream(file, false);

        out.write(result.getBytes());
        out.flush();
        out.close();
    }
}
