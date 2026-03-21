package org.example.workforce.dto;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    @Pattern(regexp = "^$|^[+]?[0-9]{10,15}$", message = "Phone must be 10-15 digits, optionally starting with +")
    private String phone;
    private String address;
    @Size(max = 100, message = "Emergency contact name must not exceed 100 characters")
    private String emergencyContactName;
    @Pattern(regexp = "^$|^[+]?[0-9]{10,15}$", message = "Emergency phone must be 10-15 digits, optionally starting with +")
    private String emergencyContactPhone;
}
