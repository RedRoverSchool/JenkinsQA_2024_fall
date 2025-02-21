package school.redrover.models.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class User {
    private String username;
    private String password;
    private String email;
}
