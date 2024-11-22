package com.citronix.service.impl;

import com.citronix.dto.ArbreDTO;
import com.citronix.dto.VenteDTO;
import com.citronix.mapper.VenteMapper;
import com.citronix.model.entity.Recolte;
import com.citronix.model.entity.Vente;
import com.citronix.model.enums.Saison;
import com.citronix.repository.RecolteRepository;
import com.citronix.repository.VenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class VenteServiceImplTest {

    @Mock
    private VenteRepository venteRepository;

    @Mock
    private RecolteRepository recolteRepository;

    @Mock
    private VenteMapper venteMapper;

    @InjectMocks
    private VenteServiceImpl venteService;

    private VenteDTO venteDTO;
    private Vente vente;
    private Recolte recolte;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        recolte= new Recolte();
        recolte.setId(1L);
        recolte.setDateRecolte(LocalDate.of(2023, 7, 15));
        recolte.setSaison(Saison.ETE);

        vente=new Vente();
        vente.setId(1L);
        vente.setRecolte(recolte);
        vente.setClient("kholod");
        vente.setDateVente(LocalDate.of(2024, 11, 21));
        vente.setPrixUnitaire(12.5);

        venteDTO = new VenteDTO();
        venteDTO.setRecolteId(1L);
        venteDTO.setDateVente(LocalDate.of(2024, 11, 21));
        venteDTO.setRevenu(200.50);

    }


        @Test
        public void testCreateVente_Success(){
            when(recolteRepository.findById(1L)).thenReturn(Optional.of(recolte));
            when(venteMapper.toEntity(venteDTO)).thenReturn(vente);
            when(venteRepository.save(vente)).thenReturn(vente);
            when(venteMapper.toDTO(vente)).thenReturn(venteDTO);

            VenteDTO createdVenteDTO=venteService.creerVente(venteDTO);
            assertNotNull(createdVenteDTO);
            assertEquals(1L,createdVenteDTO.getRecolteId());
            verify(venteRepository,times(1)).save(vente);
    }

    @Test
    public void testCreateVente_RecolteNotFound() {

        when(recolteRepository.findById(1L)).thenReturn(Optional.empty());
        try{
            venteService.creerVente(venteDTO);
            fail("RuntimeException must be thrown");

        }catch(RuntimeException e){
            assertEquals("Récolte non trouvée", e.getMessage());
        }
    }

//    @Test
//    public void testGetVente_Success(){
//
//        when(recolteRepository.findById(1L)).thenReturn(Optional.of(recolte));
//        when(venteMapper.toDTO(vente)).thenReturn(venteDTO);
//
//         List<VenteDTO> gettedVenteDTO= venteService.obtenirVentesParRecolte(recolte.getId());
//         assertNotNull(gettedVenteDTO);
//
//    }

    @Test
    public void testGetVente_NotFound() {
        when(recolteRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            List<VenteDTO> ventes = venteService.obtenirVentesParRecolte(1L);

            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Aucune vente trouvée pour la récolte avec l'ID : 1", e.getMessage());
        }
    }

    @Test
    public void testDeleteVente_Success() {
        when(venteRepository.findById(1L)).thenReturn(Optional.of(vente));

        venteService.deleteVente(1L);

        verify(venteRepository, times(1)).delete(vente);
    }


    @Test
    public void testDeleteVente_NotFound() {
        when(venteRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            venteService.deleteVente(1L);

            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Vente non trouvée", e.getMessage());
        }
    }


    @Test
    public void testUpdateVente_Success() {
        venteDTO.setClient("Updated Client");
        venteDTO.setPrixUnitaire(100.0);

        when(venteRepository.findById(vente.getId())).thenReturn(Optional.of(vente));
        when(venteRepository.save(any(Vente.class))).thenReturn(vente);
        when(venteMapper.toDTO(any(Vente.class))).thenReturn(venteDTO);

        VenteDTO result = venteService.updateVente(vente.getId(), venteDTO);

        assertNotNull(result);
        assertEquals("Updated Client", result.getClient());
        assertEquals(100.0, result.getPrixUnitaire(), 0.01);
        assertEquals(LocalDate.of(2024, 11, 21), result.getDateVente());

        verify(venteRepository, times(1)).save(any(Vente.class));

    }

    @Test
    public void testUpdateVente_NotFound() {
        when(recolteRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            venteService.updateVente(1L, venteDTO);

            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Vente non trouvée", e.getMessage());
        }
    }



}