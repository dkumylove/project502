package org.choongang.commons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMember extends Base {

    @CreatedBy
    @Column(length = 40, updatable = false)  // 업데이트할때 변경되면 안됨
    private String createdBy;

    @LastModifiedBy
    @Column(length = 40, insertable = false) // 처음등록할떄 들어가면 안됨
    private String modifiedBy;

}
