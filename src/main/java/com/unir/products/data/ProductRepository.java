package com.unir.products.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;//Importa la interfaz JpaRepository, que proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar y Borrar) sobre la entidad Product.
//Spring registrará como beans todas aquellas clases que extiendan de esta interfaz de forma automática

import com.unir.products.model.pojo.Product;//Importa la clase Product, que es la entidad que se manejará


//en spring data, solo seremos responsables de declarar las interfaces para acceder a los datos, pero no su implementación

//La interfaz ProductRepository permite realizar operaciones de acceso a datos sobre la entidad Product sin necesidad de implementar la lógica de acceso a datos manualmente.
//Los "beans" son objetos gestionados por Spring, que facilita la creación y gestión de instancias de clases a través de la inyección de dependencias.

//La interfaz ProductRepository extiende JpaRepository<Product, Long>. Esto significa que hereda todos los métodos proporcionados por JpaRepository, como findById, findAll, save, delete, etc.
public interface ProductRepository extends JpaRepository<Product, Long> {
	//El tipo Product es la entidad que se gestionará, y Long es el tipo de la clave primaria.
	List<Product> findByName(String name);
	//El método List<Product> findByName(String name); se declara para buscar productos por su nombre. Spring Data JPA generará automáticamente la implementación de este método basándose en su nombre.
}

//Todo el código necesario para conectar con la base de datos, componer la consulta
//SQL y procesar el resultado es gestionado por Spring Data.