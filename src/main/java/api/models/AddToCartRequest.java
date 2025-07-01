package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString methods
@Builder // Lombok annotation to provide a builder pattern for object creation
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all arguments
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddToCartRequest {
    @JsonProperty("_id") // Map the JSON field "_id" to this Java field
    private String _id;// Assuming Product is a class that contains product details
    private Product product;

}
