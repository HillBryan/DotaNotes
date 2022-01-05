package com.dota_notes.db.springbootmysql.resource;

import com.dota_notes.db.springbootmysql.model.User;
import com.dota_notes.db.springbootmysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/rest/user")
public class UserResource {

    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "")
    public User getCardsFromDeck(@RequestParam String steam_id) {
        return userRepository.getUserBySteamID(steam_id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "add")
    public User addCard(@RequestBody Map<String, String> values) {
        userRepository.addUser(values.get("steam_id"));
        return userRepository.getUserBySteamID(values.get("steam_id"));
    }
}
