package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Lombok annotation for getters, setters, equals, hashCode, toString
@Builder // Lombok annotation for builder pattern
@NoArgsConstructor // Lombok annotation for no-arg constructor
@AllArgsConstructor // Lombok annotation for all-arg constructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in JSON
public class CreateOrderRequest {
    private List<OrderItem> orders;
}
