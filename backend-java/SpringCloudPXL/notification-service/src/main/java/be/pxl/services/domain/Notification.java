package be.pxl.services.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private String sender;
    private String message;

}
