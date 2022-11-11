package com.dju.demo.helpers;

import org.bson.Document;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String listToJson(List<Document> docs) {
        return String.format(
                "[%s]",
                docs.stream().map(Document::toJson).collect(Collectors.joining(","))
        );
    }
}
