package yap2.indi.amusic.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yap2.indi.amusic.models.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
