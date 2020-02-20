package org.dmace.security.demo.service;

import org.dmace.security.demo.model.UserEntity;
import org.dmace.security.demo.repo.UserRepo;
import org.dmace.security.demo.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends BaseService<UserEntity, Long, UserRepo> {

    Optional<UserEntity> findByUsername(final String username) {
        return this.repository.findByUsername(username);
    }

}
