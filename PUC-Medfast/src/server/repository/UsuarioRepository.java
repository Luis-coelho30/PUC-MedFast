package server.repository;

import server.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository {

    private List<Usuario> usuarioRepository;

    public UsuarioRepository() {
        usuarioRepository = new ArrayList<>();
    }

    public List<Usuario> findAll() {
        return usuarioRepository;
    }

    public Optional<Usuario> findByMail(String mail) {
        return usuarioRepository.stream()
                    .filter(u -> u.getLogin().equalsIgnoreCase(mail))
                    .findFirst();
    }

    public void save(Usuario user) {
        usuarioRepository.add(user);
    }

    public void delete(Usuario user) {
        usuarioRepository.remove(user);
    }
}
