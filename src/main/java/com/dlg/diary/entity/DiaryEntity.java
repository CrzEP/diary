package com.dlg.diary.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author lingui
 * @Date 2023/3/28 10:13
 */
@Entity
@Table(name = "t_diary")
@Data
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String diaryName;

    private Date createTime;

    @Transient
    private List<FileExtEntity> fileExts;

}
