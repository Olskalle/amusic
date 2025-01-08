package yap2.indi.amusic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yap2.indi.amusic.models.Artist;
import yap2.indi.amusic.models.Genre;
import yap2.indi.amusic.models.dtos.ArtistInfoDto;
import yap2.indi.amusic.repositories.GenreRepository;
import yap2.indi.amusic.services.ArtistService;

import java.util.List;
import java.util.Optional;

@Controller
public class ArtistController {

    private final ArtistService artistService;
    private final GenreRepository genreRepository;

    @Autowired
    public ArtistController(ArtistService artistService, GenreRepository genreRepository) {
        this.artistService = artistService;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/artists")
    public String getAllArtists(Model model) {
        List<Artist> artists = artistService.getAllArtists();
        model.addAttribute("artists", artists);
        return "artists";
    }

    @GetMapping("/artists/info")
    public String getArtistInfo(@RequestParam Long id, Model model) {
        ArtistInfoDto dto = artistService.getArtistInfo(id);
        model.addAttribute("info", dto);
        return "artist-info";
    }

    @GetMapping("/artists/edit")
    public String editArtistForm(@RequestParam Long id, Model model) {
        Optional<Artist> artistOptional = artistService.getArtistById(id);
        if (!artistOptional.isPresent()) {
            return "redirect:/error";
        }

        model.addAttribute("artist", artistOptional.get());

        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);

        return "artist-edit";
    }

    @PostMapping("/artists/edit")
    public String editArtist(@RequestParam Long id, @ModelAttribute Artist artist) {
        artistService.updateArtist(id, artist);
        return "redirect:/artists";
    }

    @PostMapping("/artists/delete")
    public String deleteArtist(@RequestParam Long id) {
        artistService.deleteArtist(id);
        return "redirect:/artists";
    }

    @PostMapping("/artists/add")
    public String addArtist(@ModelAttribute Artist artist) {
        artistService.addArtist(artist);
        return "redirect:/artists";
    }

    @GetMapping("/artists/add")
    public String getAddArtistView(Model model) {
        Artist artist = new Artist();
        model.addAttribute("artist", artist);

        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);

        return "artist-add";
    }
}
