package com.dota_notes.db.springbootmysql.repository;

import com.dota_notes.db.springbootmysql.model.HeroComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HeroCommentRepository extends JpaRepository<HeroComment, Integer> {

    @Query(value= "SELECT * FROM dota_notes.hero_comment WHERE steam_id = :steam_id", nativeQuery = true)
    List<HeroComment> getHeroCommentsBySteamID(@Param("steam_id") String steam_id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO dota_notes.hero_comment (comment, recipient_hero_id, steam_id)" +
            "VALUES (:comment, :recipient_hero_id, :steam_id)", nativeQuery = true)
        void addHeroComment(@Param("comment") String match_details,
                            @Param("recipient_hero_id") String steam_id,
                            @Param("steam_id") String hash_key);
}
