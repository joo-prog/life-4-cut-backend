package com.onebyte.life4cut.album.repository;

import com.onebyte.life4cut.album.domain.UserAlbum;

import java.util.Optional;

public interface UserAlbumQueryRepository {
    Optional<UserAlbum> findByUserIdAndAlbumId(Long userId, Long albumId);
}
