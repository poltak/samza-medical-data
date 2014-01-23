package poltak.samza.medicaldata.system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

public class MedicalDataFeed
{
  private       BufferedReader bufferedReader;
  private final String         dataInputPath;

  public MedicalDataFeed(final String dataInputPath)
  {
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

    public String getInputFileName()
    {
      return inputFileName;
    }
  }
}
