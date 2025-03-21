package com.yildiz.sehrinsesi.repository;

import com.yildiz.sehrinsesi.model.Complaints;
import com.yildiz.sehrinsesi.utils.ComplaintsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintsRepository extends JpaRepository<Complaints, Long> {

    List<Complaints> findByUserId(int user_id);
    List<Complaints> findByStatus(ComplaintsStatus status);

}