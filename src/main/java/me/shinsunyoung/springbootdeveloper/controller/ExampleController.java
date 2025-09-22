package me.shinsunyoung.springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {

    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) {
        // Model : view 로 데이터를 넘겨주는 객체
        // 따로 생성할 필요없이 코드처럼 인자로 선언하기만 하면 스프링이 알아서 만들어줌

        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동", "독서"));

        model.addAttribute("person", examplePerson);
        model.addAttribute("today", LocalDate.now());

        return "example"; // example.html 라는 뷰 조회
        // 스프링 부트는 @Controller 애너테이션을 통해 반환하는 값의 이름을 가진 뷰의 파일을 찾으라는 것임을 앎
    }

    @Setter
    @Getter
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
