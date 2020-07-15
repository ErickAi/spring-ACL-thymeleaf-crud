package com.er.acl.repositories;

import com.er.acl.domain.NoticeMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeMessageRepo extends JpaRepository<NoticeMessage, Long> {

}
