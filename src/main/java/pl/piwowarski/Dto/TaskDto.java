package pl.piwowarski.Dto;

import lombok.Getter;
import lombok.Setter;
import pl.piwowarski.model.TaskStatus;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskDto {

        private Long id;

        @NotBlank(message = "Title cannot be blank")
        private String title;

        @NotBlank(message = "Description cannot be blank")
        private String description;

        @NotNull(message = "Status is required")
        private TaskStatus status;

        @FutureOrPresent(message = "Deadline must be in the future or today")
        private LocalDate dueDate;

        private List<Long> assignedUserIds;
}
