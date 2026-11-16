package com.microservice.userservice.services;

import com.microservice.userservice.dto.UserRequest;
import com.microservice.userservice.dto.UserResponse;
import com.microservice.userservice.entity.User;
import com.microservice.userservice.exception.DuplicateEmailException;
import com.microservice.userservice.exception.UserNotFoundException;
import com.microservice.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;


    @Transactional
    public UserResponse createUser ( UserRequest request){
        String email = normalizeEmail(request.email());
        if (userRepository.existsByEmail(email)){
            throw new RuntimeException("User already exists with email :"+email);
        }
        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(request.email());
        user.setPhone(request.phone());

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);

    }

    // get all user

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // get user by id

    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()->
                        new UserNotFoundException("User not found with id : "+id));

        return mapToResponse(user);

    }

    // update user

    @Transactional
    public UserResponse updateUser(Long id,UserRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(()->
                        new UserNotFoundException("User already exists with email : "+id));
        String email = normalizeEmail(request.email());
        if (userRepository.existsByEmailAndIdNot(email,id)){
            throw new DuplicateEmailException("Email already used by another user :"+email);
        }

        user.setName(request.name().trim());
        user.setEmail(request.email());
        user.setPhone(request.phone());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);

    }

    @Transactional
    public void deleteUser(Long id){
        User  user = userRepository.findById(id)
                .orElseThrow(()->
                        new UserNotFoundException("User not found with id "+id));
        userRepository.delete(user);

    }
    private String normalizeEmail (String email){
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private UserResponse mapToResponse(User user){
        return  new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
