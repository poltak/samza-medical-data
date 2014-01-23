package poltak.samza.medicaldata.system;

import org.apache.samza.SamzaException;

import java.util.Date;

public class Patient
{
  private final String id;
  private final Date   timeStamp;
  private final int  bloodPressureSystolic;
  private final int getBloodPressureDiastolic;
  private final int heartRate;
  private final double CO2Percentage;
  private final double oxygenPercentage;

  // TODO: input checking

  /**
   * Creates a new Patient object.
   */
  public Patient(final String id, final Date timeStamp, int bloodPressureSystolic, int getBloodPressureDiastolic,
                 int heartRate, double CO2Percentage, double oxygenPercentage) throws InvalidPatientDataException
  {
    this.id = id;
    // TODO: Possible source of error.
    this.timeStamp = new Date(timeStamp.getTime());
    this.bloodPressureSystolic = bloodPressureSystolic;
    this.getBloodPressureDiastolic = getBloodPressureDiastolic;
    this.heartRate = heartRate;
    this.CO2Percentage = CO2Percentage;
    this.oxygenPercentage = oxygenPercentage;
  }

}
