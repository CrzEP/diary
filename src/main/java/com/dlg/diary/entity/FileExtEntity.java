package com.dlg.diary.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author lingui
 * @Date 2023/3/28 10:15
 */
@Entity
@Table(name = "t_fileExt")
@Data
public class FileExtEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String type;

    private String url;

    private String filePath;

    private Date createTime;

}
