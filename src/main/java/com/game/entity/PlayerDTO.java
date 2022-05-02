package com.game.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "player")
public class PlayerDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length=12)
    private String name;

    @Column(name = "title", length=30)
    private String title;

    @Column(name = "race")
    @Enumerated(EnumType.STRING)
    private Race race;

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private Profession profession;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "banned")
    private Boolean banned;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "level")
    private Integer level;

    @Column(name = "untilNextLevel")
    private Integer untilNextLevel;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDTO playerDTO = (PlayerDTO) o;
        return Objects.equals(id, playerDTO.id) && Objects.equals(name, playerDTO.name) && Objects.equals(title, playerDTO.title) && race == playerDTO.race && profession == playerDTO.profession && Objects.equals(birthday, playerDTO.birthday) && Objects.equals(banned, playerDTO.banned) && Objects.equals(experience, playerDTO.experience) && Objects.equals(level, playerDTO.level) && Objects.equals(untilNextLevel, playerDTO.untilNextLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, title, race, profession, birthday, banned, experience, level, untilNextLevel);
    }

    @Override
    public String toString() {
        return "id: " + id +
                " name: " + name +
                " title: " + title +
                " race: " + race +
                " profession: " + profession +
                " birthday: " + birthday +
                " banned: " + banned +
                " experience: " + experience +
                " level: " + level +
                " untilNextLevel: " + untilNextLevel;
    }
}
