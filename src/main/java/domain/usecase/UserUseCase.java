
package domain.usecase;

import domain.model.User;
import port.input.UserInputPort;
import port.output.UserOutputPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class UserUseCase implements UserInputPort {

    private final UserOutputPort outputPort;

    public UserUseCase(UserOutputPort outputPort) {
        this.outputPort = outputPort;
    }

    @Override
    public User cadastrar(User user) {

        validateUserData(user);
        user.setDataCadastro(LocalDateTime.now());

        return outputPort.cadastrar(user);
    }

    @Override
    public List<User> buscar() {
        return outputPort.buscar();
    }

    @Override
    public User buscarId(Long id) {

        return outputPort.buscarId(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Usuário não encontrado com ID: " + id
                        )
                );
    }

    @Override
    public User editar(Long id, User edicao) {

        User existing = buscarId(id);

        if (edicao.getEmail() != null &&
                !edicao.getEmail().equalsIgnoreCase(existing.getEmail())) {

            if (outputPort.verificarEmail(edicao.getEmail())) {
                throw new IllegalArgumentException(
                        "Email já está cadastrado."
                );
            }
        }

        if (edicao.getCpf() != null &&
                !edicao.getCpf().equals(existing.getCpf())) {

            if (outputPort.verificarCpf(edicao.getCpf())) {
                throw new IllegalArgumentException(
                        "CPF já está cadastrado."
                );
            }
        }

        if (edicao.getNome() != null) {
            existing.setNome(edicao.getNome());
        }

        if (edicao.getEmail() != null) {
            existing.setEmail(edicao.getEmail());
        }

        if (edicao.getCpf() != null) {
            existing.setCpf(edicao.getCpf());
        }

        if (edicao.getDataNascimento() != null) {
            existing.setDataNascimento(edicao.getDataNascimento());
        }

        return outputPort.cadastrar(existing);
    }

    @Override
    public void deletarPorId(Long id) {

        buscarId(id);
        outputPort.deletarPorId(id);
    }

    private void validateUserData(User user) {

        if (user.getNome() == null || user.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "Nome não pode ser vazio."
            );
        }

        if (user.getEmail() == null ||
                !user.getEmail().contains("@")) {
            throw new IllegalArgumentException(
                    "Email inválido."
            );
        }

        if (outputPort.verificarEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                    "Email já está em uso."
            );
        }

        if (user.getCpf() != null &&
                outputPort.verificarCpf(user.getCpf())) {
            throw new IllegalArgumentException(
                    "CPF já está em uso."
            );
        }
    }
}
