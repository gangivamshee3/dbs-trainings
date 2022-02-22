package com.classpath.ordermicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String role;
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    @JsonIgnore
    private Set<User> users;

    public Set<User> getUsers(){
        if(this.users == null){
            this.users = new HashSet<>();
        }
        return this.users;
    }
}