package cni.projet.projetpfe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String email;
    private String password;
    private Integer role; // 1 for admin, 2 for standard user
}
