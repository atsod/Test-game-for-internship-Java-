package com.game.service;

import com.game.entity.PlayerDTO;
import com.game.entity.PlayerPage;
import com.game.entity.PlayerSearchCriteria;
import com.game.repository.PlayerCriteriaRepository;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerCriteriaRepository playerCriteriaRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerCriteriaRepository playerCriteriaRepository) {
        this.playerRepository = playerRepository;
        this.playerCriteriaRepository = playerCriteriaRepository;
    }

    @Override
    public List<PlayerDTO> getPlayers(PlayerPage playerPage,
                                      PlayerSearchCriteria playerSearchCriteria) {
        return playerCriteriaRepository.findAllWithFilters(playerPage, playerSearchCriteria).getContent();
    }

    @Override
    public Integer getCount(PlayerSearchCriteria playerSearchCriteria) {
        return playerCriteriaRepository.sizeQuery(playerSearchCriteria);
    }

    @Override
    @Transactional
    public PlayerDTO createPlayer(PlayerDTO player) {
        return playerRepository.save(player);
    }

    @Override
    public PlayerDTO getPlayer(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    @Override
    public PlayerDTO updatePlayer(PlayerDTO player) {
        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
