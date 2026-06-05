package cl.usm.gestionPeliculasMemoria.services;

import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import cl.usm.gestionPeliculasMemoria.repositories.PeliculasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeliculasServiceImplTest {

    @Mock
    private PeliculasRepository peliculasRepository;

    @InjectMocks
    private PeliculasServiceImpl peliculasService;

    @Test
    void debeCrearPelicula() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");

        when(peliculasRepository.insert(any(Pelicula.class)))
                .thenReturn(pelicula);

        Pelicula resultado = peliculasService.createPelicula(pelicula);

        assertNotNull(resultado);
        assertEquals("P1", resultado.getId());
        assertNotNull(resultado.getTokenDescarga());

        verify(peliculasRepository).insert(any(Pelicula.class));
    }

    @Test
    void debeRetornarNullCuandoFallaInsert() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");

        when(peliculasRepository.insert(any(Pelicula.class)))
                .thenThrow(new RuntimeException());

        Pelicula resultado = peliculasService.createPelicula(pelicula);

        assertNull(resultado);

        verify(peliculasRepository).insert(any(Pelicula.class));
    }

    @Test
    void debeRetornarTodasLasPeliculas() {

        Pelicula p1 = new Pelicula();
        p1.setId("P1");

        Pelicula p2 = new Pelicula();
        p2.setId("P2");

        when(peliculasRepository.findAll())
                .thenReturn(Arrays.asList(p1, p2));

        List<Pelicula> resultado = peliculasService.getAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("P1", resultado.get(0).getId());
        assertEquals("P2", resultado.get(1).getId());

        verify(peliculasRepository).findAll();
    }

    @Test
    void debeRetornarListaVacia() {

        when(peliculasRepository.findAll())
                .thenReturn(List.of());

        List<Pelicula> resultado = peliculasService.getAll();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(peliculasRepository).findAll();
    }

    @Test
    void debeBuscarPorId() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");

        when(peliculasRepository.findById("P1"))
                .thenReturn(pelicula);

        Pelicula resultado = peliculasService.findById("P1");

        assertNotNull(resultado);
        assertEquals("P1", resultado.getId());

        verify(peliculasRepository).findById("P1");
    }

    @Test
    void debeRetornarNullCuandoNoExiste() {

        when(peliculasRepository.findById("XXX"))
                .thenReturn(null);

        Pelicula resultado = peliculasService.findById("XXX");

        assertNull(resultado);

        verify(peliculasRepository).findById("XXX");
    }

    @Test
    void debeFiltrarPorTitulo() {

        Pelicula p1 = new Pelicula();
        p1.setId("P1");
        p1.setTitulo("Matrix");

        Pelicula p2 = new Pelicula();
        p2.setId("P2");
        p2.setTitulo("Titanic");

        when(peliculasRepository.findAll())
                .thenReturn(Arrays.asList(p1, p2));

        List<Pelicula> resultado = peliculasService.filter("matrix");

        assertEquals(1, resultado.size());
        assertEquals("Matrix", resultado.get(0).getTitulo());

        verify(peliculasRepository).findAll();
    }

    @Test
    void debeFiltrarPorId() {

        Pelicula p1 = new Pelicula();
        p1.setId("ABC123");
        p1.setTitulo("Matrix");

        when(peliculasRepository.findAll())
                .thenReturn(List.of(p1));

        List<Pelicula> resultado = peliculasService.filter("abc");

        assertEquals(1, resultado.size());
        assertEquals("ABC123", resultado.get(0).getId());

        verify(peliculasRepository).findAll();
    }

    @Test
    void debeRetornarListaVaciaCuandoNoHayCoincidencias() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");
        pelicula.setTitulo("Matrix");

        when(peliculasRepository.findAll())
                .thenReturn(List.of(pelicula));

        List<Pelicula> resultado = peliculasService.filter("Titanic");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(peliculasRepository).findAll();
    }
}