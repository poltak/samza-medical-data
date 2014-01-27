package poltak.samza.medicaldata.task;

import org.apache.samza.system.IncomingMessageEnvelope;
import org.apache.samza.system.OutgoingMessageEnvelope;
import org.apache.samza.system.SystemStream;
import org.apache.samza.task.MessageCollector;
import org.apache.samza.task.StreamTask;
import org.apache.samza.task.TaskCoordinator;
import poltak.samza.medicaldata.util.InvalidMedicalDataException;
import poltak.samza.medicaldata.util.MedicalData;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MedicalDataFeedStreamTask implements StreamTask
{
  private static final SystemStream OUTPUT_STREAM       = new SystemStream("kafka", "medical-test");
  private static final String       VALID_CSV_DELIMITER = ",";
  private static final String       VALID_TIME_FORMAT   = "hh:mm:ss";
  private static final int          VALID_NUM_FIELDS    = 7;

  @Override
  public void process(final IncomingMessageEnvelope incomingMessageEnvelope, final MessageCollector messageCollector,
                      final TaskCoordinator taskCoordinator)
  {
    MedicalData dataReceived;
    try
    {
      // TODO: check that a String is actually received
      dataReceived = parseLineOfData((String) incomingMessageEnvelope.getMessage());
    } catch (InvalidMedicalDataException e)
    {
      dataReceived = null;
    } catch (ParseException e)
    {
      dataReceived = null;
    }

    messageCollector.send(new OutgoingMessageEnvelope(OUTPUT_STREAM, dataReceived));
  }

  private MedicalData parseLineOfData(final String line) throws InvalidMedicalDataException, ParseException
  {
    String[] fields = line.split(VALID_CSV_DELIMITER);

    if (fields.length != VALID_NUM_FIELDS)
      throw new InvalidMedicalDataException("Invalid number of fields in supplied data.");

    return new MedicalData(
        fields[0],
        new SimpleDateFormat(VALID_TIME_FORMAT).parse(fields[1]),
        Integer.parseInt(fields[2]),
        Integer.parseInt(fields[3]),
        Integer.parseInt(fields[4]),
        Double.parseDouble(fields[5]),
        Double.parseDouble(fields[6])
    );
  }
}
