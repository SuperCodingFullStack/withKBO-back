package study.withkbo.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.withkbo.user.dto.UserBody;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserJpaRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private static UserJpaRepository userJpaRepository;

    public User signUp(UserBody userBody) {
        
    }
}
