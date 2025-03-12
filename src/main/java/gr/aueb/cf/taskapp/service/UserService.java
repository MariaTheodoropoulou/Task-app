package gr.aueb.cf.taskapp.service;

import gr.aueb.cf.taskapp.core.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.taskapp.core.exceptions.UserNotFoundException;
import gr.aueb.cf.taskapp.dto.UserInsertDTO;
import gr.aueb.cf.taskapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.taskapp.dto.UserUpdateDTO;
import gr.aueb.cf.taskapp.mapper.Mapper;
import gr.aueb.cf.taskapp.model.User;
import gr.aueb.cf.taskapp.repository.UserRepository;
import gr.aueb.cf.taskapp.security.SecurityConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SecurityConfiguration securityConfiguration;
    private final Mapper mapper;

    public UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO) throws UserAlreadyExistsException {
        if ((userRepository.findByUsername(userInsertDTO.getUsername()).isPresent() &&
                userRepository.findByLastname(userInsertDTO.getLastname()).isPresent())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = mapper.mapToUserEntity(userInsertDTO);
        user.setPassword(securityConfiguration.passwordEncoder().encode(userInsertDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return mapper.mapToUserReadOnlyDTO(savedUser);
    }

    public UserReadOnlyDTO updateUser(UserUpdateDTO userUpdateDTO) throws UserAlreadyExistsException {

    }
    public UserReadOnlyDTO deleteUser(Long id) throws UserNotFoundException{}
    public List<UserReadOnlyDTO> getAllUsers() throws UserNotFoundException {}
    public UserReadOnlyDTO getUser(Long id) throws UserNotFoundException {}
}
