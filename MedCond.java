import java.lang.String;

public class MedCond {
    private String mdContact;
    private String mdPhone;
    private String algType;
    private String illType;

    // constructor
    public MedCond(String mdContact, String mdPhone, String algType, String illType)
    {
        this.mdContact = mdContact;
        this.mdPhone = mdPhone;
        this.algType = algType;
        this.illType = illType;
    }

    //getters
    public String getMdContact() {
        return mdContact;
    }

    public String getMdPhone() {
        return mdPhone;
    }

    public String getAlgType() {
        return algType;
    }

    public String getIllType() {
        return illType;
    }

    // setters
    public void updateMdContact(String mdContact) {
        this.mdContact = mdContact;
    }

    public void updateMdPhone(String mdPhone) {
        this.mdPhone = mdPhone;
    }

    public void updateAlgType(String algType) throws Exception {

        if (algType.equalsIgnoreCase("none") || algType.equalsIgnoreCase("food") || algType.equalsIgnoreCase("medication") || algType.equalsIgnoreCase("other"))
        {
            this.algType = algType;
        }
        else
        {
            throw new Exception("Invalid algType");
        }
    }

    public void updateIllType(String illType) throws Exception {
        if (illType.equalsIgnoreCase("none") || illType.equalsIgnoreCase("CHD") || illType.equalsIgnoreCase("diabetes") || illType.equalsIgnoreCase("asthma") || illType.equalsIgnoreCase("other"))
        {
            this.illType = illType;
        }
        else
        {
            throw new Exception("Invalid illType");
        }
    }
}
