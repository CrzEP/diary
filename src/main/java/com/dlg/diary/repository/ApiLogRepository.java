package com.dlg.diary.repository;

import com.dlg.diary.entity.ApiLogEntity;
import com.dlg.diary.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiLogRepository extends JpaRepository<ApiLogEntity, Long> {

}