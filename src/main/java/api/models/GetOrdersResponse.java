package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * POJO (Plain Old Java Object) class representing the full response structure
 * for a "Get Orders" API call.
 * It contains a list of OrderDetails objects, a count, and a message.
 */
@Data // Lombok annotation for getters, setters, equals, hashCode, toString
@Builder // Lombok annotation for builder pattern
@NoArgsConstructor // Lombok annotation for no-arg constructor
@AllArgsConstructor // Lombok annotation for all-arg constructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in JSON (for serialization)
public class GetOrdersResponse {

    // This field will hold the list of OrderDetails objects
    private List<OrderDetails> data;

    private int count;
    private String message;

    // You can add other top-level fields if your API response includes them.
}
