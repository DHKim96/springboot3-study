package me.shinsunyoung.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // 엔티티 생성 및 수정 시간을 자동으로 감시하고 기록
/*
* @EntityListeners
** JPA 표준 애너테이션.
** 엔티티의 생명주기 이벤트(@PrePersist, @PostPersist, @PreUpdate, @PostUpdate 등)가 발생할 때, 지정한 리스너 클래스가 콜백을 받을 수 있도록 해줌
** 즉, 엔티티의 저장, 수정, 삭제 등의 이벤트를 감지해서 자동으로 처리할 로직을 연결할 수 있는 기능

* AuditingEntityListener.class
** Spring Data JPA에서 제공하는 기본 리스너 클래스.
** @CreatedDate, @LastModifiedDate, @CreatedBy, @LastModifiedBy 같은 감사(Auditing)용 애너테이션을 자동으로 처리
** 엔티티가 저장되거나 수정될 때, 해당 필드에 현재 시간이나 사용자 정보를 자동으로 넣어줌
 */
@Getter
@Entity // 엔티티로 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate // 엔티티가 생성될 때 생성 시간
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Builder // 빌더 패턴으로 객체 생성
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) { // 엔티티의 필드값이 바뀌면 중간에 에러가 발생해도 제대로 된 값 수정을 보장
        this.title = title;
        this.content = content;
    }

}
