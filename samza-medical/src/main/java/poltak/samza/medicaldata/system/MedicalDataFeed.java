package poltak.samza.medicaldata.system;

import org.apache.samza.SamzaException;
import org.codehaus.jackson.map.ObjectMapper;
import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MedicalDataFeed
{
  private static final ObjectMapper jsonMapper = new ObjectMapper();

  private       CsvMapReader                 csvMapReader;
  private final Set<MedicalDataFeedListener> listeners;
  private final String                       dataInputPath;

  public MedicalDataFeed(final String dataInputPath)
  {
    this.listeners = new HashSet<MedicalDataFeedListener>();
    this.dataInputPath = dataInputPath;
  }

  public void start()
  {
    try
    {
      csvMapReader = new CsvMapReader(new FileReader(dataInputPath), CsvPreference.STANDARD_PREFERENCE);
    } catch (FileNotFoundException e)
    {
      throw new RuntimeException("Unable to read input feed from: " + dataInputPath, e);
    }
  }

  public void stop()
  {
    try
    {
      csvMapReader.close();
    } catch (IOException e)
    {
      throw new RuntimeException("Unable to close access to file at: " + dataInputPath, e);
    }
  }

  public void listen(MedicalDataFeedListener listener)
  {
    listeners.add(listener);
  }

  public void unlisten(MedicalDataFeedListener listener)
  {
    if (!listeners.contains(listener))
    {
      throw new RuntimeException("Trying to unlisten to a channel that listener is not listening to.");
    }

    listeners.remove(listener);
  }

  public static class MedicalDataFeedEvent
  {
    private final String inputFileName;
    private final String id;
    private final Date   timeStamp;
    private final int    bloodPressureSystolic;
    private final int    bloodPressureDiastolic;
    private final int    heartRate;
    private final double CO2Percentage;
    private final double oxygenPercentage;

    // TODO: input checking

    /**
     * Creates a new MedicalDataFeedEvent object.
     */
    public MedicalDataFeedEvent(final String inputFileName,
                                final String id,
                                final Date timeStamp,
                                final int bloodPressureSystolic,
                                final int bloodPressureDiastolic,
                                final int heartRate,
                                final double CO2Percentage,
                                final double oxygenPercentage)
        throws InvalidPatientDataException
    {
      this.inputFileName = inputFileName;
      this.id = id;
      // TODO: Possible source of error.
      this.timeStamp = new Date(timeStamp.getTime());
      this.bloodPressureSystolic = bloodPressureSystolic;
      this.bloodPressureDiastolic = bloodPressureDiastolic;
      this.heartRate = heartRate;
      this.CO2Percentage = CO2Percentage;
      this.oxygenPercentage = oxygenPercentage;
    }

    public String toJson()
    {
      Map<String, Object> jsonObject = toMap(this);

      try
      {
        return jsonMapper.writeValueAsString(jsonObject);
      } catch (Exception e)
      {
        throw new SamzaException(e);
      }
    }

    public static Map<String, Object> toMap(MedicalDataFeedEvent event)
    {
      Map<String, Object> jsonObject = new HashMap<String, Object>();

      jsonObject.put("id", event.getId());
      jsonObject.put("timestamp", event.getTimeStamp());
      jsonObject.put("bps", event.getBloodPressureSystolic());
      jsonObject.put("bpd", event.getBloodPressureDiastolic());
      jsonObject.put("co2", event.getCO2Percentage());
      jsonObject.put("oxygen", event.getOxygenPercentage());
      jsonObject.put("heartrate", event.getHeartRate());

      return jsonObject;
    }

    public String getInputFileName()
    {
      return inputFileName;
    }

    public Date getTimeStamp()
    {
      return timeStamp;
    }

    public String getId()
    {
      return id;
    }

    public int getBloodPressureSystolic()
    {
      return bloodPressureSystolic;
    }

    public int getGetBloodPressureDiastolic()
    {
      return bloodPressureDiastolic;
    }

    public int getHeartRate()
    {
      return heartRate;
    }

    public double getCO2Percentage()
    {
      return CO2Percentage;
    }

    public double getOxygenPercentage()
    {
      return oxygenPercentage;
    }

    public Object getBloodPressureDiastolic()
    {
      return bloodPressureDiastolic;
    }
  }

  public static interface MedicalDataFeedListener
  {
    void onEvent(MedicalDataFeedEvent event);
  }
}
