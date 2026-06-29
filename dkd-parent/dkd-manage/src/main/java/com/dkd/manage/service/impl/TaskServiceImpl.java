package com.dkd.manage.service.impl;

import java.util.List;
import com.dkd.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.TaskMapper;
import com.dkd.manage.domain.Task;
import com.dkd.manage.domain.vo.TaskVo;
import com.dkd.manage.service.ITaskService;

/**
 * 工单管理Service业务层处理
 * 
 * @author itheima
 * @date 2026-06-29
 */
@Service
public class TaskServiceImpl implements ITaskService 
{
    @Autowired
    private TaskMapper taskMapper;

    /**
     * 查询工单管理
     * 
     * @param taskId 工单管理主键
     * @return 工单管理
     */
    @Override
    public Task selectTaskByTaskId(Long taskId)
    {
        return taskMapper.selectTaskByTaskId(taskId);
    }

    /**
     * 查询工单管理列表
     *
     * @param task 工单管理
     * @return 工单管理
     */
    @Override
    public List<Task> selectTaskList(Task task)
    {
        return taskMapper.selectTaskList(task);
    }

    /**
     * 查询运维工单列表
     * @param task
     * @return TaskVo集合
     */
    @Override
    public List<TaskVo> selectTaskVoList(Task task) {
        return taskMapper.selectTaskVoList(task);
    }

    /**
     * 新增工单管理
     * 
     * @param task 工单管理
     * @return 结果
     */
    @Override
    public int insertTask(Task task)
    {
        task.setCreateTime(DateUtils.getNowDate());
        return taskMapper.insertTask(task);
    }

    /**
     * 修改工单管理
     * 
     * @param task 工单管理
     * @return 结果
     */
    @Override
    public int updateTask(Task task)
    {
        task.setUpdateTime(DateUtils.getNowDate());
        return taskMapper.updateTask(task);
    }

    /**
     * 批量删除工单管理
     * 
     * @param taskIds 需要删除的工单管理主键
     * @return 结果
     */
    @Override
    public int deleteTaskByTaskIds(Long[] taskIds)
    {
        return taskMapper.deleteTaskByTaskIds(taskIds);
    }

    /**
     * 删除工单管理信息
     * 
     * @param taskId 工单管理主键
     * @return 结果
     */
    @Override
    public int deleteTaskByTaskId(Long taskId)
    {
        return taskMapper.deleteTaskByTaskId(taskId);
    }
}
