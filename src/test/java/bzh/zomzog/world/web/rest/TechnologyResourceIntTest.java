package bzh.zomzog.world.web.rest;

import bzh.zomzog.world.Application;
import bzh.zomzog.world.domain.Technology;
import bzh.zomzog.world.repository.TechnologyRepository;
import bzh.zomzog.world.service.TechnologyService;
import bzh.zomzog.world.web.rest.dto.TechnologyDTO;
import bzh.zomzog.world.web.rest.mapper.TechnologyMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TechnologyResource REST controller.
 *
 * @see TechnologyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TechnologyResourceIntTest {

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";

    @Inject
    private TechnologyRepository technologyRepository;

    @Inject
    private TechnologyMapper technologyMapper;

    @Inject
    private TechnologyService technologyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTechnologyMockMvc;

    private Technology technology;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TechnologyResource technologyResource = new TechnologyResource();
        ReflectionTestUtils.setField(technologyResource, "technologyService", technologyService);
        ReflectionTestUtils.setField(technologyResource, "technologyMapper", technologyMapper);
        this.restTechnologyMockMvc = MockMvcBuilders.standaloneSetup(technologyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        technology = new Technology();
        technology.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTechnology() throws Exception {
        int databaseSizeBeforeCreate = technologyRepository.findAll().size();

        // Create the Technology
        TechnologyDTO technologyDTO = technologyMapper.technologyToTechnologyDTO(technology);

        restTechnologyMockMvc.perform(post("/api/technologys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technologyDTO)))
                .andExpect(status().isCreated());

        // Validate the Technology in the database
        List<Technology> technologys = technologyRepository.findAll();
        assertThat(technologys).hasSize(databaseSizeBeforeCreate + 1);
        Technology testTechnology = technologys.get(technologys.size() - 1);
        assertThat(testTechnology.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = technologyRepository.findAll().size();
        // set the field null
        technology.setName(null);

        // Create the Technology, which fails.
        TechnologyDTO technologyDTO = technologyMapper.technologyToTechnologyDTO(technology);

        restTechnologyMockMvc.perform(post("/api/technologys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technologyDTO)))
                .andExpect(status().isBadRequest());

        List<Technology> technologys = technologyRepository.findAll();
        assertThat(technologys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTechnologys() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        // Get all the technologys
        restTechnologyMockMvc.perform(get("/api/technologys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(technology.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        // Get the technology
        restTechnologyMockMvc.perform(get("/api/technologys/{id}", technology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(technology.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTechnology() throws Exception {
        // Get the technology
        restTechnologyMockMvc.perform(get("/api/technologys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

		int databaseSizeBeforeUpdate = technologyRepository.findAll().size();

        // Update the technology
        technology.setName(UPDATED_NAME);
        TechnologyDTO technologyDTO = technologyMapper.technologyToTechnologyDTO(technology);

        restTechnologyMockMvc.perform(put("/api/technologys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technologyDTO)))
                .andExpect(status().isOk());

        // Validate the Technology in the database
        List<Technology> technologys = technologyRepository.findAll();
        assertThat(technologys).hasSize(databaseSizeBeforeUpdate);
        Technology testTechnology = technologys.get(technologys.size() - 1);
        assertThat(testTechnology.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

		int databaseSizeBeforeDelete = technologyRepository.findAll().size();

        // Get the technology
        restTechnologyMockMvc.perform(delete("/api/technologys/{id}", technology.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Technology> technologys = technologyRepository.findAll();
        assertThat(technologys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
