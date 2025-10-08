package pl.piwowarski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.piwowarski.Dto.UserDto;
import pl.piwowarski.mapper.UserMapper;
import pl.piwowarski.model.Role;
import pl.piwowarski.model.User;
import pl.piwowarski.repository.RoleRepository;
import pl.piwowarski.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long userId) {
        return Optional.of(userRepository.findById(userId).get());
    }

    public void deleteUser(Long id) {
        User user = userRepository.getOne(id);
        user.setTasksOwned(null);
        userRepository.delete(user);
    }

    public User createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role commonUser = roleRepository.findByRole("COMMON_USER");
        user.setRoles(new ArrayList<>(Collections.singletonList(commonUser)));
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public User setRoleAsAdmin(User user) {
        Role adminUser = roleRepository.findByRole("ADMIN_USER");
        user.setRoles(new ArrayList<>(Collections.singletonList(adminUser)));
        return userRepository.save(user);
    }

    public List<User> findAllUsersByKeyword(String keyword) {
        if (keyword != null) {
            return userRepository.search(keyword);
        }
        /** .findAll() used as simplification
         * It is acknowledged that in more extensive project
         * it would cause a performance vulnerability
         * */
        return userRepository.findAll();
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}
