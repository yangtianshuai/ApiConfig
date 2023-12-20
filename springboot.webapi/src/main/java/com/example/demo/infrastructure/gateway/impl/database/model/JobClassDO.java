package com.example.demo.infrastructure.gateway.impl.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 工作类别字典
 */
@Data
@Entity
@Table(name="JOB_CLASS_DICT")
public class JobClassDO {
    /**
     * 流水号
     */
    @Id
    @Column(name = "SERIAL_NO")
    private String id;
    /**
     * 工作类别代码
     */
    @Column(name = "JOB_CLASS_CODE")
    private String titleCode;
    /**
     * 工作类别名称
     */
    @Column(name = "JOB_CLASS_NAME")
    private String titleName;
}
