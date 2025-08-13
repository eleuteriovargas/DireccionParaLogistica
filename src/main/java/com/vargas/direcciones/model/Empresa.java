package com.vargas.direcciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_empresa", nullable = false)
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombreEmpresa;

    @Column(name = "nombre_contacto")
    @Size(max = 100, message = "El nombre de contacto no puede exceder los 100 caracteres")
    private String nombreContacto;

    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s./0-9]*$",
            message = "Formato de teléfono inválido")
    private String telefono;

    @Email(message = "Debe ser una dirección de correo válida")
    private String email;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    private String estado;

    @NotBlank(message = "El país es obligatorio")
    @Size(max = 100, message = "El país no puede exceder los 100 caracteres")
    private String pais;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    @Lob
    @Column(length = 1000)
    private String notas;

    public void setMapUrl(String s) {
    }

    @Transient // Indica que no se persiste en BD
    private String mapUrl;

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nombreEmpresa='" + nombreEmpresa + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}