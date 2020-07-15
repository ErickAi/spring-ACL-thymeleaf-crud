package com.er.acl;

import com.er.acl.domain.NoticeMessage;

public class TestData {
    //    FROM test-data.sql
    public static final NoticeMessage EXISTS_ADMIN_MESSAGE = new NoticeMessage(1L, "New admin message");
    public static final NoticeMessage EXISTS_USER_MESSAGE_1 = new NoticeMessage(2L, "New user message first");
    public static final NoticeMessage EXISTS_USER_MESSAGE_2 = new NoticeMessage(3L, "New user message second");
    //    NEW data
    public static final NoticeMessage NEW_USER_MESSAGE = new NoticeMessage("New user test-message");

}
