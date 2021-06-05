package qna.domain.wrappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserNameTest {

    @Test
    void 이름_원시값_포장_객체_생성() {
        UserName userName = new UserName("권민욱");
        assertThat(userName).isEqualTo(new UserName("권민욱"));
    }

    @Test
    void 이름_길이가_20_초과_하는_경우_에러_발생() {
        String name ="권민욱권민욱권민욱권민욱권민욱권민욱권민욱";
        assertThatThrownBy(() -> new UserName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 가능한 길이는 20자 이하만 입력 가능합니다.");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void 이름이_null_이나_비어있는_문자열이_입력_하는_경우_에러_발생(String password) {
        assertThatThrownBy(() -> new UserName(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력값이 입력되지 않았습니다.");
    }
}
