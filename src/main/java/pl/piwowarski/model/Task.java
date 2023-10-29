package pl.piwowarski.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @NotEmpty(message = "{task.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{task.description.notEmpty}")
    @Column(length = 1200)
    @Size(max = 1200, message = "{task.description.required.size}")
    private String description;

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

    public Task() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Set<User> getOwners() {
        return owners;
    }

    public void setOwners(Set<User> owners) {
        this.owners = owners;
    }

    public void addOwner(User owner) {
        if (owners == null) {
            owners = new HashSet<>();
        }
        owners.add(owner);
    }

    public void removeOwner(User owner) {
        getOwners().remove(owner);
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
