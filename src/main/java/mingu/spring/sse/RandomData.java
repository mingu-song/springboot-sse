package mingu.spring.sse;

import java.time.LocalDateTime;

public record RandomData(
        int index,
        String name,
        LocalDateTime time
) {}
