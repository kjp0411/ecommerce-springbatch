package fastcampus.ecommerce.batch.service.monitoring;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchJobExecutionListener implements JobExecutionListener {

  private final CustomPrometheusPushGatewayManager manager;

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("listener: beforeJob");
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    log.info("listener: afterJob {}", jobExecution.getExecutionContext());

    manager.pushMetrics(Map.of("job name", jobExecution.getJobInstance().getJobName()));
  }
}
