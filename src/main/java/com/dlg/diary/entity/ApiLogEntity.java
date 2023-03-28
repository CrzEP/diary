package com.dlg.diary.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * api操作相关实体类
 *
 * @author lingui
 * @Date 2022-09-26 11:10:58
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_api_log")
public class ApiLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logTime;

    private String api;

    private long time;

    private boolean status;

    private String ipAddr;

    private String header;

    private String requestUri;

    private String merthod;

    private String args;


}
