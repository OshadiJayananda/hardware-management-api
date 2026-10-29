package com.hw.hardwaremanagement.user;

import com.hw.hardwaremanagement.user.dto.UserRegistrationRequest;
import com.hw.hardwaremanagement.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public UserResponse registerCustomer(UserRegistrationRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "An account with this email already exists");
        }

        User user = new User();

        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));
        user.setPhone(
                request.getPhone() == null
                        || request.getPhone().isBlank()
                                ? null
                                : request.getPhone().trim());

        // Public registration always creates a customer.
        user.setRole(Role.CUSTOMER);
        user.setStatus("ACTIVE");

        User savedUser = userRepository.save(user);

        return new UserResponse(savedUser);
    }
}