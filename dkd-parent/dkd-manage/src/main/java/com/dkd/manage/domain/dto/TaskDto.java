package com.dkd.manage.domain.dto;

import java.util.List;

/**
 * 工单Dto
 */
public class TaskDto {

    /** 创建类型 */
    private Long createType;

    /** 关联设备编号 */
    private String innerCode;

    /** 任务执行人Id */
    private Long userId;

    /** 指派人Id */
    private Long assignorId;

    /** 工单类型 */
    private Long productTypeId;

    /** 描述信息 */
    private String desc;

    /** 工单详情(只有补货工单才涉及) */
    private List<TaskDetailsDto> details;

    public Long getCreateType() {
        return createType;
    }

    public void setCreateType(Long createType) {
        this.createType = createType;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAssignorId() {
        return assignorId;
    }

    public void setAssignorId(Long assignorId) {
        this.assignorId = assignorId;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<TaskDetailsDto> getDetails() {
        return details;
    }

    public void setDetails(List<TaskDetailsDto> details) {
        this.details = details;
    }
}
