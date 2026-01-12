package me.shinsunyoung.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // created_at, updated_at 자동 업데이트
/*
 * Spring Data JPA에서 Auditing(감사) 기능을 전역적으로 켜는 애너테이션
 * 애플리케이션이 실행될 때 Spring이 AuditingEntityListener를 활성화해서 엔티티에 있는 @CreatedDate, @LastModifiedDate, @CreatedBy, @LastModifiedBy 같은 애너테이션이 동작하도록 함
 */
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
