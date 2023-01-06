package src.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.gson.adapters.LocalDateDeserializer;
import src.gson.adapters.LocalDateSerializer;
import src.gson.adapters.LocalTimeDeserializer;
import src.gson.adapters.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalTime;

public class GsonConverter {
    public static Gson newGsonWriterConverter() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                .create();
    }

    public static Gson newGsonRWConverter() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                .create();
    }
}
