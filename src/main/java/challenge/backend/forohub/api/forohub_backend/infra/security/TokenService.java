package challenge.backend.forohub.api.forohub_backend.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import challenge.backend.forohub.api.forohub_backend.domain.user.UserEntity;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;

    public String generateToken(UserEntity user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);

            return JWT.create()
                .withIssuer("ForoHub")
                .withSubject(user.getName())
                .withClaim("id", user.getId())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException();
        }
    }

    public String verifyToken(String token){
        DecodedJWT verifier = null;

        if(token.isBlank() || token == null){
            throw new RuntimeException("No se ha enviado el token");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                .withIssuer("ForoHub")
                .build()
                .verify(token);

            verifier.getSubject();
        } catch (JWTVerificationException e) {
            System.out.println(e.toString());
        }

        if(verifier.getSubject() == null){
            throw new RuntimeException("No se ha podido verificaar el token");
        }

        return verifier.getSubject();
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("Z"));
    }
}
