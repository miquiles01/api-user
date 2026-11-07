
package adapter.input.mapper;

import adapter.input.dto.UserRequestDTO;
import adapter.input.dto.UserResponseDTO;
import domain.model.User;

public class UserMapper {

    public static User toDomain(UserRequestDTO dto) {
        return new User(
                null,
                dto.getNome(),
                dto.getEmail(),
                dto.getCpf(),
                dto.getDataNascimento(),
                null
        );
    }

    public static UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getCpf(),
                user.getDataNascimento(),
                user.getDataCadastro()
        );
    }
}
