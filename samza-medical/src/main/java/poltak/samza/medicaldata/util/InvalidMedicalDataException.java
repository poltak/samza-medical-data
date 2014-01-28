package poltak.samza.medicaldata.util;

public class InvalidMedicalDataException extends Exception
{
  public InvalidMedicalDataException(final String message)
  {
    super(message);
  }
}
