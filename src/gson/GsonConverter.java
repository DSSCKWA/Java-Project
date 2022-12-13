package src.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.gson.adapters.LocalDateSerializer;

import java.time.LocalDate;

public class GsonConverter {
    public static Gson newGsonWriterConverter() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
    }
}
