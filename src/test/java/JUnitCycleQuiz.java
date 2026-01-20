import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitCycleQuiz {

//    각 테스트 케이스 시작 전 "Hello!" 출력하는 메서드와
//    모든 테스트 종료 후 "Bye!" 출력하는 메서드 추가하기

    @BeforeEach
    public void beforeEach() {
        System.out.println("Hello!");
    }

    @AfterAll
    public static void AfterAll() { // static 잊지마!
        System.out.println("Bye!");
    }

    @Test
    public void junitTest3(){
        System.out.println("This is first test");
    }

    @Test
    public void junitTest4(){
        System.out.println("This is second test");
    }

}
