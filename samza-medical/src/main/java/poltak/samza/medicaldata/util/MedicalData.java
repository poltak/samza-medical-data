package poltak.samza.medicaldata.util;

import java.util.Date;

public class MedicalData
{
  private final String id;
  private final Date   timestamp;
  private final int bloodPressureSystolic;
  private final int bloodPressureDiastolic;
  private final int heartRate;
  private final double co2Percentage;
  private final double oxygenPercentage;

  public MedicalData(final String id,
                     final Date timestamp,
                     final int bloodPressureSystolic,
                     final int bloodPressureDiastolic,
                     final int heartRate,
                     final double co2Percentage,
                     final double oxygenPercentage)
      throws InvalidMedicalDataException
  {
    this.id = id;
    // TODO: Possible source of error.
    this.timestamp = new Date(timestamp.getTime());
    this.bloodPressureSystolic = bloodPressureSystolic;
    this.bloodPressureDiastolic = bloodPressureDiastolic;
    this.heartRate = heartRate;
    this.co2Percentage = co2Percentage;
    this.oxygenPercentage = oxygenPercentage;
  }

  @Override
  public String toString()
  {
    return "ID: " + id + "\n" +
           "Timestamp:" + timestamp.toString() + "\n" +
           "Blood Pressure (S): " + bloodPressureSystolic + "\n" +
           "Blood Pressure (D): " + bloodPressureDiastolic + "\n" +
           "Heart Rate: " + heartRate + "\n" +
           "CO2 Percentage: " + co2Percentage + "%\n" +
           "Oxygen Percentage: " + oxygenPercentage + "%";
  }
}
