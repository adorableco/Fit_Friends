package com.example.userservice.domain;

import com.example.userservice.dto.GameResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class UserTest {

    @Test
    @DisplayName("승리한 경기인 경우 승률이 증가한다.")
    void updateWinningRateWhenWin() {
        User user = createUser();
        int matchCount = 10;
        user.setMatchCount(matchCount);
        user.setWinCount(5);

        user.updateWinningRate(GameResult.WIN);

        Assertions.assertThat(user.getWinningRate()).isEqualTo(60.0);
    }

    @Test
    @DisplayName("패배한 경기인 경우 승률이 감소한다.")
    void updateWinningRateWhenLose() {
        User user = createUser();
        int matchCount = 50;
        user.setMatchCount(matchCount);
        user.setWinCount(5);

        user.updateWinningRate(GameResult.LOSE);

        Assertions.assertThat(user.getWinningRate()).isEqualTo(10.0);
    }

    @Test
    @DisplayName("출석률 업데이트에 성공한다.")
    void updateAttendanceRate() {
        User user = createUser();
        int matchCount = 10;
        user.setMatchCount(matchCount);
        user.setAttendanceCount(8);

        user.updateAttendanceRate(true);

        Assertions.assertThat(user.getAttendanceRate()).isEqualTo(90.0);
    }

    @Test
    @DisplayName("matchCount를 1 증가시킨다.")
    void increaseMatchCount() {
        User user = createUser();
        int matchCount = 1;
        user.setMatchCount(matchCount);

        user.increaseMatchCount();

        Assertions.assertThat(user.getMatchCount()).isEqualTo(matchCount + 1);
    }

    private User createUser() {
        return User.builder()
                .name("Alexander Iqbal")
                .email("alexanderiqbal@gmail.com")
                .level("beginner")
                .age("teens")
                .picture("Nascetur erat ex fusce.")
                .userId(UUID.randomUUID())
                .genderVisible(true)
                .ageVisible(true)
                .gender('F')
                .accessToken("Fermentum massa tellus lectus venenatis.")
                .build();
    }
}
