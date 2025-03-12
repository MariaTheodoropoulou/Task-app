package gr.aueb.cf.taskapp.authentication;

import gr.aueb.cf.taskapp.core.exceptions.UserNotAuthorizedException;
import gr.aueb.cf.taskapp.dto.AuthenticationRequestDTO;
import gr.aueb.cf.taskapp.dto.AuthenticationResponseDTO;
import gr.aueb.cf.taskapp.model.User;
import gr.aueb.cf.taskapp.repository.UserRepository;
import gr.aueb.cf.taskapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO dto) throws UserNotAuthorizedException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UserNotAuthorizedException("User not authorized"));

        String token = jwtService.generateToken(authentication.getName(), user.getRole().name());

        return new AuthenticationResponseDTO(user.getFirstname(), user.getLastname(), token);
    }
}
