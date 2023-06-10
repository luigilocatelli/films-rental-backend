package com.training.service;


import com.training.models.Customer;
import com.training.models.security.UserDTO;
import com.training.models.security.UserRoleDTO;
import com.training.models.security.request.RegisterRequest;
import com.training.models.security.response.RegisterResponse;
import com.training.repos.CustomerRepository;
import com.training.repos.RegisterRepository;
import com.training.utils.Functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final PasswordEncoder passwordEncoder;
    private final RegisterRepository registerRepository;
    private final CustomerRepository customerRepository;

    public RegisterServiceImpl(@Autowired PasswordEncoder passwordEncoder, @Autowired RegisterRepository registerRepository, @Autowired CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.registerRepository = registerRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public RegisterResponse doRegister(RegisterRequest registerRequest) {
        Optional<UserDTO> isUserInDb = registerRepository.findByUsername(registerRequest.getUsername());
        if (isUserInDb.isPresent()) {
            throw new RuntimeException("User has already in db");
        }

        UserDTO newUser = mapNewRegisterToNewUser(registerRequest);
        registerRepository.save(newUser);
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setRegistered(true);
        registerResponse.setMesssage("User was saved");
        return registerResponse;
    }

    private UserDTO mapNewRegisterToNewUser(RegisterRequest registerRequest) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(registerRequest.getUsername());
        userDTO.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Customer customer = new Customer();
        customer.setStoreId(1L); //TODO: rendere store dinamico
        customer.setFirstName(registerRequest.getName());
        customer.setLastName(registerRequest.getSurname());
        customer.setEmail(registerRequest.getEmail());
        customer.setActive(1L);
        customer.setAddressId(1L); //TODO: valorizzare indirizzo su customer da form registrazione
        customer.setCreateDate(new Date());
        customer.setLastUpdate(Functions.convertDateToLocalDate(new Date()));

        customerRepository.save(customer);

        userDTO.setCustomer(customer);

        UserRoleDTO basicRole = new UserRoleDTO();
        basicRole.setRole("ROL_BASIC");
        basicRole.setUserDTO(userDTO);

        userDTO.getUserRoles().add(basicRole);
        return userDTO;
    }
}
