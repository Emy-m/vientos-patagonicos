package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Categoria;
import java.util.List;

public interface CategoriaService {
    // validar que el nombre no se repita
    void crearCategoria(String nombre);

    List<Categoria> categorias();

    // validar que sea un categoria existente
    void modificarCategoria(Long idCategoria, String nombre);
}
