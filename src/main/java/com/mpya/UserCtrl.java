package com.mpya;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserCtrl {
    private final UserRepo repository;

    public UserCtrl(UserRepo repository) {
        this.repository = repository;
    }

    @GetMapping("/user")
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/user")
    User newEmployee(@RequestBody User newEmployee) {
        return repository.save(newEmployee);
    }


    @GetMapping("/user/{id}")
    User one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User replaceEmployee(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());

                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/user/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}