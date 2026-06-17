package isw.backend.util;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp
) {
    public static ErrorResponse of(String message, int status) {
        return ErrorResponse.builder()
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
