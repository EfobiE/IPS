import java.lang.String;

public class PatientProf {
    // variables can be private b/c they all have functions to get & set their values.
    private final String adminID; // final keyword means the adminID cannot be changed. It never should be, so that's good.
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private float coPay;
    private String insuType;
    private String patientType;
    private MedCond medCondInfo;

    // all methods are public because they are just for initializing, getting and setting.

    //Constructor function
    //      For example, to make a PatientProf object with the name p in code, write:
    //      PatientProf p = new PatientProf(adminID,firstName,etc...);
    public PatientProf(String adminID,String firstName,String lastName,String address,String phone, float coPay,
                       String insuType,String patientType,MedCond medCondInfo)
    {
        this.adminID = adminID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.coPay = coPay;
        this.insuType = insuType;
        this.patientType = patientType;
        this.medCondInfo = medCondInfo;
    }

    // Getter methods
    public String getAdminID()
    {
        return adminID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPhone()
    {
        return phone;
    }

    public float getCoPay()
    {
        return coPay;
    }

    public String getInsuType()
    {
        return insuType;
    }

    public String getPatientType()
    {
        return patientType;
    }

    public MedCond getMedCondInfo()
    {
        return medCondInfo;
    }


    //Setter methods
    public void updateFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void updateLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void updateAddress(String address)
    {
        this.address = address;
    }

    public void updatePhone(String phone)
    {
        this.phone = phone;
    }

    public void updateCoPay(float coPay)
    {
        this.coPay = coPay;
    }

    public void updatePatientType(String patientType) throws Exception {
        if (patientType.equalsIgnoreCase("Pediatric") || patientType.equalsIgnoreCase("Adult") || patientType.equalsIgnoreCase("Senior"))
        {
            this.patientType = patientType;
        }
        else
        {
            throw new Exception("Invalid patientType");
        }
    }

    public void updateInsuType(String insuType) throws Exception {
        if (insuType.equalsIgnoreCase("Private") || insuType.equalsIgnoreCase("Government"))
        {
            this.insuType = insuType;
        }
        else
        {
            throw new Exception("Invalid insuType");
        }
    }

    public void updateMedCondInfo(MedCond medCondInfo)
    {
        this.medCondInfo = medCondInfo;
    }
}