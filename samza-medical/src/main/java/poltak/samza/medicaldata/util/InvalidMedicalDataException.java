package poltak.samza.medicaldata.util;

public class InvalidMedicalDataException extends Exception
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
