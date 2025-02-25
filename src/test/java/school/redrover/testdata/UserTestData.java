package school.redrover.testdata;

import lombok.Getter;
import lombok.Setter;
import school.redrover.models.user.User;

import java.util.UUID;

public class UserTestData {

    private static final String USER_NAME = "user_" + UUID.randomUUID().toString().substring(0, 8);

    @Setter
    @Getter
    private static User defaultUser = User.builder()
            .username(USER_NAME)
            .password("Pass@" + UUID.randomUUID().toString().substring(0, 6))
            .email(USER_NAME + "@example.com").build();

}
