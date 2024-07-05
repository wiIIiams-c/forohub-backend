package challenge.backend.forohub.api.forohub_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import challenge.backend.forohub.api.forohub_backend.domain.services.UserEntityService;
import challenge.backend.forohub.api.forohub_backend.domain.user.DataAuthenticationUser;
import challenge.backend.forohub.api.forohub_backend.domain.user.DataRegisterUser;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserEntity;
import challenge.backend.forohub.api.forohub_backend.infra.security.DataJwtToken;
import challenge.backend.forohub.api.forohub_backend.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authManager;
    private TokenService tokenService;
    private UserEntityService userEntityService;

    @Autowired
    public AuthController(AuthenticationManager authManager, TokenService tokenService, UserEntityService userEntityService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.userEntityService = userEntityService;
    }

    @PostMapping("/login")
    public ResponseEntity authUser(@RequestBody @Valid DataAuthenticationUser dataAuthUser){
        Authentication authToken = new UsernamePasswordAuthenticationToken(dataAuthUser.email(), dataAuthUser.password());
        var userAuthenticated = authManager.authenticate(authToken);
        var jwtToken = tokenService.generateToken((UserEntity) userAuthenticated.getPrincipal());

        return ResponseEntity.ok(new DataJwtToken(jwtToken));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity registerUser(@RequestBody @Valid DataRegisterUser dataRegisterUser, UriComponentsBuilder uriBuilder){
        var response = userEntityService.registerUser(dataRegisterUser);

        if(Boolean.FALSE.equals(response)){
            return ResponseEntity.badRequest().body("Email ya esta en uso...");
        }

        return ResponseEntity.ok("Usuario ha sido creado...");
    }
}
