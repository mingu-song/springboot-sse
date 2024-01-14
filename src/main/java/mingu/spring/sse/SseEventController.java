package mingu.spring.sse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class SseEventController {

    @GetMapping("/sse")
    public SseEmitter serverEvent() {
        SseEmitter emitter = new SseEmitter();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> executeEvent(emitter));
        return emitter;
    }

    private void executeEvent(SseEmitter emitter) {
        try {
            for (int idx = 1; idx <= 10; idx++) {
                SseEmitter.SseEventBuilder event = SseEmitter.event().data(
                        new RandomData(idx, UUID.randomUUID().toString(), LocalDateTime.now()));
                emitter.send(event);
                Thread.sleep(1000);
            }
            emitter.complete();
        } catch (IOException | InterruptedException e) {
            emitter.completeWithError(e);
        }
    }
}
