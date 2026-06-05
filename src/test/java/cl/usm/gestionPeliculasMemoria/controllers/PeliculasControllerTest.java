package cl.usm.gestionPeliculasMemoria.controllers;

import cl.usm.gestionPeliculasMemoria.entities.Comentario;
import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import cl.usm.gestionPeliculasMemoria.services.PeliculasService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeliculasControllerTest {

    @Mock
    private PeliculasService peliculasService;

    @InjectMocks
    private PeliculasController peliculasController;

    @Test
    void debeRetornarTodasLasPeliculas() {

        Pelicula p1 = new Pelicula();
        p1.setId("P1");

        when(peliculasService.getAll())
                .thenReturn(List.of(p1));

        ResponseEntity<List<Pelicula>> response =
                peliculasController.getAll(null);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(peliculasService).getAll();
    }

    @Test
    void debeFiltrarPeliculas() {

        Pelicula p1 = new Pelicula();
        p1.setId("P1");

        when(peliculasService.filter("matrix"))
                .thenReturn(List.of(p1));

        ResponseEntity<List<Pelicula>> response =
                peliculasController.getAll("matrix");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(peliculasService).filter("matrix");
    }

    @Test
    void debeRetornar500CuandoGetAllFalla() {

        when(peliculasService.getAll())
                .thenThrow(new RuntimeException());

        ResponseEntity<List<Pelicula>> response =
                peliculasController.getAll(null);

        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void debeCrearPelicula() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");

        when(peliculasService.createPelicula(any(Pelicula.class)))
                .thenReturn(pelicula);

        ResponseEntity<?> response =
                peliculasController.createPelicula(pelicula);

        assertEquals(200, response.getStatusCode().value());

        Pelicula body = (Pelicula) response.getBody();

        assertNotNull(body);
        assertEquals("P1", body.getId());

        verify(peliculasService).createPelicula(any(Pelicula.class));
    }

    @Test
    void debeRetornarErrorCuandoNoSePuedeCrear() {

        Pelicula pelicula = new Pelicula();

        when(peliculasService.createPelicula(any(Pelicula.class)))
                .thenReturn(null);

        ResponseEntity<?> response =
                peliculasController.createPelicula(pelicula);

        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void debeBuscarPeliculaPorId() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");

        when(peliculasService.findById("P1"))
                .thenReturn(pelicula);

        ResponseEntity<Pelicula> response =
                peliculasController.findById("P1");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("P1", response.getBody().getId());

        verify(peliculasService).findById("P1");
    }

    @Test
    void debeRetornar404CuandoNoExiste() {

        when(peliculasService.findById("XXX"))
                .thenReturn(null);

        ResponseEntity<Pelicula> response =
                peliculasController.findById("XXX");

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void debeRetornar500CuandoFindByIdFalla() {

        when(peliculasService.findById("P1"))
                .thenThrow(new RuntimeException());

        ResponseEntity<Pelicula> response =
                peliculasController.findById("P1");

        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void debeRetornarComentarios() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");
        pelicula.setComentarios(new Comentario[]{});

        when(peliculasService.findById("P1"))
                .thenReturn(pelicula);

        ResponseEntity<?> response =
                peliculasController.getComentarios("P1");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        verify(peliculasService).findById("P1");
    }

    @Test
    void debeRetornar404EnComentariosCuandoNoExiste() {

        when(peliculasService.findById("XXX"))
                .thenReturn(null);

        ResponseEntity<?> response =
                peliculasController.getComentarios("XXX");

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void debeRetornar500CuandoComentariosFalla() {

        when(peliculasService.findById("P1"))
                .thenThrow(new RuntimeException());

        ResponseEntity<?> response =
                peliculasController.getComentarios("P1");

        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void debeRetornarTodasLasPeliculasCuandoQueryEsVacia() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");

        when(peliculasService.getAll())
                .thenReturn(List.of(pelicula));

        ResponseEntity<List<Pelicula>> response =
                peliculasController.getAll("");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(peliculasService).getAll();
        verify(peliculasService, never()).filter(anyString());
    }
}