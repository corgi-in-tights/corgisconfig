package dev.reyaan.corgisconfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

public abstract class ConfigHandler<T> {
    public File file;

    public ConfigHandler(File file) {
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        this.file = file;
    }


    public T init(Class<T> clazz) {
        if (file.exists()) {
            try {
                return read(clazz);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return saveDefault(clazz);
        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract T read(Class<T> clazz) throws IOException;
    public abstract void write(T object) throws IOException;

    public T saveDefault(Class<T> clazz) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T config = clazz.getDeclaredConstructor().newInstance();
        write(config);
        return config;
    }
}
