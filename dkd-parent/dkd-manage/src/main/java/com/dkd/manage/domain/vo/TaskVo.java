package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Task;
import com.dkd.manage.domain.TaskType;

/**
 * 工单视图对象
 */
public class TaskVo extends Task {

    // 工单类型
    private TaskType taskType;

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
}
