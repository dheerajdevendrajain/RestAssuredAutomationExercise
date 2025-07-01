package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString methods
@Builder // Lombok annotation to provide a builder pattern for object creation
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all arguments
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest {

    private String userEmail;
    private String userPassword;
}
