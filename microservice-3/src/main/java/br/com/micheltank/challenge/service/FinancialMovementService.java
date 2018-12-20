package br.com.micheltank.challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.micheltank.challenge.entities.FinancialMovementEntity;
import br.com.micheltank.challenge.repository.FinancialMovementRepository;

@Service
public class FinancialMovementService {

	@Autowired
	private FinancialMovementRepository financialMovementRepository;
	
	public FinancialMovementEntity save(FinancialMovementEntity financialMovement) {
		return financialMovementRepository.save(financialMovement);
	}

	public Optional<FinancialMovementEntity> findById(String id) {
		return financialMovementRepository.findById(id);
	}

	public void delete(String id) {
		Optional<FinancialMovementEntity> financialMovement = financialMovementRepository.findById(id);
		if (financialMovement.isPresent()) {
			financialMovementRepository.delete(financialMovement.get());
		}
	}

	public List<FinancialMovementEntity> findAll() {
		return financialMovementRepository.findAll();
	}

}
