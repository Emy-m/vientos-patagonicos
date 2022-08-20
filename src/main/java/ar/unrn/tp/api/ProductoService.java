package ar.unrn.tp.api;

import java.util.List;

interface ProductoService {
    //validar que sea una categoría existente y que codigo no se repita
    void crearProducto(String codigo, String descripcion, String marca, float precio, Long IdCategoría);

    //validar que sea un producto existente
    void modificarProducto(Long idProducto, String descripcion, String marca, float precio, Long IdCategoría);

    //Devuelve todos los productos
    List listarProductos();
}