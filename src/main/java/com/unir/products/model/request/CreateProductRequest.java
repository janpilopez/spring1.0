package com.unir.products.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//La clase CreateProductRequest está diseñada para representar el cuerpo de una petición HTTP que se envía al servidor al crear un nuevo producto. Cuando un cliente envía una solicitud (por ejemplo, un formulario en una aplicación web), el cuerpo de la solicitud puede estar en formato JSON. Spring utilizará esta clase para deserializar (convertir) el JSON en un objeto Java.
public class CreateProductRequest {
	private String name;
	private String country;
	private String description;
	private Boolean visible;
    //@JsonProperty("is_visible")
    //private Boolean visible;
}

//Esta clase servirá para trasladar a un objeto Java el contenido de la petición
//HTTP que reciba el controlador en las operaciones de creación de productos (el
//cuerpo de la petición será un JSON). Spring tratará de convertir automáticamente el
//JSON recibido en un objeto de esta clase, utilizando internamente la librería Jackson.
//Para que este proceso de deserialización sea exitoso, debemos tener en cuenta que:
//El nombre de las claves del JSON debe coincidir con el nombre de los atributos de la▸
//clase Java. Si no se pudiese conseguir eso, usaríamos la anotación @JsonProperty
//encima del atributo Java, indicando entre comillas el nombre de la clave en el JSON,
//como en el caso de country (aunque en este ejemplo no sea necesario).