package br.com.micheltank.challenge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.micheltank.challenge.entities.FinancialMovementEntity;

@Repository
public interface FinancialMovementRepository extends MongoRepository<FinancialMovementEntity, String> {

} 
