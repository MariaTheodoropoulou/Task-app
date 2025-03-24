package gr.aueb.cf.taskapp.controllers;
import gr.aueb.cf.taskapp.authentication.AuthenticationService;
import gr.aueb.cf.taskapp.core.exceptions.UserNotAuthorizedException;
import gr.aueb.cf.taskapp.dto.AuthenticationRequestDTO;
import gr.aueb.cf.taskapp.dto.AuthenticationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) throws UserNotAuthorizedException {
        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.authenticate(authenticationRequestDTO);
        LOGGER.info("User authenticated");
        return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.OK);
    }
}
