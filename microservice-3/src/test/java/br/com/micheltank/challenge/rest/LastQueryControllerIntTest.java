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
import br.com.micheltank.challenge.entities.LastQueryEntity;
import br.com.micheltank.challenge.repository.LastQueryRepository;
import br.com.micheltank.challenge.rest.LastQueryController;
import br.com.micheltank.challenge.service.LastQueryService;
import br.com.senior.treinamento.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChallengeApplication.class})
public class LastQueryControllerIntTest {

	private static final String DEFAULT_CPF = "99999999999";
	private static final String UPDATED_CPF = "55555555555";
	
	private static final LocalDateTime DEFAULT_DATE = LocalDateTime.now();
	private static final LocalDateTime UPDATED_DATE = LocalDateTime.of(2019, 1, 1, 1, 1);
	
	@Autowired
	private LastQueryRepository lastQueryRepository;
	
	@Autowired
	private LastQueryService lastQueryService;
	
	@Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
	
	private MockMvc restClienteMockMvc;
	
	private LastQueryEntity lastQuery;
	
	@Before
	public void setup() {
		final LastQueryController lastQueryController = new LastQueryController(lastQueryService);
		this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(lastQueryController)
				.setMessageConverters(jacksonMessageConverter).build();
	}
	
	private static LastQueryEntity createEntity() {
		LastQueryEntity lastQuery = new LastQueryEntity();
		lastQuery.setCpf(DEFAULT_CPF);
		lastQuery.setDate(DEFAULT_DATE);
		return lastQuery;
	}
	
	@Before
	public void initTest() {
		lastQuery = createEntity();
	}
	
	@Test
	public void createLastQuery() throws Exception {
		long quantityRecordBeforeCreate = lastQueryRepository.count();
		
		restClienteMockMvc.perform(post("/api/lastQuery")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(lastQuery)))
			.andExpect(status().isCreated());
		
		long quantityRecordsAfterCreate = lastQueryRepository.count();
		
		assertThat(quantityRecordsAfterCreate).isEqualTo(quantityRecordBeforeCreate + 1);
		assertThat(lastQuery.getCpf()).isEqualTo(DEFAULT_CPF);
		assertThat(lastQuery.getDate()).isEqualTo(DEFAULT_DATE);
	}
	
	@Test
	public void findLastQuery() throws Exception {
		lastQueryService.save(lastQuery);
		
		restClienteMockMvc.perform(get("/api/lastQuery"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.[*].id").value(hasItem(lastQuery.getId())))
			.andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
			.andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
	}
	
	@Test
	public void findLastQueryById() throws Exception {
		lastQuery = lastQueryService.save(lastQuery);
		
		restClienteMockMvc.perform(get("/api/lastQuery/{id}", lastQuery.getId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id").value(lastQuery.getId()))
			.andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()))
			.andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
	}
	
	@Test
	public void updateLastQuery() throws IOException, Exception {
		lastQuery = lastQueryService.save(lastQuery);
		lastQuery.setCpf(UPDATED_CPF);
		lastQuery.setDate(UPDATED_DATE);
		
		restClienteMockMvc.perform(put("/api/lastQuery")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(lastQuery)))
			.andExpect(status().isNoContent());
		
		Optional<LastQueryEntity> lastQueryChanged = lastQueryService.findById(lastQuery.getId());
		assertThat(lastQueryChanged.get().getCpf()).isEqualTo(UPDATED_CPF);
		assertThat(lastQueryChanged.get().getDate()).isEqualTo(UPDATED_DATE);
	}
	
	@Test
	public void deleteLastQuery() throws Exception {
		lastQuery = lastQueryService.save(lastQuery);
		
		long quantityRecordsBeforeDelete = lastQueryRepository.count();
		
		restClienteMockMvc.perform(delete("/api/lastQuery/{id}", lastQuery.getId()))
			.andExpect(status().isNoContent());

		long quantityRecordsAfterDelete = lastQueryRepository.count();
		
		assertThat(quantityRecordsAfterDelete).isEqualTo(quantityRecordsBeforeDelete - 1);		
	}
}
