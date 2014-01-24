package poltak.samza.medicaldata.task;

import org.apache.samza.system.IncomingMessageEnvelope;
import org.apache.samza.system.OutgoingMessageEnvelope;
import org.apache.samza.system.SystemStream;
import org.apache.samza.task.MessageCollector;
import org.apache.samza.task.StreamTask;
import org.apache.samza.task.TaskCoordinator;
import poltak.samza.medicaldata.util.MedicalData;

public class MedicalDataValidatorStreamTask implements StreamTask
{
  private static final SystemStream OUTPUT_STREAM = new SystemStream("kafka", "validation-result");

  @Override
  public void process(final IncomingMessageEnvelope incomingMessageEnvelope, final MessageCollector messageCollector,
                      final TaskCoordinator taskCoordinator) throws Exception
  {
    MedicalData data = (MedicalData) incomingMessageEnvelope.getMessage();

    if (data == null)
    {
      messageCollector.send(new OutgoingMessageEnvelope(OUTPUT_STREAM, null, null, "BAD DATA"));
    } else
    {
      messageCollector.send(new OutgoingMessageEnvelope(OUTPUT_STREAM, null, null, "everything is OKAY!"));
    }

  }
}
