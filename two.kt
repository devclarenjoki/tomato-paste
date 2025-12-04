package com.codility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonParser {
    public List<PostEntity> parse(InputStream inputStream) throws Exception {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new JsonDeserializer<DateTime>() {
                @Override
                public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                    return formatter.parseDateTime(json.getAsString());
                }
            })
            .create();
        
        try {
            List<PostEntity> result = gson.fromJson(new InputStreamReader(inputStream), 
                new TypeToken<List<PostEntity>>(){}.getType());
            return result != null ? result : List.of();
        } catch (Exception e) {
            return List.of();
        }
    }
}