package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor; /**
 * POJO (Plain Old Java Object) class representing a single item within an order.
 * This will be nested inside the CreateOrderRequest.
 */
@Data // Lombok annotation for getters, setters, equals, hashCode, toString
@Builder // Lombok annotation for builder pattern
@NoArgsConstructor // Lombok annotation for no-arg constructor
@AllArgsConstructor // Lombok annotation for all-arg constructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in JSON
public class OrderItem {

    private String country;
    @JsonProperty("productOrderedId") // Map the JSON field "productOrderedId" to this Java field
    private String productOrderedId;

}
