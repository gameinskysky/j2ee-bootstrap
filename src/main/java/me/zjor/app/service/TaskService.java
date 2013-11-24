package me.zjor.app.service;

import com.google.inject.persist.Transactional;
import lombok.extern.slf4j.Slf4j;
import me.zjor.app.manager.TaskManager;
import me.zjor.app.model.Task;
import me.zjor.app.model.TaskStatus;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Sergey Royz
 * @since: 18.11.2013
 */
@Slf4j
public class TaskService {

    public static Pattern TAG_REGEX = Pattern.compile("(#[\\S]+)");

    @Inject
    private TaskManager taskManager;

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

    @Transactional
    public void setStatus(String taskId, TaskStatus newStatus) {
        Task task = taskManager.findById(taskId);
        if (task == null) {
            log.warn("Task {} was not found", taskId);
            return;
        }

        task.setStatus(newStatus);
    }

}
