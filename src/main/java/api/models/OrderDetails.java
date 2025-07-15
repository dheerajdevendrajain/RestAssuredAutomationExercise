package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO (Plain Old Java Object) class representing the details of a single order item
 * as found within the 'data' array of the Get Orders API response.
 */
@Data // Lombok annotation for getters, setters, equals, hashCode, toString
@Builder // Lombok annotation for builder pattern
@NoArgsConstructor // Lombok annotation for no-arg constructor
@AllArgsConstructor // Lombok annotation for all-arg constructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in JSON (for serialization)
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore any JSON fields not present in this POJO during deserialization
public class OrderDetails {

    @JsonProperty("_id") // Maps JSON "_id" to Java field
    private String orderId; // Renamed for clarity

    private String orderById;
    private String orderBy;

    @JsonProperty("productOrderedId") // Maps JSON "productOrderedId" to Java field
    private String productOrderedId;

    private String productName;
    private String country;
    private String productDescription;
    private String productImage;
    private String orderDate; // Assuming it comes as a String, or use java.util.Date/LocalDate with formatters
    private String orderPrice; // Assuming it comes as a String, or use double/BigDecimal
    @JsonProperty("__v") // Maps JSON "__v" to Java field
    private int version; // Renamed for clarity
}
