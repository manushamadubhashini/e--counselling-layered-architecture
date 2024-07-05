package lk.ijse.eCounselling.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class User {
    private String userId;
    private String userName;
    private String userType;
    private String password;
}
