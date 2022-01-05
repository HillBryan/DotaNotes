package com.dota_notes.db.springbootmysql.resource;

import com.dota_notes.db.springbootmysql.model.Link;
import com.dota_notes.db.springbootmysql.model.User;
import com.dota_notes.db.springbootmysql.repository.LinkRepository;
import com.dota_notes.db.springbootmysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/rest/link")
public class LinkResource {

    @Autowired
    LinkRepository linkRepository;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "")
    public Link getLink(@RequestParam String hash_key) {
        return linkRepository.getLinkByUser(hash_key);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/add")
    public Link addCard(@RequestBody Map<String, String> values) {
        linkRepository.addLink(values.get("match_details"),
                               values.get("steam_id"),
                               values.get("hash_key"));
        return linkRepository.getLinkByUser(values.get("hash_key"));
    }
}
