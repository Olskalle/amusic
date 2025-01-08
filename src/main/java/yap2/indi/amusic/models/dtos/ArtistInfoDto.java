package yap2.indi.amusic.models.dtos;

import java.util.List;

import yap2.indi.amusic.models.Album;
import yap2.indi.amusic.models.Artist;
import yap2.indi.amusic.models.Genre;
import yap2.indi.amusic.models.Song;

public class ArtistInfoDto {
    private Artist artist;
    private List<Album> albums;
    private List<Song> songs;
    private Genre genre;

    public Genre getGenre() {
        return genre;
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    public Artist getArtist() {
        return artist;
    }
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    public List<Album> getAlbums() {
        return albums;
    }
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
    public List<Song> getSongs() {
        return songs;
    }
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
