package com.example.demo.infrastructure.gateway.impl.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * 职称字典
 */
@Data
@Entity
@Table(name="TITLE_DICT")
public class TitleDO implements Serializable {
    /**
     * 流水号
     */
    @Id
    @Column(name = "SERIAL_NO")
    private String id;
    /**
     * 职称代码
     */
    @Column(name = "TITLE_CODE")
    private String titleCode;
    /**
     * 职称名称
     */
    @Column(name = "TITLE_NAME")
    private String titleName;

}
