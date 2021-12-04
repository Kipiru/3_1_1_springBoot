package web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// Этот класс реализует интерфейс GrantedAuthority, в котором необходимо переопределить только один метод getAuthority() (возвращает имя роли).
// Имя роли должно соответствовать шаблону: «ROLE_ИМЯ», например, ROLE_USER.
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false
            , unique = true
    )
    private String role;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {
    }


    public Role(String role) {
        this.role = role;
    }

    public Role(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }



    @Override
    public String getAuthority() {
        return role;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Role)) return false;
//
//        Role role1 = (Role) o;
//
//        return role.equals(role1.role);
//    }
//
//    @Override
//    public int hashCode() {
//        return role.hashCode();
//    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//
//        if (!(o instanceof Role))
//            return false;
//
//        Role other = (Role) o;
//
//        return id != null &&
//                id.equals(other.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }


        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return id != null ? id.equals(role.id) : role.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.valueOf(role);
    }
}
