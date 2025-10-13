package com.grupo4.restaurante.services;

import com.grupo4.restaurante.entities.Usuario;
import com.grupo4.restaurante.entities.VerificationToken;
import com.grupo4.restaurante.repositories.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {
    private final VerificationTokenRepository tokenRepository;

    public void createTokenForUser(Usuario user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);
    }

    public Optional<Usuario> validateVerificationToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> t.getExpiryDate().isAfter(LocalDateTime.now()))
                .map(VerificationToken::getUser);
    }
}
