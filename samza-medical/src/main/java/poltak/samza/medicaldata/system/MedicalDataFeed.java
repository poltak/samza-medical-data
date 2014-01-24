package poltak.samza.medicaldata.system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MedicalDataFeed
{
  private       BufferedReader                            bufferedReader;
  private final Set<MedicalDataFeedListener> listeners;
  private final String                                    dataInputPath;

  public MedicalDataFeed(final String dataInputPath)
  {
    this.listeners = new HashSet<MedicalDataFeedListener>();
    this.dataInputPath = dataInputPath;
  }

  public void start()
  {
    try
    {
      bufferedReader = new BufferedReader(new FileReader(dataInputPath));
    } catch (FileNotFoundException e)
    {
      throw new RuntimeException("Unable to read input feed from: " + dataInputPath, e);
    }
  }

  public void stop()
  {
    try
    {
      bufferedReader.close();
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
    private final int    getBloodPressureDiastolic;
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
                                final int getBloodPressureDiastolic,
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
      this.getBloodPressureDiastolic = getBloodPressureDiastolic;
      this.heartRate = heartRate;
      this.CO2Percentage = CO2Percentage;
      this.oxygenPercentage = oxygenPercentage;
    }

    public static Map<String, Object> toMap(MedicalDataFeedEvent event)
    {
      Map<String, Object> jsonObject = new HashMap<String, Object>();

      jsonObject.put("id", event.getId());
      jsonObject.put("timestamp", event.getTimeStamp());
      jsonObject.put("bps", event.getBloodPressureSystolic());
      jsonObject.put("bpd", event.getBloodPressureDiastolic);
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
      return getBloodPressureDiastolic;
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
  }

  public static interface MedicalDataFeedListener
  {
    void onEvent(MedicalDataFeedEvent event);
  }
}
