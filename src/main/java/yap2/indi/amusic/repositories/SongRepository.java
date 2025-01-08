package yap2.indi.amusic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yap2.indi.amusic.models.Song;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    
    List<Song> findByArtistId(Long artistId);
    
    List<Song> findByTitleContaining(String name); 
    
    List<Song> findByAlbumId(Long albumId);
}
