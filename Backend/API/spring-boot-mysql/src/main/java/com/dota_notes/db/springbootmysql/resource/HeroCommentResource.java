package com.dota_notes.db.springbootmysql.resource;

import com.dota_notes.db.springbootmysql.model.HeroComment;
import com.dota_notes.db.springbootmysql.model.Link;
import com.dota_notes.db.springbootmysql.repository.HeroCommentRepository;
import com.dota_notes.db.springbootmysql.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/heroComment")
public class HeroCommentResource {

    @Autowired
    HeroCommentRepository heroCommentRepository;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "")
    public List<HeroComment> getHeroComment(@RequestParam String steam_id) {
        return heroCommentRepository.getHeroCommentsBySteamID(steam_id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/add")
    public List<HeroComment> addHeroComment(@RequestBody Map<String, String> values) {
        heroCommentRepository.addHeroComment(values.get("comment"),
                                             values.get("recipient_hero_id"),
                                             values.get("steam_id"));
        return heroCommentRepository.getHeroCommentsBySteamID(values.get("steam_id"));
    }
}
