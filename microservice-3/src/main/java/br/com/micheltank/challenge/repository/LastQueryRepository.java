package br.com.micheltank.challenge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.micheltank.challenge.entities.LastQueryEntity;

@Repository
public interface LastQueryRepository extends MongoRepository<LastQueryEntity, String> {

} 
