package service;

import entities.UserEntity;
import org.springframework.stereotype.Service;
import repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity create(UserEntity user) {

        validateUserData(user);

        user.setDataCadastro(LocalDateTime.now());
        return repository.save(user);
    }


    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    public UserEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com ID: " + id));
    }


    public UserEntity update(Long id, UserEntity updated) {

        UserEntity existing = findById(id);

        if (updated.getEmail() != null &&
                !updated.getEmail().equals(existing.getEmail())) {

            if (emailEmUso(updated.getEmail())) {
                throw new IllegalArgumentException("Email já está cadastrado.");
            }
        }

        if (updated.getCpf() != null &&
                !updated.getCpf().equals(existing.getCpf())) {

            if (cpfEmUso(updated.getCpf())) {
                throw new IllegalArgumentException("CPF já está cadastrado.");
            }
        }

        if (updated.getNome() != null) existing.setNome(updated.getNome());
        if (updated.getEmail() != null) existing.setEmail(updated.getEmail());
        if (updated.getCpf() != null) existing.setCpf(updated.getCpf());
        if (updated.getDataNascimento() != null) existing.setDataNascimento(updated.getDataNascimento());

        return repository.save(existing);
    }


    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado para deletar.");
        }
        repository.deleteById(id);
    }


    private void validateUserData(UserEntity user) {
        if (emailEmUso(user.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso.");
        }

        if (cpfEmUso(user.getCpf())) {
            throw new IllegalArgumentException("CPF já está em uso.");
        }

        if (user.getNome() == null || user.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }
    }

    private boolean emailEmUso(String email) {
        return repository.findAll().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    private boolean cpfEmUso(String cpf) {
        return repository.findAll().stream()
                .anyMatch(u -> u.getCpf().equals(cpf));
    }

}
