package org.email.notification.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class EmailRequest {
    private String to;
    private String subject;
    private String body;
    private List<String> cc;
}
