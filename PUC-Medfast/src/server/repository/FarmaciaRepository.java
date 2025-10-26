package server.repository;

import server.model.Farmacia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FarmaciaRepository {

    private List<Farmacia> farmaciaRepository;

    public FarmaciaRepository() {
        farmaciaRepository = new ArrayList<>();
    }

    public List<Farmacia> findAll() {
        return farmaciaRepository;
    }

    public Optional<Farmacia> findByName(String name) {
        return farmaciaRepository.stream()
                .filter(f -> f.getNome().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<Farmacia> findByAddress(String address) {
        return farmaciaRepository.stream()
                .filter(f -> f.getEndereco().equalsIgnoreCase(address))
                .findFirst();
    }

    public void save(Farmacia farmacia) {
        farmaciaRepository.add(farmacia);
    }

    public void delete(Farmacia farmacia) {
        farmaciaRepository.remove(farmacia);
    }
}
