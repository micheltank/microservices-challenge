package br.com.micheltank.challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.micheltank.challenge.entities.LastQueryEntity;
import br.com.micheltank.challenge.repository.LastQueryRepository;

@Service
public class LastQueryService {

	@Autowired
	private LastQueryRepository lastQueryRepository;
	
	public LastQueryEntity save(LastQueryEntity lastQuery) {
		return lastQueryRepository.save(lastQuery);
	}

	public Optional<LastQueryEntity> findById(String id) {
		return lastQueryRepository.findById(id);
	}

	public void delete(String id) {
		Optional<LastQueryEntity> lastQuery = lastQueryRepository.findById(id);
		if (lastQuery.isPresent()) {
			lastQueryRepository.delete(lastQuery.get());
		}
	}

	public List<LastQueryEntity> findAll() {
		return lastQueryRepository.findAll();
	}

}
