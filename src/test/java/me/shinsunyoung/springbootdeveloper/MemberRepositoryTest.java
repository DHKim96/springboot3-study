package me.shinsunyoung.springbootdeveloper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers() {
        //when
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberById() {
        //when
        Member member = memberRepository.findById(1L).orElseThrow();
        //then
        assertThat(member.getName()).isEqualTo("A");
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberByName() {
        //when
        Member member = memberRepository.findByName("C").orElseThrow();
        //then
        assertThat(member.getId()).isEqualTo(3);
    }

    @Test
    void saveMember() {
        //given
        Member member = new Member(1L, "A");
        //when
        memberRepository.save(member);
        //then
        assertThat(memberRepository.findById(1L).orElseThrow().getName()).isEqualTo("A");
    }

    @Test
    void saveAllMembers() {
        //given
        List<Member> members = List.of(new Member(1L, "A"), new Member(2L, "B"));
        //when
        memberRepository.saveAll(members);
        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Sql("/insert-members.sql")
    @Test
    void deleteMemberById() {
        //when
        memberRepository.deleteById(1L);
        //then
        assertThat(memberRepository.findById(1L).isEmpty()).isTrue();
    }

    @Sql("/insert-members.sql")
    @Test
    void deleteAllMembers() {
        //when
        memberRepository.deleteAll(); // 보통은 `@AfterEach` 를 붙여 cleanUp() 메서드와 같은 형태로 사용
        //then
        assertThat(memberRepository.findAll().size()).isZero();
    }

    @Sql("/insert-members.sql")
    @Test
    void update() {
        //given
        Member member = memberRepository.findById(1L).orElseThrow();
        //when
        member.changeName("C");
        //then
        assertThat(memberRepository.findById(1L).orElseThrow().getName()).isEqualTo("C");

        //`@DataJpaTest` 애너테이션 은 `@Transactional` 포함하고 있음
    }


}