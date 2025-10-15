package me.shinsunyoung.springbootdeveloper.repository;

import me.shinsunyoung.springbootdeveloper.domain.RefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository {
    Optional<RefreshToken> findByUserId(Long userId); // Optional로 감싸서 null-safety를 보장
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
