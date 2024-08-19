package io.ylab.storage.in.memory.dto;

import io.ylab.storage.in.memory.data.Identifiable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto implements Identifiable<Long> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
}
