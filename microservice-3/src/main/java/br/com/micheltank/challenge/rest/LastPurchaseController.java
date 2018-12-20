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

import br.com.micheltank.challenge.entities.LastPurchaseEntity;
import br.com.micheltank.challenge.service.LastPurchaseService;

@RestController
@RequestMapping("/api")
public class LastPurchaseController {

	private LastPurchaseService lastPurchaseService;
	
	public LastPurchaseController(LastPurchaseService lastPurchaseService) {
		this.lastPurchaseService = lastPurchaseService;
	}
	
	@PostMapping("/lastPurchase")
	public ResponseEntity<LastPurchaseEntity> create(@RequestBody LastPurchaseEntity lastPurchase) throws URISyntaxException {
		lastPurchase = lastPurchaseService.save(lastPurchase);
		return new ResponseEntity<LastPurchaseEntity>(lastPurchase, HttpStatus.CREATED);
	}

	@ExceptionHandler(ValidationException.class)
	public final ResponseEntity<Error> handleValidationException(ValidationException ex, WebRequest request) {
		return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/lastPurchase")
	public ResponseEntity<Void> update(@RequestBody LastPurchaseEntity lastPurchase) throws URISyntaxException {
		if (lastPurchaseService.findById(lastPurchase.getId()) == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		lastPurchase = lastPurchaseService.save(lastPurchase);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/lastPurchase/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		lastPurchaseService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/lastPurchase")
	public ResponseEntity<List<LastPurchaseEntity>> findAll() {
		List<LastPurchaseEntity> lastPurchases = lastPurchaseService.findAll();
		return new ResponseEntity<List<LastPurchaseEntity>>(lastPurchases, HttpStatus.OK);
	}

	@GetMapping("/lastPurchase/{id}")
	public ResponseEntity<LastPurchaseEntity> findById(@PathVariable String id) {
		Optional<LastPurchaseEntity> lastPurchase = lastPurchaseService.findById(id);
		if (lastPurchase.isPresent()) {
			return new ResponseEntity<LastPurchaseEntity>(lastPurchase.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
