package cz.czu.nick.chess.backend.service;

import cz.czu.nick.chess.backend.model.User;
import cz.czu.nick.chess.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

//    public User find() {}

    public String validateUser(User user) {
//        userRepository.save(user);
        User foundUser = userRepository.findUserByUsername(user.getUsername());
        if (foundUser == null) {
            return null;
        } else {
            if (user.getPasswordHash().equals(foundUser.getPasswordHash())) {
                return foundUser.getUsername();
            } else {
                return null;
            }
        }
    }
}
