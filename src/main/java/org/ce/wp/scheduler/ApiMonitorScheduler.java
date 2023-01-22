package org.ce.wp.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ce.wp.entity.Terminal;
import org.ce.wp.entity.Url;
import org.ce.wp.repository.TerminalRepository;
import org.ce.wp.repository.UrlRepository;
import org.ce.wp.service.RestClientService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Parham Ahmadi
 * @since 23.01.23
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "alert.engine.scheduler.enable", havingValue = "true")
public class ApiMonitorScheduler {
    private final TerminalRepository terminalRepository;
    private final RestClientService restClientService;
    private final UrlRepository urlRepository;

    @Scheduled(fixedDelayString = "${alert.engine.scheduler.delay.ms}")
    public void scheduler() {
        List<Url> urls = urlRepository.findAll();
        long startTime = System.currentTimeMillis();
        log.info("Scheduler will start for monitoring {} apis", urls.size());
        urls.forEach(url -> {
            Terminal terminal = new Terminal();
            terminal.setUrl(url);
            try {
                int statusCode = restClientService.callApi(url.getUri());
                terminal.setStatusCode(statusCode);
                if (statusCode % 100 != 2) {
                    terminal.setFailureReason("Api is unavailable");
                }
            } catch (Exception e) {
                log.warn("Error occurred while calling api: {}", url.getUri(), e);
                terminal.setFailureReason(e.getMessage());
            } finally {
                terminalRepository.save(terminal);
            }
        });
        log.info("Scheduler ended at duration {}", System.currentTimeMillis() - startTime);
    }
}
