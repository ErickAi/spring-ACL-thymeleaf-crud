package com.er.acl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "system_message")
public class NoticeMessage {

    public NoticeMessage(String content) {
        this(null, content);
    }
    public NoticeMessage(NoticeMessage copy) {
        this.id = copy.getId();
        this.content = copy.getContent();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;
}
