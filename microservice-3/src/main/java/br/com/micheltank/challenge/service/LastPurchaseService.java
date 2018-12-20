package br.com.micheltank.challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.micheltank.challenge.entities.LastPurchaseEntity;
import br.com.micheltank.challenge.repository.LastPurchaseRepository;

@Service
public class LastPurchaseService {

	@Autowired
	private LastPurchaseRepository lastPurchaseRepository;
	
	public LastPurchaseEntity save(LastPurchaseEntity lastPurchase) {
		return lastPurchaseRepository.save(lastPurchase);
	}

	public Optional<LastPurchaseEntity> findById(String id) {
		return lastPurchaseRepository.findById(id);
	}

	public void delete(String id) {
		Optional<LastPurchaseEntity> lastPurchase = lastPurchaseRepository.findById(id);
		if (lastPurchase.isPresent()) {
			lastPurchaseRepository.delete(lastPurchase.get());
		}
	}

	public List<LastPurchaseEntity> findAll() {
		return lastPurchaseRepository.findAll();
	}

}
