package cz.czu.nick.chess.backend.service;

import cz.czu.nick.chess.backend.model.User;
import cz.czu.nick.chess.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean existsUsersByUser(User user) {
        return userRepository.existsUsersByUsername(user.getUsername());
    }
}
