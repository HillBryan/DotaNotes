package com.dota_notes.db.springbootmysql.resource;

import com.dota_notes.db.springbootmysql.model.HeroComment;
import com.dota_notes.db.springbootmysql.model.UserComment;
import com.dota_notes.db.springbootmysql.repository.HeroCommentRepository;
import com.dota_notes.db.springbootmysql.repository.UserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/userComment")
public class UserCommentResource {

    @Autowired
    UserCommentRepository userCommentRepository;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "")
    public List<UserComment> getUserComment(@RequestParam String steam_id) {
        return userCommentRepository.getUserCommentsBySteamID(steam_id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/add")
    public List<UserComment> addUserComment(@RequestBody Map<String, String> values) {
        userCommentRepository.addUserComment(values.get("comment"),
                                             values.get("recipient_steam_id"),
                                             values.get("steam_id"));
        return userCommentRepository.getUserCommentsBySteamID(values.get("steam_id"));
    }
}
