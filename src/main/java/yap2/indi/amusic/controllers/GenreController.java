package yap2.indi.amusic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yap2.indi.amusic.models.Genre;
import yap2.indi.amusic.models.dtos.GenreList;
import yap2.indi.amusic.services.GenreService;

@Controller
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String getGenres(Model model) {
        var genres = genreService.findAll();
        var genreList = new GenreList(genres);
        genreList.addGenreFirst(new Genre());
        model.addAttribute("genres", genreList);

        return "genres";
    }

    @PostMapping("/genres/add")
    public String addGenre(@ModelAttribute Genre genre, Model model) {
        genreService.save(genre);

        return getGenres(model);
    }
    
    @PostMapping("/genres/edit")
    public String editGenre(@RequestParam Long id, @ModelAttribute Genre genre, Model model) {
        var exists = genreService.findById(id) != null;

        if (exists){
            genreService.update(genre);
        }

        return "redirect:/" + getGenres(model);
    }

    @PostMapping("/genres/delete")
    public String deleteGenre(@RequestParam Long id, Model model) {
        var exists = genreService.findById(id) != null;

        if (exists){
            genreService.delete(id);;
        }

        return "redirect:/" + getGenres(model);
    }

    @PostMapping("/genres/save")
    public String saveAll(@ModelAttribute GenreList genreList, Model model) {
        genreService.saveAll(genreList.getInner());

        return "redirect:/" + getGenres(model);
    }
}
