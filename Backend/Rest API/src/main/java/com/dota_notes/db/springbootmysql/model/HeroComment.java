package com.dota_notes.db.springbootmysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HeroComment {

    @Id
    @GeneratedValue
    @Column(name = "hero_comment_id")
    private Integer link_id;

    @Column(name = "steam_id")
    private String steam_id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "recipient_hero_id")
    private String recipient_hero_id;

    public HeroComment() {

    }

    public Integer getLink_id() {
        return link_id;
    }

    public void setLink_id(Integer link_id) {
        this.link_id = link_id;
    }

    public String getSteam_id() {
        return steam_id;
    }

    public void setSteam_id(String steam_id) {
        this.steam_id = steam_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRecipient_hero_id() {
        return recipient_hero_id;
    }

    public void setRecipient_hero_id(String recipient_hero_id) {
        this.recipient_hero_id = recipient_hero_id;
    }
}
