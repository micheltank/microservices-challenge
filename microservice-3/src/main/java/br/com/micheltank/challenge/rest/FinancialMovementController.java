package br.com.micheltank.challenge.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import br.com.micheltank.challenge.entities.FinancialMovementEntity;
import br.com.micheltank.challenge.service.FinancialMovementService;

@RestController
@RequestMapping("/api")
public class FinancialMovementController {

	private FinancialMovementService financialMovementService;
	
	public FinancialMovementController(FinancialMovementService financialMovementService) {
		this.financialMovementService = financialMovementService;
	}
	
	@PostMapping("/financialMovement")
	public ResponseEntity<FinancialMovementEntity> create(@RequestBody FinancialMovementEntity financialMovement) throws URISyntaxException {
		financialMovement = financialMovementService.save(financialMovement);
		return new ResponseEntity<FinancialMovementEntity>(financialMovement, HttpStatus.CREATED);
	}

	@ExceptionHandler(ValidationException.class)
	public final ResponseEntity<Error> handleValidationException(ValidationException ex, WebRequest request) {
		return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/financialMovement")
	public ResponseEntity<Void> update(@RequestBody FinancialMovementEntity financialMovement) throws URISyntaxException {
		if (financialMovementService.findById(financialMovement.getId()) == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		financialMovement = financialMovementService.save(financialMovement);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/financialMovement/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		financialMovementService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/financialMovement")
	public ResponseEntity<List<FinancialMovementEntity>> findAll() {
		List<FinancialMovementEntity> financialMovements = financialMovementService.findAll();
		return new ResponseEntity<List<FinancialMovementEntity>>(financialMovements, HttpStatus.OK);
	}

	@GetMapping("/financialMovement/{id}")
	public ResponseEntity<FinancialMovementEntity> findById(@PathVariable String id) {
		Optional<FinancialMovementEntity> financialMovement = financialMovementService.findById(id);
		if (financialMovement.isPresent()) {
			return new ResponseEntity<FinancialMovementEntity>(financialMovement.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
