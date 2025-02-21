package school.redrover.models.user;

import lombok.Data;

import java.util.List;

@Data
public class AllUsersResponse {
    private List<User> users;

    @Data
    public static class User {
        private String id;
        private String name;
        private String email;
    }

}
