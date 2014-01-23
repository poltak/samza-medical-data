package system;

import java.util.List;
import java.util.Map;

public class MedicalDataSystemFactory extends SystemFactory
{
  @Override
  public SystemConsumer getConsumer(final String systemName, final Config config, final MetricsRegistry metricsRegistry)
  {
    return new SystemConsumer()
    {
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
      public List<IncomingMessageEnvelope> poll(
          final Map<SystemStreamPartition, Integer> systemStreamPartitionIntegerMap, final long l)
          throws InterruptedException
      {
        return null;
      }
    }
  }

  @Override
  public SystemProducer getProducer(final String systemName, final Config config, final MetricsRegistry metricsRegistry)
  {
    throw new SamzaException("Cannot produce to read only data.");
  }

  @Override
  public SystemAdmin getAdmin(final String systemName, final Config config)
  {
    return new SinglePartitionSystemAdmin();
  }

}
