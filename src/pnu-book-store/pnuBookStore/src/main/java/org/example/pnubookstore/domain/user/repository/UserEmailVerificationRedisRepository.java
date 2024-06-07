package org.example.pnubookstore.domain.user.repository;

import java.util.Optional;
import org.example.pnubookstore.domain.user.entity.EmailVerification;
import org.springframework.data.repository.CrudRepository;

public interface UserEmailVerificationRedisRepository extends CrudRepository<EmailVerification, String> {
    EmailVerification findByUuid(String uuid);
}
