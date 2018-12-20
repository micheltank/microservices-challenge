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

import br.com.micheltank.challenge.entities.LastQueryEntity;
import br.com.micheltank.challenge.service.LastQueryService;

@RestController
@RequestMapping("/api")
public class LastQueryController {

	private LastQueryService lastQueryService;
	
	public LastQueryController(LastQueryService lastQueryService) {
		this.lastQueryService = lastQueryService;
	}
	
	@PostMapping("/lastQuery")
	public ResponseEntity<LastQueryEntity> create(@RequestBody LastQueryEntity lastQuery) throws URISyntaxException {
		lastQuery = lastQueryService.save(lastQuery);
		return new ResponseEntity<LastQueryEntity>(lastQuery, HttpStatus.CREATED);
	}

	@ExceptionHandler(ValidationException.class)
	public final ResponseEntity<Error> handleValidationException(ValidationException ex, WebRequest request) {
		return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/lastQuery")
	public ResponseEntity<Void> update(@RequestBody LastQueryEntity lastQuery) throws URISyntaxException {
		if (lastQueryService.findById(lastQuery.getId()) == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		lastQuery = lastQueryService.save(lastQuery);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/lastQuery/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		lastQueryService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/lastQuery")
	public ResponseEntity<List<LastQueryEntity>> findAll() {
		List<LastQueryEntity> lastQuerys = lastQueryService.findAll();
		return new ResponseEntity<List<LastQueryEntity>>(lastQuerys, HttpStatus.OK);
	}

	@GetMapping("/lastQuery/{id}")
	public ResponseEntity<LastQueryEntity> findById(@PathVariable String id) {
		Optional<LastQueryEntity> lastQuery = lastQueryService.findById(id);
		if (lastQuery.isPresent()) {
			return new ResponseEntity<LastQueryEntity>(lastQuery.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
