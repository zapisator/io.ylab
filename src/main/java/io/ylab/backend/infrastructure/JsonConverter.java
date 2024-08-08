package io.ylab.backend.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConverter {

    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String) {
            return "\"" + escapeString((String) obj) + "\"";
        }
        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }
        if (obj instanceof Map) {
            return mapToJson((Map<?, ?>) obj);
        }
        if (obj instanceof List) {
            return listToJson((List<?>) obj);
        }
        throw new IllegalArgumentException("Unsupported type: " + obj.getClass());
    }

    private static String escapeString(String str) {
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private static String mapToJson(Map<?, ?> map) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                json.append(",");
            }
            json.append("\"").append(escapeString(entry.getKey().toString())).append("\":");
            json.append(toJson(entry.getValue()));
            first = false;
        }
        json.append("}");
        return json.toString();
    }

    private static String listToJson(List<?> list) {
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        for (Object item : list) {
            if (!first) {
                json.append(",");
            }
            json.append(toJson(item));
            first = false;
        }
        json.append("]");
        return json.toString();
    }

    public static Map<String, String> fromJson(String json) {
        final Map<String, String> map = new HashMap<>();
        if (json == null || json.isEmpty() || !json.startsWith("{") || !json.endsWith("}")) {
            return map;
        }

        final String content = json.substring(1, json.length() - 1);
        final String[] keyValuePairs = content.split(",");

        for (String keyValuePair : keyValuePairs) {
            String[] parts = keyValuePair.split(":");
            if (parts.length == 2) {
                String key = parts[0].trim().replaceAll("[{\"}]", "").trim();
                String value = parts[1].trim().replaceAll("[{\"}]", "").trim();
                map.put(key, value);
            }
        }
        return map;
    }

}
