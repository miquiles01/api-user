package port.input;

import domain.model.User;

import java.util.List;

public interface UserInputPort {

    User cadastrar(User user);

    List<User> buscar();

    User buscarId(Long id);

    User editar(Long id, User user);

    void deletarPorId(Long id);
}
