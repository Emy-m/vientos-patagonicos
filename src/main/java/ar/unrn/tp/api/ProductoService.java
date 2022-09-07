package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Producto;

import java.util.List;

public interface ProductoService {
    //validar que sea una categoría existente y que codigo no se repita
    void crearProducto(String codigo, String descripcion, String marca, float precio, Long IdCategoría);

    //validar que sea un producto existente
    void modificarProducto(Long idProducto, String descripcion, String marca, float precio, Long IdCategoría, Long version);

    //Devuelve todos los productos
    List<Producto> listarProductos();
}
