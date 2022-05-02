package com.game.service;

import com.game.entity.PlayerDTO;
import com.game.entity.PlayerPage;
import com.game.entity.PlayerSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlayerService {

    List<PlayerDTO> getPlayers(PlayerPage playerPage,
                               PlayerSearchCriteria playerSearchCriteria);

    Integer getCount(PlayerSearchCriteria playerSearchCriteria);

    PlayerDTO createPlayer(PlayerDTO player);

    PlayerDTO getPlayer(Long id);

    PlayerDTO updatePlayer(PlayerDTO player);

    void deletePlayer(Long id);
}
