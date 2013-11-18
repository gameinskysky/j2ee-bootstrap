package me.zjor.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zjor.app.model.Task;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: Sergey Royz
 * @since: 18.11.2013
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO implements Serializable {

    private String id;
    private String source;
    private String task;
    private List<String> tags = new ArrayList<String>();

    public static TaskDTO fromModel(Task model) {
        TaskDTO dto = new TaskDTO();
        dto.setId(model.getId());
        dto.setSource(model.getSource());
        dto.setTask(model.getTask());

        if (!StringUtils.isEmpty(model.getTags())) {
            for (String tag: StringUtils.split(model.getTags(), ',')) {
                dto.getTags().add(tag);
            }
        }
        return dto;
    }

    public static List<TaskDTO> fromCollection(Collection<Task> items) {
        List<TaskDTO> result = new ArrayList<TaskDTO>();

        for (Task t: items) {
            result.add(fromModel(t));
        }

        return result;
    }

}
