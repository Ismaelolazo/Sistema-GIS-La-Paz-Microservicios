package Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "Usuarios")
@Schema(description = "Entidad que representa un usuario en el sistema")
public class Usuario {
	
	@Id
    @Column(name = "ID_usuario")
    @Schema(description = "Identificador único del usuario", example = "1")
    private int id;
    
	@Column(name = "Nombre")
	@Schema(description = "Nombre del usuario", example = "John Doe")
	private String name;
    
	@Column(name="Correo")
	@Schema(description = "Correo electrónico del usuario", example = "john.doe@example.com")
	private String email;
    
	@Column(name="Contra")
	@Schema(description = "Contraseña del usuario", example = "password123")
	private String password;
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Usuario(int id,String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ "]";
	}
}