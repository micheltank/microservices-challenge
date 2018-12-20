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
import br.com.micheltank.challenge.entities.FinancialMovementEntity;
import br.com.micheltank.challenge.repository.FinancialMovementRepository;
import br.com.micheltank.challenge.rest.FinancialMovementController;
import br.com.micheltank.challenge.service.FinancialMovementService;
import br.com.senior.treinamento.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChallengeApplication.class})
public class FinancialMovementControllerIntTest {

	private static final Double DEFAULT_VALUE = 10.0;
	private static final Double UPDATED_VALUE = 20.0;
	
	private static final String DEFAULT_COMPANY = "Google";
	private static final String UPDATED_COMPANY = "Apple";
	
	@Autowired
	private FinancialMovementRepository financialMovementRepository;
	
	@Autowired
	private FinancialMovementService financialMovementService;
	
	@Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
	
	private MockMvc restClienteMockMvc;
	
	private FinancialMovementEntity financialMovement;
	
	@Before
	public void setup() {
		final FinancialMovementController financialMovementController = new FinancialMovementController(financialMovementService);
		this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(financialMovementController)
				.setMessageConverters(jacksonMessageConverter).build();
	}
	
	private static FinancialMovementEntity createEntity() {
		FinancialMovementEntity financialMovement = new FinancialMovementEntity();
		financialMovement.setValue(DEFAULT_VALUE);
		financialMovement.setCompany(DEFAULT_COMPANY);
		return financialMovement;
	}
	
	@Before
	public void initTest() {
		financialMovement = createEntity();
	}
	
	@Test
	public void createFinancialMovement() throws Exception {
		long quantityRecordBeforeCreate = financialMovementRepository.count();
		
		restClienteMockMvc.perform(post("/api/financialMovement")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(financialMovement)))
			.andExpect(status().isCreated());
		
		long quantityRecordsAfterCreate = financialMovementRepository.count();
		
		assertThat(quantityRecordsAfterCreate).isEqualTo(quantityRecordBeforeCreate + 1);
		assertThat(financialMovement.getValue()).isEqualTo(DEFAULT_VALUE);
		assertThat(financialMovement.getCompany()).isEqualTo(DEFAULT_COMPANY);
	}
	
	@Test
	public void findFinancialMovement() throws Exception {
		financialMovementService.save(financialMovement);
		
		restClienteMockMvc.perform(get("/api/financialMovement"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.[*].id").value(hasItem(financialMovement.getId())))
			.andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
			.andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())));
	}
	
	@Test
	public void findFinancialMovementById() throws Exception {
		financialMovement = financialMovementService.save(financialMovement);
		
		restClienteMockMvc.perform(get("/api/financialMovement/{id}", financialMovement.getId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id").value(financialMovement.getId()))
			.andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
			.andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()));
	}
	
	@Test
	public void updateFinancialMovement() throws IOException, Exception {
		financialMovement = financialMovementService.save(financialMovement);
		financialMovement.setValue(UPDATED_VALUE);
		financialMovement.setCompany(UPDATED_COMPANY);
		
		restClienteMockMvc.perform(put("/api/financialMovement")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(financialMovement)))
			.andExpect(status().isNoContent());
		
		Optional<FinancialMovementEntity> financialMovementChanged = financialMovementService.findById(financialMovement.getId());
		assertThat(financialMovementChanged.get().getValue()).isEqualTo(UPDATED_VALUE);
		assertThat(financialMovementChanged.get().getCompany()).isEqualTo(UPDATED_COMPANY);
	}
	
	@Test
	public void deleteFinancialMovement() throws Exception {
		financialMovement = financialMovementService.save(financialMovement);
		
		long quantityRecordsBeforeDelete = financialMovementRepository.count();
		
		restClienteMockMvc.perform(delete("/api/financialMovement/{id}", financialMovement.getId()))
			.andExpect(status().isNoContent());

		long quantityRecordsAfterDelete = financialMovementRepository.count();
		
		assertThat(quantityRecordsAfterDelete).isEqualTo(quantityRecordsBeforeDelete - 1);		
	}
}
