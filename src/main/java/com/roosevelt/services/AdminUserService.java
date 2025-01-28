package com.roosevelt.services;

import com.roosevelt.dto.admin.GradoSeccionDTO;
import com.roosevelt.dto.admin.UserDTO;
import com.roosevelt.entities.GradoSeccion;
import com.roosevelt.entities.User;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class AdminUserService {

    // -----------------------
    // LISTAR USUARIOS
    // -----------------------
    public List<User> listAllUsers() {
        return User.listAll(Sort.by("id"));
    }

    public List<User> listAdmins() {
        return User.list("rol", "ADMIN");
    }

    public List<User> listProfesores() {
        return User.list("rol", "PROFESOR");
    }

    public List<User> listEstudiantes() {
        return User.list("rol", "ESTUDIANTE");
    }

    // -----------------------
    // CREAR USUARIO
    // -----------------------
    @Transactional
    public User createUser(UserDTO userDto) {
        // Verificar email duplicado
        long count = User.count("email", userDto.email);
        if (count > 0) {
            throw new WebApplicationException("Ya existe un usuario con el email: " + userDto.email,
                    Response.Status.CONFLICT);
        }

        User user = new User();
        user.nombre = userDto.nombre;
        user.email = userDto.email;
        user.password = userDto.password; // en producción => encriptar
        user.rol = userDto.rol;

        // Manejo de gradoSeccion si es ESTUDIANTE
        if ("ESTUDIANTE".equalsIgnoreCase(user.rol)) {
            GradoSeccion gs = handleGradoSeccion(userDto.gradoSeccion);
            user.gradoSeccion = gs;
        }

        user.persist();
        return user;
    }

    // -----------------------
    // ACTUALIZAR USUARIO
    // -----------------------
    @Transactional
    public User updateUser(Long id, UserDTO userDto) {
        User user = User.findById(id);
        if (user == null) {
            throw new WebApplicationException("Usuario no encontrado con id: " + id,
                    Response.Status.NOT_FOUND);
        }

        // Verificar si se cambió el email
        if (!user.email.equals(userDto.email)) {
            long countEmail = User.count("email", userDto.email);
            if (countEmail > 0) {
                throw new WebApplicationException("Ya existe un usuario con el email: " + userDto.email,
                        Response.Status.CONFLICT);
            }
            user.email = userDto.email;
        }

        user.nombre = userDto.nombre;
        user.password = userDto.password;
        user.rol = userDto.rol;

        // Si es ESTUDIANTE => asignar o crear gradoSeccion
        if ("ESTUDIANTE".equalsIgnoreCase(user.rol)) {
            GradoSeccion gs = handleGradoSeccion(userDto.gradoSeccion);
            user.gradoSeccion = gs;
        } else {
            // Si dejó de ser estudiante
            user.gradoSeccion = null;
        }

        user.persist();
        return user;
    }

    // -----------------------
    // ELIMINAR USUARIO
    // -----------------------
    @Transactional
    public void deleteUser(Long id) {
        User user = User.findById(id);
        if (user == null) {
            throw new WebApplicationException("Usuario no encontrado con id: " + id,
                    Response.Status.NOT_FOUND);
        }
        user.delete();
    }

    // -----------------------
    // METODO AUXILIAR
    // -----------------------
    private GradoSeccion handleGradoSeccion(GradoSeccionDTO gsDto) {
        if (gsDto == null) {
            throw new WebApplicationException("Se requiere gradoSeccion para un usuario estudiante",
                    Response.Status.BAD_REQUEST);
        }

        if (gsDto.id != null) {
            GradoSeccion existing = GradoSeccion.findById(gsDto.id);
            if (existing == null) {
                throw new WebApplicationException("No existe el GradoSeccion con id: " + gsDto.id,
                        Response.Status.NOT_FOUND);
            }
            return existing;
        } else {
            // Crear uno nuevo
            GradoSeccion newGs = new GradoSeccion();
            newGs.grado = gsDto.grado;
            newGs.seccion = gsDto.seccion;
            newGs.persist();
            return newGs;
        }
    }
}
