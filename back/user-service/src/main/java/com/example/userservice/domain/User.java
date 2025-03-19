package com.example.userservice.domain;

import com.example.userservice.dto.GameResult;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    private String name;

    private String email;

    @Column(nullable = false)
    private String picture;

    private char gender;

    private String age;

    private String level;

    private int matchCount = 0;

    private int winCount = 0;

    private double winningRate = 0.0;

    private int attendanceCount = 0;

    private double attendanceRate = 0.0;

    private String accessToken;

    @ColumnDefault("1")
    private boolean genderVisible;

    @ColumnDefault("1")
    private boolean ageVisible;

    /**
     * Updates the user's name and profile picture.
     *
     * <p>This method replaces the current user's name and profile picture with the provided values,
     * returning the user instance to enable method chaining.</p>
     *
     * @param name the new name to set for the user
     * @param picture the new profile picture URL or path for the user
     * @return the updated user instance
     */
    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    /**
     * Updates the winning rate based on the provided game result.
     *
     * <p>If the game result equals <code>GameResult.WIN</code>, the win count is incremented.
     * The method then recalculates the winning rate as the percentage of wins to matches (win count divided
     * by match count multiplied by 100), rounding the result to two decimal places. Note that the match count
     * is not updated in this method and should be incremented separately.</p>
     *
     * @param gameResult the outcome of the game; a win increases the win count
     */
    public void updateWinningRate(GameResult gameResult){
        if(gameResult.equals(GameResult.WIN)) this.winCount++;
        this.winningRate = Math.round(((double) this.winCount / this.matchCount * 100) * 100) / 100.0;
    }

    /**
     * Increments the user's match count by one.
     *
     * <p>This method should be called whenever the user participates in a new match, ensuring the match count is accurately updated.</p>
     */
    public void increaseMatchCount() {
        this.matchCount++;
    }

    /**
     * Updates the user's attendance rate based on the outcome of a match.
     *
     * <p>If the provided attendance flag is true, the method increments the attendance count.
     * It then recalculates the attendance rate as the percentage of attended matches relative to the total match count,
     * rounding the result to two decimal places.
     *
     * @param attendance true if the user attended the match, false otherwise
     */
    public void updateAttendanceRate(boolean attendance) {
        if(attendance) this.attendanceCount += 1;
        this.attendanceRate = Math.round(((double) this.attendanceCount / this.matchCount * 100) * 100) / 100.0;
    }

    /**
     * Constructs a new {@code User} instance with the specified attributes.
     * <p>
     * This constructor is intended for use with the builder pattern and initializes
     * all required fields for persistence.
     * </p>
     *
     * @param userId        the unique identifier of the user
     * @param name          the name of the user
     * @param email         the user's email address
     * @param picture       the URL or file path to the user's profile picture
     * @param gender        a character representing the user's gender
     * @param age           the user's age as a string
     * @param level         the user's level
     * @param accessToken   the access token associated with the user
     * @param genderVisible flag indicating if the user's gender is visible
     * @param ageVisible    flag indicating if the user's age is visible
     */
    @Builder
    public User(UUID userId, String name, String email, String picture,
                char gender, String age, String level, String accessToken,
                boolean genderVisible, boolean ageVisible) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.gender = gender;
        this.age = age;
        this.level = level;
        this.accessToken = accessToken;
        this.genderVisible = genderVisible;
        this.ageVisible = ageVisible;
    }


}
