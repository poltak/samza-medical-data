package poltak.samza.medicaldata.system;

import org.apache.samza.Partition;
import org.apache.samza.system.IncomingMessageEnvelope;
import org.apache.samza.system.SystemStreamPartition;
import org.apache.samza.util.BlockingEnvelopeMap;
import poltak.samza.medicaldata.system.MedicalDataFeed.MedicalDataFeedEvent;
import poltak.samza.medicaldata.system.MedicalDataFeed.MedicalDataFeedListener;

public class MedicalDataConsumer extends BlockingEnvelopeMap implements MedicalDataFeedListener
{
  private final String          systemName;
  private final MedicalDataFeed feed;

  public MedicalDataConsumer(final String systemName, final MedicalDataFeed feed)
  {
    this.systemName = systemName;
    this.feed = feed;
  }

  public void onEvent(final MedicalDataFeedEvent event)
  {
    SystemStreamPartition systemStreamPartition =
        new SystemStreamPartition(systemName, event.getInputFileName(), new Partition(0));

    try
    {
      put(systemStreamPartition, new IncomingMessageEnvelope(systemStreamPartition, null, null, event));
    } catch (InterruptedException e)
    {
      // TODO
      e.printStackTrace();
    }
  }

  @Override
  public void start()
  {
    feed.start();
    feed.listen(this);
  }

  @Override
  public void stop()
  {
    feed.unlisten(this);
    feed.stop();
  }

  @Override
  public void register(final SystemStreamPartition systemStreamPartition, final String startingOffset)
  {
    super.register(systemStreamPartition, startingOffset);
  }
}
