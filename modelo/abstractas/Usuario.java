package modelo.abstractas;
public abstract class Usuario {
    private String nombre;
    private String dni;
    private String email;
    private String telefono;
    public Usuario(String nombre, String dni, String email, String telefono) {
        this.nombre = nombre; this.dni = dni; this.email = email; this.telefono = telefono;
    }
    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
}
