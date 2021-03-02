package com.neoflex.telegram.bot.controller.user;

import com.neoflex.telegram.bot.model.User;
import com.neoflex.telegram.bot.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rest/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Map<String, User> all() {
        return userRepository.findAll();
    }

    @GetMapping("/add/{id}/{name}")
    public User add(@PathVariable("id") final String id,
                    @PathVariable("name") final String name) {
        userRepository.save(new User(id, name, "RU"));
        return userRepository.findById(id);
    }

    @GetMapping("/update/{id}/{name}")
    public User update(@PathVariable("id") final String id,
                       @PathVariable("name") final String name) {
        userRepository.update(new User(id, name, "EUR"));
        return userRepository.findById(id);
    }

    @GetMapping("/delete/{id}")
    public Map<String, User> delete(@PathVariable("id") final String id) {
        userRepository.delete(id);
        return all();
    }

}
