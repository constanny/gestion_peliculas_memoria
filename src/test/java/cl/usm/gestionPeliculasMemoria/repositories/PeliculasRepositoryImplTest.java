package cl.usm.gestionPeliculasMemoria.repositories;

import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeliculasRepositoryImplTest {

    private PeliculasRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new PeliculasRepositoryImpl();
    }

    @Test
    void debeInsertarPelicula() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");
        pelicula.setTitulo("Matrix");

        Pelicula resultado = repository.insert(pelicula);

        assertNotNull(resultado);
        assertEquals("P1", resultado.getId());
        assertEquals("Matrix", resultado.getTitulo());
    }

    @Test
    void noDebePermitirIdNulo() {

        Pelicula pelicula = new Pelicula();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> repository.insert(pelicula)
        );

        assertEquals("El ID de la pelicula no puede ser nulo", exception.getMessage());
    }

    @Test
    void noDebePermitirDuplicados() {

        Pelicula p1 = new Pelicula();
        p1.setId("P1");

        Pelicula p2 = new Pelicula();
        p2.setId("P1");

        repository.insert(p1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> repository.insert(p2)
        );

        assertEquals("La pelicula con ID P1 ya existe", exception.getMessage());
    }

    @Test
    void noDebePermitirDuplicadosIgnorandoMayusculas() {

        Pelicula p1 = new Pelicula();
        p1.setId("P1");

        Pelicula p2 = new Pelicula();
        p2.setId("p1");

        repository.insert(p1);

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.insert(p2)
        );
    }

    @Test
    void debePermitirInsertarPeliculasDistintas() {

        Pelicula p1 = new Pelicula();
        p1.setId("P1");

        Pelicula p2 = new Pelicula();
        p2.setId("P2");

        repository.insert(p1);
        repository.insert(p2);

        List<Pelicula> resultado = repository.findAll();

        assertEquals(2, resultado.size());
        assertEquals("P1", resultado.get(0).getId());
        assertEquals("P2", resultado.get(1).getId());
    }

    @Test
    void debeRetornarTodasLasPeliculas() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");

        repository.insert(pelicula);

        List<Pelicula> resultado = repository.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("P1", resultado.get(0).getId());
    }

    @Test
    void findAllDebeRetornarUnaCopia() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");

        repository.insert(pelicula);

        List<Pelicula> peliculas = repository.findAll();
        peliculas.clear();

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void debeBuscarPorId() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");

        repository.insert(pelicula);

        Pelicula resultado = repository.findById("P1");

        assertNotNull(resultado);
        assertEquals("P1", resultado.getId());
    }

    @Test
    void debeBuscarPorIdIgnorandoMayusculas() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("P1");

        repository.insert(pelicula);

        Pelicula resultado = repository.findById("p1");

        assertNotNull(resultado);
        assertEquals("P1", resultado.getId());
    }

    @Test
    void debeRetornarNullSiNoExiste() {

        assertNull(repository.findById("XXX"));
    }

    @Test
    void debeRetornarNullSiIdEsNulo() {

        assertNull(repository.findById(null));
    }
}