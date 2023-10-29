package pl.piwowarski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piwowarski.model.Role;
import pl.piwowarski.repository.RoleRepository;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role setUpNewRole(Role role) {
        return roleRepository.save(role);
    }
}
