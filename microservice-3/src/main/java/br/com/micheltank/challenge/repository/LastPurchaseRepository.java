package br.com.micheltank.challenge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.micheltank.challenge.entities.LastPurchaseEntity;

@Repository
public interface LastPurchaseRepository extends MongoRepository<LastPurchaseEntity, String> {

} 
