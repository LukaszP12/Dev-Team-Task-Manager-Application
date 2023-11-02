package pl.piwowarski.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Email(message = "{user.email.notValid}")
    @NotEmpty(message = "{user.email.notEmpty}")
    @Column(unique = true)
    private String email;
    @NotEmpty(message = "{user.name.notEmpty}")
    private String name;
    @NotEmpty(message = "{user.password.notEmpty}")
    @Length(min = 5, message = "{user.password.required.length}")
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_task",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    @Fetch(value = FetchMode.SELECT)
    private Set<Task> tasksOwned;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public List<Task> getTasksCompleted() {
        return tasksOwned.stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    public List<Task> getTasksInProgress() {
        return tasksOwned.stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }

    public User(@Email @NotEmpty String email,
                @NotEmpty String name) {
        this.email = email;
        this.name = name;
    }

    public User(@Email @NotEmpty String email,
                @NotEmpty String name,
                @NotEmpty @Length(min = 5) String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User(@Email @NotEmpty String email,
                @NotEmpty String name,
                @NotEmpty @Length(min = 5) String password,
                Set<Task> tasksOwned,
                List<Role> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.tasksOwned = tasksOwned;
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                email.equals(user.email) &&
                name.equals(user.name) &&
                password.equals(user.password) &&
                Objects.equals(tasksOwned, user.tasksOwned) &&
                Objects.equals(roles, user.roles);
    }

}
