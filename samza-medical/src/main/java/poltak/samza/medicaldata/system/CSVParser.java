package poltak.samza.medicaldata.system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVParser
{
  private static final String CSV_DELIMITER    = ",";
  private static final String TIMESTAMP_FORMAT = "hh:mm:ss";

  // Array indices for valid fields.
  private static final int ID                       = 0;
  private static final int TIMESTAMP                = 1;
  private static final int BLOOD_PRESSURE_SYSTOLIC  = 2;
  private static final int BLOOD_PRESSURE_DIASTOLIC = 3;
  private static final int CO2                      = 4;
  private static final int OXYGEN                   = 5;
  private static final int HEART_RATE               = 6;

  /**
   * Parses a specified CSV file to a Samza stream.
   * // TODO: get this to return a patient for each line
   *
   * @param pathToFile The path to the specific CSV formatted file.
   */
  public void parse(final String pathToFile)
  {
    BufferedReader br = null;
    String line;

    try
    {
      br = new BufferedReader(new FileReader(pathToFile));
      while ((line = br.readLine()) != null)
      {
        String[] fields = line.split(CSV_DELIMITER);

        // Parse and store each of the data fields from the split line.
        Patient patient = new Patient(fields[ID], new SimpleDateFormat(TIMESTAMP_FORMAT).parse(fields[TIMESTAMP]), Short.parseShort(fields[BLOOD_PRESSURE_SYSTOLIC]),
                                      Short.parseShort(fields[BLOOD_PRESSURE_DIASTOLIC]), Short.parseShort(fields[HEART_RATE]), Double.parseDouble(fields[CO2]),
                                      Double.parseDouble(fields[OXYGEN]));
      }
    } catch (FileNotFoundException e)
    {
      // TODO
    } catch (IOException e)
    {
      // TODO
    } catch (ParseException e)
    {
      // TODO
    } catch (InvalidPatientDataException e)
    {
      // TODO
    } finally
    {
      if (br != null)
      {
        try
        {
          br.close();
        } catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
  }
}
