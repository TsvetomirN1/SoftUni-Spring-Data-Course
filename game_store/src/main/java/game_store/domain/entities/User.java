package game_store.domain.entities;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String email;
    private String password;
    private String fullName;
    private List<Game> games;
    private Role role;

    public User() {
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games=games;
    }

    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
