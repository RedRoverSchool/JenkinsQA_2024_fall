package school.redrover.testdata;

import school.redrover.models.user.User;

import java.util.UUID;

public class TestData {

    private static final String USER_NAME = "user_" + UUID.randomUUID().toString().substring(0, 8);

    public static final User USER = User.builder()
            .username(USER_NAME)
            .password("Pass@" + UUID.randomUUID().toString().substring(0, 6))
            .email(USER_NAME + "@example.com").build();

}
