package me.zjor.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Sergey Royz
 * @since: 18.11.2013
 */
public class TaskService {

    public static Pattern TAG_REGEX = Pattern.compile("(#[\\S]+)");

    /**
     * Extracts tokens prepended by '#' as tags
     *
     * @param message
     * @return list of tags
     */
    public static List<String> extractTags(String message) {
        List<String> tags = new ArrayList<String>();

        Matcher m = TAG_REGEX.matcher(message);
        while (m.find()) {
            tags.add(m.group(1));
        }

        return tags;
    }

    /**
     * Removes tags from source message
     *
     * @param message
     * @return
     */
    public static String removeTags(String message) {
        List<String> tags = extractTags(message);
        for (String tag : tags) {
            message = message.replaceAll(tag, "");
        }
        message = message.replaceAll("\\s{2,}", " ");
        message = message.trim();
        return message;
    }

}
