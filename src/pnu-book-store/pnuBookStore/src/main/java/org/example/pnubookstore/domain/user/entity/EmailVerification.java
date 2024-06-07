package org.example.pnubookstore.domain.user.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value="email", timeToLive = 3600)
@Getter
public class EmailVerification {

    @Id
    private String uuid;
    private String email;

    public EmailVerification(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }
}
