package pl.piwowarski.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    @NotEmpty(message = "{task.description.notEmpty}")
    @Column(length = 1200)
    @Size(max = 1200, message = "{task.description.required.size}")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private LocalDate dueDate;

    @ManyToMany
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignedUsers;

    @NotEmpty(message = "{task.name.notEmpty}")
    private String name;

    @NotNull(message = "{task.date.notNull}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private boolean isCompleted;

    private String creatorName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Fetch(value = FetchMode.SELECT)
    private Set<User> owners = new HashSet<>();

    public Task(@NotEmpty String name,
                @NotEmpty @Size(max = 1200) String description,
                @NotNull LocalDate date,
                String creatorName) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.isCompleted = false;
        this.creatorName = creatorName;
    }

    public Task(@NotEmpty String name,
                @NotEmpty @Size(max = 1200) String description,
                @NotNull LocalDate date,
                String creatorName,
                User owner) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.isCompleted = false;
        this.creatorName = creatorName;
        this.owners.add(owner);
    }


    public void addOwner(User owner) {
        if (owners == null) {
            owners = new HashSet<>();
        }
        owners.add(owner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return isCompleted == task.isCompleted &&
                Objects.equals(id, task.id) &&
                name.equals(task.name) &&
                description.equals(task.description) &&
                date.equals(task.date) &&
                Objects.equals(creatorName, task.creatorName) &&
                Objects.equals(owners, task.getOwners());
    }

}
