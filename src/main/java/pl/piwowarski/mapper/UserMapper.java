package pl.piwowarski.mapper;

import pl.piwowarski.Dto.UserDto;
import pl.piwowarski.model.User;

import java.util.List;
import java.util.stream.Collectors;


public class UserMapper {

        // Mapowanie encja -> DTO
        public static UserDto toDto(User user) {
            if (user == null) return null;

            return UserDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .build();
        }

        // Mapowanie DTO -> encja
        public static User toEntity(UserDto userDto) {
            if (userDto == null) return null;

            return User.builder()
                    .id(userDto.getId())  // przy tworzeniu może być null
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .email(userDto.getEmail())
                    .build();
        }

        // Mapowanie listy encji -> lista DTO
        public static List<UserDto> toDtoList(List<User> users) {
            return users.stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
        }
}
