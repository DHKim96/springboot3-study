import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JunitQuiz {

    @DisplayName("기본 테스트")
    @Test
    public void junitTest() {

        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길은";

//        1. 모든 변수가 null 이 아닌지 확인
        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name3).isNotNull();
//        2. name1 과 name2 가 같은지 확인
        assertThat(name1).isEqualTo(name2);
//        3. name1 과 name3 가 다른지 확인
        assertThat(name1).isNotEqualTo(name3);
    }

    @DisplayName("기본 테스트2")
    @Test
    public void junitTest2() {
        int num1 = 15;
        int num2 = 0;
        int num3 = -5;
        
//        1. num1 양수인지 확인
        assertThat(num1).isPositive();
//        2. num2 0인지 확인
        assertThat(num2).isZero();
//        3. num3 음수인지 확인
        assertThat(num3).isNegative();
//        4. num1 > num2 인지 확인
        assertThat(num1).isGreaterThan(num2);
//        5. num3 < num2 인지 확인
        assertThat(num3).isLessThan(num2);
    }

}
