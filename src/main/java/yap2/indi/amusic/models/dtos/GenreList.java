package yap2.indi.amusic.models.dtos;

import java.util.List;

import yap2.indi.amusic.models.Genre;

public class GenreList {
    private List<Genre> inner;
    
    public GenreList() {

    }

    public GenreList(List<Genre> genres) {
        this.inner = genres;
    }

    public List<Genre> getInner() {
        return inner;
    }

    public void setInner(List<Genre> inner) {
        this.inner = inner;
    }


    public void addGenre(Genre genre) {
        inner.add(genre);
    }

    public void addGenreFirst(Genre genre) {
        inner.addFirst(genre);
    }
}
