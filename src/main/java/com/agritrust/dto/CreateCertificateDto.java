package com.agritrust.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCertificateDto {
	
    @NotBlank
    @Size(max = 100)
    private String certificateNumber;

    @NotBlank
    @Size(max = 100)
    private String certificateType;
    // e.g. ORGANIC, FAIR_TRADE, ISO_22000

    @NotBlank
    @Size(max = 150)
    private String issuingAuthority;
    
    @PastOrPresent
    private LocalDate issuedDate;
    
    @NotNull
    @Future
    private LocalDate validUntil;

}
