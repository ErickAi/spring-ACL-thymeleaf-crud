package com.er.acl.acl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import static com.er.acl.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ACL конфигурация ЗАПРЕЩАЕТ пользователю с ролью")
class AclSecurityDenyTest extends AbstractAclSecurityIntegrationTest {

    @Test
    @DisplayName("'SOMEONE' просматривать все сообщения (Возвращает пустой список)")
    @WithMockUser(roles = {"SOMEONE"})
    void getAll() {
        assertTrue(repository.count() > 0);
        assertTrue(service.getAll().isEmpty());
    }

    @Test
    @DisplayName("'GUEST' и 'SOMEONE' создвать новые сообщения")
    @WithMockUser(roles = {"GUEST", "SOMEONE"})
    void add() {
        assertThrows(AccessDeniedException.class, () -> service.add(NEW_USER_MESSAGE));
    }

    @Test
    @DisplayName("'USER' изменять чужие сообщения")
    @WithMockUser(/*default value 'user'*/)
    void update() {
        assertThrows(AccessDeniedException.class, () -> service.update(EXISTS_ADMIN_MESSAGE));
    }

    @Test
    @DisplayName("'USER' удалять чужие сообщения")
    @WithMockUser(/*default value 'user'*/)
    void delete() {
        assertThrows(AccessDeniedException.class, () -> service.delete(EXISTS_ADMIN_MESSAGE));
    }
}
