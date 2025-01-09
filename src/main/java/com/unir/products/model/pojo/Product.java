package com.unir.products.model.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter //genera codigo automaticamente
@Setter //genera codigo automaticamente
@AllArgsConstructor //genera codigo automaticamente
@NoArgsConstructor //genera codigo automaticamente
@Builder
@ToString //genera codigo automaticamente


//@Getter: Genera automáticamente métodos "getter" para todos los atributos de la clase.
//@Setter: Genera automáticamente métodos "setter" para todos los atributos de la clase.
//@AllArgsConstructor: Genera un constructor que acepta un argumento para cada atributo de la clase.
//@NoArgsConstructor: Genera un constructor sin argumentos.
//@Builder: Permite la creación de objetos de esta clase utilizando el patrón de diseño "Builder", facilitando la instanciación de objetos con un código más limpio.
//@ToString: Genera un método toString() que devuelve una representación en cadena de la entidad, incluyendo los valores de los atributos.

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", unique = true)
	private String name;
	@Column(name = "country")
	private String country;
	@Column(name = "description")
	private String description;
	@Column(name = "visible")
	private Boolean visible;
}
//El POJO debe tener definidos constructores con y sin argumentos para asegurar que
//no falle en ningún caso. Esto lo conseguimos con las anotaciones de Lombok,
//NoArgsConstructor y AllArgsConstructor .