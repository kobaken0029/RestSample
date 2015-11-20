package com.kobaken0029.restsample;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class ResponseObject {
    private List<Forecast> forecasts;
    private Location location;

    @Data
    class Forecast {
        private String telop;
        private Temperature temperature;
    }

    @Data
    class Location {
        /** フィールド名 */
        public static final String FIELD_NAME_ID = "id";
        public static final String FIELD_NAME_CITY = "city";

        private String id;
        private String city;
        private String area;
        private String prefecture;
    }

    @Data
    class Temperature {
        /** HASH値のkey */
        public static final String HASH_KEY_CELSIUS = "celsius";

        private HashMap<String, String> max;
        private HashMap<String, String> min;
    }
}
