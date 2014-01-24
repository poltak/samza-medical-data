package poltak.samza.medicaldata.task;

import org.apache.samza.system.IncomingMessageEnvelope;
import org.apache.samza.system.OutgoingMessageEnvelope;
import org.apache.samza.system.SystemStream;
import org.apache.samza.task.MessageCollector;
import org.apache.samza.task.StreamTask;
import org.apache.samza.task.TaskCoordinator;

import java.util.Map;

public class MedicalDataFeedStreamTask implements StreamTask
{
  private static final SystemStream OUTPUT_STREAM = new SystemStream("kafka", "medical-test");

  /**
   * Does nothing but pass the message through to a new stream.
   * @param incomingMessageEnvelope
   * @param messageCollector
   * @param taskCoordinator
   * @throws Exception
   */
  @Override
  public void process(final IncomingMessageEnvelope incomingMessageEnvelope, final MessageCollector messageCollector,
                      final TaskCoordinator taskCoordinator) throws Exception
  {
    messageCollector.send(new OutgoingMessageEnvelope(OUTPUT_STREAM, incomingMessageEnvelope.getMessage()));
  }
}
