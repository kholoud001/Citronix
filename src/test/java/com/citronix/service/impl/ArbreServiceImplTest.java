package com.citronix.service.impl;

import com.citronix.dto.ArbreDTO;
import com.citronix.mapper.ArbreMapper;
import com.citronix.model.entity.Arbre;
import com.citronix.model.entity.Champ;
import com.citronix.repository.ArbreRepository;
import com.citronix.repository.ChampRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ArbreServiceImplTest {

    @Mock
    private ArbreRepository arbreRepository;

    @Mock
    private ChampRepository champRepository;

    @Mock
    private ArbreMapper arbreMapper;

    @InjectMocks
    private ArbreServiceImpl arbreService;

    private ArbreDTO arbreDTO;
    private Arbre arbre;
    private Champ champ;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        champ = new Champ();
        champ.setId(1L);
        champ.setSuperficie(100.0);

        arbreDTO = new ArbreDTO();
        arbreDTO.setChampId(1L);
        arbreDTO.setDatePlantation(LocalDate.of(2023, 4, 15)); // Between March and May

        arbre = new Arbre();
        arbre.setId(1L);
        arbre.setChamp(champ);
        arbre.setDatePlantation(LocalDate.of(2023, 4, 15)); // Between March and May
    }


    @Test
    public void testCreateArbre_Success() {
        when(champRepository.findById(1L)).thenReturn(Optional.of(champ));
        when(arbreMapper.toEntity(arbreDTO)).thenReturn(arbre);
        when(arbreRepository.save(arbre)).thenReturn(arbre);
        when(arbreMapper.toDTO(arbre)).thenReturn(arbreDTO);

        ArbreDTO createdArbreDTO = arbreService.createArbre(1L, arbreDTO);

        assertNotNull(createdArbreDTO);
        assertEquals(1L, createdArbreDTO.getChampId());
        verify(arbreRepository, times(1)).save(arbre);
    }

    @Test
    public void testCreateArbre_ChampNotFound() {
        when(champRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            arbreService.createArbre(1L, arbreDTO);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Champ non trouvé", e.getMessage());
        }
    }



    @Test
    public void testGetArbre_Success() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));
        when(arbreMapper.toDTO(arbre)).thenReturn(arbreDTO);

        ArbreDTO retrievedArbreDTO = arbreService.getArbre(1L);

        assertNotNull(retrievedArbreDTO);
        assertEquals(1L, retrievedArbreDTO.getChampId());
        verify(arbreRepository, times(1)).findById(1L);
    }


    @Test
    public void testGetArbre_NotFound() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            arbreService.getArbre(1L);

            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Arbre non trouvé", e.getMessage());
        }
    }



    @Test
    public void testDeleteArbre_Success() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));

        arbreService.deleteArbre(1L);

        verify(arbreRepository, times(1)).delete(arbre);
    }


    @Test
    public void testDeleteArbre_NotFound() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            arbreService.deleteArbre(1L);

            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Arbre non trouvé", e.getMessage());
        }
    }


    @Test
    public void testUpdateArbre_Success() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));
        when(champRepository.findById(1L)).thenReturn(Optional.of(champ));
        when(arbreRepository.save(arbre)).thenReturn(arbre);
        when(arbreMapper.toDTO(arbre)).thenReturn(arbreDTO);

        ArbreDTO updatedArbreDTO = arbreService.updateArbre(1L, arbreDTO);

        assertNotNull(updatedArbreDTO);
        assertEquals(1L, updatedArbreDTO.getChampId());
        verify(arbreRepository, times(1)).save(arbre);
    }

    @Test
    public void testUpdateArbre_NotFound() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            arbreService.updateArbre(1L, arbreDTO);

            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Arbre not found", e.getMessage());
        }
    }



}