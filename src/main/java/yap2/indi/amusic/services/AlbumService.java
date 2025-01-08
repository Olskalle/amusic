package yap2.indi.amusic.services;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yap2.indi.amusic.models.Album;
import yap2.indi.amusic.models.Artist;
import yap2.indi.amusic.models.dtos.ArtistInfoDto;
import yap2.indi.amusic.repositories.AlbumRepository;
import yap2.indi.amusic.repositories.ArtistRepository;

@Service
public class AlbumService extends GenericServiceImpl<Album> {
    private final ArtistRepository artistRepository;

    @Autowired
    public AlbumService(ArtistRepository artistRepository, AlbumRepository albumRepository) {
        super(albumRepository);
        this.artistRepository = artistRepository;
    }

    public List<ArtistInfoDto> getAlbumsInfo() {

        List<Album> albums = repository.findAll();

        return albums.stream()
                .collect(Collectors.groupingBy(
                        Album::getArtist,
                        Collectors.toList()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Artist::getName)))
                .map(entry -> {
                    Artist artist = entry.getKey();
                    List<Album> artistAlbums = entry.getValue();

                    artistAlbums.sort(Comparator.comparing(Album::getReleaseYear));

                    var dto = new ArtistInfoDto();
                    dto.setAlbums(artistAlbums);
                    dto.setArtist(artist);

                    return dto;
                })
                .collect(Collectors.toList());
    }
}
