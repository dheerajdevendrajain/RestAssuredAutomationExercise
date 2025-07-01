package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO (Plain Old Java Object) class representing a product item.
 * This will be nested within the AddToCartRequest payload.
 */
@Data // Lombok annotation for getters, setters, equals, hashCode, toString
@Builder // Lombok annotation for builder pattern
@NoArgsConstructor // Lombok annotation for no-arg constructor
@AllArgsConstructor // Lombok annotation for all-arg constructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in JSON
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore any JSON fields not present in this POJO
public class Product {

    @JsonProperty("_id") // Map the JSON field "_id" to this Java field
    private String _id; // Renamed to avoid confusion with Java's default 'id'

    private String productName;
    private String productCategory;
    private String productSubCategory;
    private int productPrice;
    private String productDescription;
    private String productImage;
    private String productRating;
    private String productTotalOrders;
    private boolean productStatus;
    private String productAddedBy;
    @JsonProperty("__v") // Map the JSON field "__v" to this Java field
    private int __v; // Using 'version' as a more descriptive name for '__v'
    private String productFor;
}
