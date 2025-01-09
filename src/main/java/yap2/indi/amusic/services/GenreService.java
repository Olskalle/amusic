package yap2.indi.amusic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yap2.indi.amusic.Constants;
import yap2.indi.amusic.models.Album;
import yap2.indi.amusic.models.Artist;
import yap2.indi.amusic.models.Genre;
import yap2.indi.amusic.repositories.AlbumRepository;
import yap2.indi.amusic.repositories.ArtistRepository;
import yap2.indi.amusic.repositories.GenreRepository;

@Service
public class GenreService extends GenericServiceImpl<Genre> {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public GenreService(GenreRepository repo, AlbumRepository albumRepo, ArtistRepository artistRepo) {
        super(repo);
        albumRepository = albumRepo;
        artistRepository = artistRepo;
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getName() == Constants.UNDEFINED_GENRE_NAME) {
            throw new IllegalArgumentException("Зарезервированное название жанра");
        }

        return super.save(genre);
    }

    @Override
    public Genre update(Genre genre) {
        if (genre.getName() == Constants.UNDEFINED_GENRE_NAME) {
            throw new IllegalArgumentException("Зарезервированное название жанра");
        }

        return super.save(genre);
    }

    @Override
    public void delete(Long id) {
        var genreOptional = repository.findById(id);
        
        if (!genreOptional.isPresent())
            return;

        var genre = genreOptional.get();
        
        var undefinedGenreOptional = ((GenreRepository)repository).findByName(Constants.UNDEFINED_GENRE_NAME);
        Genre undefinedGenre;
        if (!undefinedGenreOptional.isPresent()) {
            undefinedGenre = new Genre();
            undefinedGenre.setName(Constants.UNDEFINED_GENRE_NAME);
            undefinedGenre.setDescription(Constants.UNDEFINED_GENRE_NAME);
            
            undefinedGenre = repository.save(undefinedGenre);
        } else {
            undefinedGenre = undefinedGenreOptional.get();
        }
        
        var artists = genre.getArtists();
        for (Artist artist : artists) {
            artist.setGenre(undefinedGenre);
        }
        artistRepository.saveAll(artists);
        
        var albums = genre.getAlbums();
        for (Album album : albums) {
            album.setGenre(undefinedGenre);
        }
        albumRepository.saveAll(albums);

        repository.deleteById(id);
    }

    public void updateOrAdd(List<Genre> entities) {
        
    }
}