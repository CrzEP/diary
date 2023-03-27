package com.dlg.diary.repository;

import com.dlg.diary.entity.EmailInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailInfoRepository extends JpaRepository<EmailInfo, Long> {

}