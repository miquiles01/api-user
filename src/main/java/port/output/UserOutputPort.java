package port.output;

import domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserOutputPort {

    User cadastrar(User user);

    List<User> buscar();

    Optional<User> buscarId(Long id);

    boolean verificarEmail(String email);

    boolean verificarCpf(String cpf);

    void deletarPorId(Long id);
}
