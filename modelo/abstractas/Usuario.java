package modelo.abstractas;

public abstract class Usuario {
    private String nombre;
    private String dni;
    private String email;
    private String telefono;

    // Credenciales de acceso
    private String username;
    private String password;
    private String rol;        // "Administrador", "Guardia", "Residente"

    public Usuario(String nombre, String dni, String email, String telefono) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // NOTA: en un sistema real la contraseña iria cifrada (hash), no en texto plano.
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Verifica si las credenciales coinciden
    public boolean credencialesCoinciden(String user, String pass) {
        return username != null && username.equals(user) && password != null && password.equals(pass);
    }
}
