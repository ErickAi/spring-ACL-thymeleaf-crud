package com.er.acl.services;

import com.er.acl.domain.NoticeMessage;
import com.er.acl.repositories.NoticeMessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeMessageServiceImpl implements NoticeMessageService {
    private final MutableAclService mutableAclService;
    private final NoticeMessageRepo repository;

    @Override
    @PostFilter(value = "hasPermission(filterObject,'READ')")
    public List<NoticeMessage> getAll() {
        return repository.findAll();
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public NoticeMessage getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found message with id: " + id));
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public void add(NoticeMessage noticeMessage) {
        repository.save(noticeMessage);
        createAcl(noticeMessage);
    }

    @Override
    @PreAuthorize("hasPermission(#noticeMessage, 'ADMINISTRATION')")
    public void update(NoticeMessage noticeMessage) {
        repository.save(noticeMessage);
    }

    @Transactional
    @PreAuthorize("hasPermission(#forDelete, 'ADMINISTRATION')")
    public void delete(NoticeMessage forDelete) {
        repository.delete(forDelete);
        deleteAcl(forDelete);
    }

    private void createAcl(NoticeMessage noticeMessage) {
        ObjectIdentity oid = new ObjectIdentityImpl(noticeMessage.getClass(), noticeMessage.getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        final Sid guest = new GrantedAuthoritySid("ROLE_GUEST");
        final Sid user = new GrantedAuthoritySid("ROLE_USER");
        final Sid editor = new GrantedAuthoritySid("ROLE_EDITOR");

        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, guest, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, user, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, user, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, owner, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, editor, true);

        mutableAclService.updateAcl(acl);
    }

    private void deleteAcl(NoticeMessage noticeMessage) {
        ObjectIdentity oid = new ObjectIdentityImpl(noticeMessage.getClass(), noticeMessage.getId());
        mutableAclService.deleteAcl(oid, false);
    }
}
