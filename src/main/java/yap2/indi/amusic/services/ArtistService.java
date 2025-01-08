package yap2.indi.amusic.services;

import yap2.indi.amusic.models.Album;
import yap2.indi.amusic.models.Artist;
import yap2.indi.amusic.models.Genre;
import yap2.indi.amusic.models.Song;
import yap2.indi.amusic.models.dtos.ArtistInfoDto;
import yap2.indi.amusic.repositories.AlbumRepository;
import yap2.indi.amusic.repositories.ArtistRepository;
import yap2.indi.amusic.repositories.GenreRepository;
import yap2.indi.amusic.repositories.SongRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    
    @Autowired
    public ArtistService(ArtistRepository artistRepository, SongRepository songRepository,
            AlbumRepository albumRepository, GenreRepository genreRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.genreRepository = genreRepository;
    }
    
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }

    public Artist createArtist(Artist user) {
        return artistRepository.save(user);
    }

    public Artist updateArtist(Long id, Artist user) {
        if (artistRepository.existsById(id)) {
            user.setId(id);
            return artistRepository.save(user);
        }
        return null;
    }

    public ArtistInfoDto getArtistInfo(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

        List<Album> albums = albumRepository.findByArtistId(id);
        List<Song> songs = songRepository.findByArtistId(id);

        ArtistInfoDto artistInfoDto = new ArtistInfoDto();
        artistInfoDto.setArtist(artist);
        artistInfoDto.setAlbums(albums);
        artistInfoDto.setSongs(songs);

        return artistInfoDto;
    }

    public void addArtist(Artist artist) {
        artistRepository.save(artist);
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }
}
