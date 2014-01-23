package poltak.samza.medicaldata.system;

import org.apache.samza.system.IncomingMessageEnvelope;
import org.apache.samza.system.SystemConsumer;
import org.apache.samza.system.SystemStreamPartition;

import java.util.List;
import java.util.Map;

public class MedicalDataConsumer implements SystemConsumer
{
  private final String systemName;
  private final Patient patientData;

  public MedicalDataConsumer(String systemName, Patient patientData)
  {
    this.systemName = systemName;
    this.patientData = patientData;
  }

  @Override
  public void start()
  {

  }

  @Override
  public void stop()
  {

  }

  @Override
  public void register(final SystemStreamPartition systemStreamPartition, final String s)
  {

  }

  @Override
  public List<IncomingMessageEnvelope> poll(final Map<SystemStreamPartition, Integer> systemStreamPartitionIntegerMap,
                                            final long l)
      throws InterruptedException
  {
    return null;
  }
}
