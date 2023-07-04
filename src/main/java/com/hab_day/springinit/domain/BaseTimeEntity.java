package com.hab_day.springinit.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
//모든 엔티티의 상위 클래스가 되어 entity들의 createdDate와 modifedDate를 자동 관리한다.(이거 상속하면 createddate와 modifieddate도 칼럼으로 인식하게 한다.)
@EntityListeners(AuditingEntityListener.class)//BaseTimeEntity에 Auditing 기능 포함
public abstract class BaseTimeEntity {

    @CreatedDate//생성시간 자동저장
    private LocalDateTime createdDate;

    @LastModifiedDate//변경 시간 자동 저장
    private LocalDateTime modifiedDate;
}
