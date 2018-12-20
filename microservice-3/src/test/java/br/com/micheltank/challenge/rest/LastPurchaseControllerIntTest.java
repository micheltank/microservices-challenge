package br.com.micheltank.challenge.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.micheltank.challenge.ChallengeApplication;
import br.com.micheltank.challenge.entities.LastPurchaseEntity;
import br.com.micheltank.challenge.repository.LastPurchaseRepository;
import br.com.micheltank.challenge.rest.LastPurchaseController;
import br.com.micheltank.challenge.service.LastPurchaseService;
import br.com.senior.treinamento.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChallengeApplication.class})
public class LastPurchaseControllerIntTest {

	private static final Double DEFAULT_VALUE = 10.0;
	private static final Double UPDATED_VALUE = 20.0;
	
	private static final String DEFAULT_DESCRIPTION = "MAC";
	private static final String UPDATED_DESCRIPTION = "BOOK";
	
	private static final LocalDateTime DEFAULT_DATE = LocalDateTime.now();
	private static final LocalDateTime UPDATED_DATE = LocalDateTime.of(2019, 1, 1, 1, 1);
	
	@Autowired
	private LastPurchaseRepository lastPurchaseRepository;
	
	@Autowired
	private LastPurchaseService lastPurchaseService;
	
	@Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
	
	private MockMvc restClienteMockMvc;
	
	private LastPurchaseEntity lastPurchase;
	
	@Before
	public void setup() {
		final LastPurchaseController lastPurchaseController = new LastPurchaseController(lastPurchaseService);
		this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(lastPurchaseController)
				.setMessageConverters(jacksonMessageConverter).build();
	}
	
	private static LastPurchaseEntity createEntity() {
		LastPurchaseEntity lastPurchase = new LastPurchaseEntity();
		lastPurchase.setValue(DEFAULT_VALUE);
		lastPurchase.setDescription(DEFAULT_DESCRIPTION);
		lastPurchase.setDate(DEFAULT_DATE);
		return lastPurchase;
	}
	
	@Before
	public void initTest() {
		lastPurchase = createEntity();
	}
	
	@Test
	public void createLastPurchase() throws Exception {
		long quantityRecordBeforeCreate = lastPurchaseRepository.count();
		
		restClienteMockMvc.perform(post("/api/lastPurchase")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(lastPurchase)))
			.andExpect(status().isCreated());
		
		long quantityRecordsAfterCreate = lastPurchaseRepository.count();
		
		assertThat(quantityRecordsAfterCreate).isEqualTo(quantityRecordBeforeCreate + 1);
		assertThat(lastPurchase.getValue()).isEqualTo(DEFAULT_VALUE);
		assertThat(lastPurchase.getDate()).isEqualTo(DEFAULT_DATE);
	}
	
	@Test
	public void findLastPurchase() throws Exception {
		lastPurchaseService.save(lastPurchase);
		
		restClienteMockMvc.perform(get("/api/lastPurchase"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.[*].id").value(hasItem(lastPurchase.getId())))
			.andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
			.andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
			.andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
	}
	
	@Test
	public void findLastPurchaseById() throws Exception {
		lastPurchase = lastPurchaseService.save(lastPurchase);
		
		restClienteMockMvc.perform(get("/api/lastPurchase/{id}", lastPurchase.getId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id").value(lastPurchase.getId()))
			.andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
			.andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
			.andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
	}
	
	@Test
	public void updateLastPurchase() throws IOException, Exception {
		lastPurchase = lastPurchaseService.save(lastPurchase);
		lastPurchase.setValue(UPDATED_VALUE);
		lastPurchase.setDescription(UPDATED_DESCRIPTION);
		lastPurchase.setDate(UPDATED_DATE);
		
		restClienteMockMvc.perform(put("/api/lastPurchase")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(lastPurchase)))
			.andExpect(status().isNoContent());
		
		Optional<LastPurchaseEntity> lastPurchaseChanged = lastPurchaseService.findById(lastPurchase.getId());
		assertThat(lastPurchaseChanged.get().getValue()).isEqualTo(UPDATED_VALUE);
		assertThat(lastPurchaseChanged.get().getDescription()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(lastPurchaseChanged.get().getDate()).isEqualTo(UPDATED_DATE);
	}
	
	@Test
	public void deleteLastPurchase() throws Exception {
		lastPurchase = lastPurchaseService.save(lastPurchase);
		
		long quantityRecordsBeforeDelete = lastPurchaseRepository.count();
		
		restClienteMockMvc.perform(delete("/api/lastPurchase/{id}", lastPurchase.getId()))
			.andExpect(status().isNoContent());

		long quantityRecordsAfterDelete = lastPurchaseRepository.count();
		
		assertThat(quantityRecordsAfterDelete).isEqualTo(quantityRecordsBeforeDelete - 1);		
	}
}
