package com.er.acl.acl;

import com.er.acl.repositories.NoticeMessageRepo;
import com.er.acl.security.AclConfig;
import com.er.acl.security.AclMethodSecurityConfiguration;
import com.er.acl.services.NoticeMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import({AclMethodSecurityConfiguration.class, AclConfig.class})
@ComponentScan({"com.er.acl.services"})
@ActiveProfiles("test")
class AbstractAclSecurityIntegrationTest {

    @Autowired
    NoticeMessageService service;
    @Autowired
    NoticeMessageRepo repository;
}
