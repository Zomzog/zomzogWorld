package bzh.zomzog.world.web.rest;

import bzh.zomzog.world.Application;
import bzh.zomzog.world.domain.Society;
import bzh.zomzog.world.repository.SocietyRepository;
import bzh.zomzog.world.service.SocietyService;
import bzh.zomzog.world.web.rest.dto.SocietyDTO;
import bzh.zomzog.world.web.rest.mapper.SocietyMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SocietyResource REST controller.
 *
 * @see SocietyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SocietyResourceIntTest {

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";

    private static final LocalDate DEFAULT_ENTER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENTER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_QUIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUIT = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private SocietyRepository societyRepository;

    @Inject
    private SocietyMapper societyMapper;

    @Inject
    private SocietyService societyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSocietyMockMvc;

    private Society society;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SocietyResource societyResource = new SocietyResource();
        ReflectionTestUtils.setField(societyResource, "societyService", societyService);
        ReflectionTestUtils.setField(societyResource, "societyMapper", societyMapper);
        this.restSocietyMockMvc = MockMvcBuilders.standaloneSetup(societyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        society = new Society();
        society.setName(DEFAULT_NAME);
        society.setEnter(DEFAULT_ENTER);
        society.setQuit(DEFAULT_QUIT);
    }

    @Test
    @Transactional
    public void createSociety() throws Exception {
        int databaseSizeBeforeCreate = societyRepository.findAll().size();

        // Create the Society
        SocietyDTO societyDTO = societyMapper.societyToSocietyDTO(society);

        restSocietyMockMvc.perform(post("/api/societys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(societyDTO)))
                .andExpect(status().isCreated());

        // Validate the Society in the database
        List<Society> societys = societyRepository.findAll();
        assertThat(societys).hasSize(databaseSizeBeforeCreate + 1);
        Society testSociety = societys.get(societys.size() - 1);
        assertThat(testSociety.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSociety.getEnter()).isEqualTo(DEFAULT_ENTER);
        assertThat(testSociety.getQuit()).isEqualTo(DEFAULT_QUIT);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = societyRepository.findAll().size();
        // set the field null
        society.setName(null);

        // Create the Society, which fails.
        SocietyDTO societyDTO = societyMapper.societyToSocietyDTO(society);

        restSocietyMockMvc.perform(post("/api/societys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(societyDTO)))
                .andExpect(status().isBadRequest());

        List<Society> societys = societyRepository.findAll();
        assertThat(societys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnterIsRequired() throws Exception {
        int databaseSizeBeforeTest = societyRepository.findAll().size();
        // set the field null
        society.setEnter(null);

        // Create the Society, which fails.
        SocietyDTO societyDTO = societyMapper.societyToSocietyDTO(society);

        restSocietyMockMvc.perform(post("/api/societys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(societyDTO)))
                .andExpect(status().isBadRequest());

        List<Society> societys = societyRepository.findAll();
        assertThat(societys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSocietys() throws Exception {
        // Initialize the database
        societyRepository.saveAndFlush(society);

        // Get all the societys
        restSocietyMockMvc.perform(get("/api/societys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(society.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].enter").value(hasItem(DEFAULT_ENTER.toString())))
                .andExpect(jsonPath("$.[*].quit").value(hasItem(DEFAULT_QUIT.toString())));
    }

    @Test
    @Transactional
    public void getSociety() throws Exception {
        // Initialize the database
        societyRepository.saveAndFlush(society);

        // Get the society
        restSocietyMockMvc.perform(get("/api/societys/{id}", society.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(society.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.enter").value(DEFAULT_ENTER.toString()))
            .andExpect(jsonPath("$.quit").value(DEFAULT_QUIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSociety() throws Exception {
        // Get the society
        restSocietyMockMvc.perform(get("/api/societys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSociety() throws Exception {
        // Initialize the database
        societyRepository.saveAndFlush(society);

		int databaseSizeBeforeUpdate = societyRepository.findAll().size();

        // Update the society
        society.setName(UPDATED_NAME);
        society.setEnter(UPDATED_ENTER);
        society.setQuit(UPDATED_QUIT);
        SocietyDTO societyDTO = societyMapper.societyToSocietyDTO(society);

        restSocietyMockMvc.perform(put("/api/societys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(societyDTO)))
                .andExpect(status().isOk());

        // Validate the Society in the database
        List<Society> societys = societyRepository.findAll();
        assertThat(societys).hasSize(databaseSizeBeforeUpdate);
        Society testSociety = societys.get(societys.size() - 1);
        assertThat(testSociety.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSociety.getEnter()).isEqualTo(UPDATED_ENTER);
        assertThat(testSociety.getQuit()).isEqualTo(UPDATED_QUIT);
    }

    @Test
    @Transactional
    public void deleteSociety() throws Exception {
        // Initialize the database
        societyRepository.saveAndFlush(society);

		int databaseSizeBeforeDelete = societyRepository.findAll().size();

        // Get the society
        restSocietyMockMvc.perform(delete("/api/societys/{id}", society.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Society> societys = societyRepository.findAll();
        assertThat(societys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
