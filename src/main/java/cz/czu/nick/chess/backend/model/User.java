package cz.czu.nick.chess.backend.model;

import cz.czu.nick.chess.app.security.SecurityUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "User")
public class User {

    @Id
    private String username;
    @NotEmpty
    private String passwordHash;

    public void setPasswordHash(String password) {
        passwordHash = SecurityUtils.encoder().encode(password);
    }
}
