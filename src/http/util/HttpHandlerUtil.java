package src.http.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import src.http.constants.HttpStatus;

import java.lang.reflect.Type;
import java.util.Map;

public class HttpHandlerUtil {
    public static Map<String, String> validateRequestBody(String request) throws HttpException {
        try {
            if (request == null || request.isBlank()) {
                throw new Exception();
            }
            final Type mapType = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> jsonData = new Gson().fromJson(request, mapType);
            if (jsonData == null || jsonData.size() <= 0) {
                throw new Exception();
            }
            return jsonData;
        } catch (Exception e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Request should be of JSON format");
        }
    }
}
