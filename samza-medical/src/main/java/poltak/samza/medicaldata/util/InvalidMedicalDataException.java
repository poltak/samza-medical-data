package poltak.samza.medicaldata.util;

import org.apache.samza.SamzaException;

public class InvalidMedicalDataException extends SamzaException
{
  public InvalidMedicalDataException()
  {
    super();
  }

  public InvalidMedicalDataException(final String message)
  {
    super(message);
  }
}
