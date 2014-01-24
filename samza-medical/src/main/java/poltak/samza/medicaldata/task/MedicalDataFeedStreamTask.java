package poltak.samza.medicaldata.task;

import org.apache.samza.system.IncomingMessageEnvelope;
import org.apache.samza.system.OutgoingMessageEnvelope;
import org.apache.samza.system.SystemStream;
import org.apache.samza.task.MessageCollector;
import org.apache.samza.task.StreamTask;
import org.apache.samza.task.TaskCoordinator;
import poltak.samza.medicaldata.system.MedicalDataFeed;
import poltak.samza.medicaldata.system.MedicalDataFeed.MedicalDataFeedEvent;

import java.util.Map;

public class MedicalDataFeedStreamTask implements StreamTask
{
  private static final SystemStream OUTPUT_STREAM = new SystemStream("kafka", "medical-test");

  @Override
  public void process(final IncomingMessageEnvelope incomingMessageEnvelope, final MessageCollector messageCollector,
                      final TaskCoordinator taskCoordinator) throws Exception
  {
    Map<String, Object> outgoingMap =
        MedicalDataFeed.MedicalDataFeedEvent.toMap((MedicalDataFeedEvent) incomingMessageEnvelope.getMessage());

    messageCollector.send(new OutgoingMessageEnvelope(OUTPUT_STREAM, outgoingMap));
  }
}
