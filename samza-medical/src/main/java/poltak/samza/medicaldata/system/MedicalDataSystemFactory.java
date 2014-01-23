package poltak.samza.medicaldata.system;

import org.apache.samza.SamzaException;
import org.apache.samza.config.Config;
import org.apache.samza.metrics.MetricsRegistry;
import org.apache.samza.system.SystemAdmin;
import org.apache.samza.system.SystemConsumer;
import org.apache.samza.system.SystemFactory;
import org.apache.samza.system.SystemProducer;
import org.apache.samza.util.SinglePartitionSystemAdmin;

public class MedicalDataSystemFactory implements SystemFactory
{
  // TODO fix all this
  @Override
  public SystemConsumer getConsumer(final String systemName, final Config config, final MetricsRegistry metricsRegistry)
  {
    try
    {
      return new MedicalDataConsumer(config.get("systems." + systemName), new Patient(null, null, 0, 0, 0, 0.0, 0.0));
    } catch (InvalidPatientDataException e)
    {
      e.printStackTrace();
      return null; //TODO
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
