package server.service;

import server.model.Farmacia;
import server.model.Usuario;
import server.repository.FarmaciaRepository;
import server.repository.UsuarioRepository;

public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final FarmaciaRepository farmaciaRepository;

    public AuthService(UsuarioRepository usuarioRepository, FarmaciaRepository farmaciaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.farmaciaRepository = farmaciaRepository;
    }

    public Usuario autenticarUsuario(String login, String senha) {
        return usuarioRepository.findAll().stream()
                    .filter(u -> u.getLogin().equals(login) && u.getSenha().equals(senha))
                    .findFirst()
                    .orElse(null);
    }

    public Farmacia autenticarFarmacia(String nome, String endereco) {
        return farmaciaRepository.findAll().stream()
                    .filter(f -> f.getNome().equals(nome) && f.getEndereco().equals(endereco))
                    .findFirst()
                    .orElse(null);
    }
}
