import java.io.*;
import java.lang.String;
import java.util.Scanner;
import java.util.Arrays;

// All print messages commented out, un-comment them if needed.

public class PatientProfDB {
    private int numPatient = 0;
    private int currentPatientIndex = 0;
    private final String fileName;
    private PatientProf[] patientList = new PatientProf[10000]; //10k is very large to be safe

    // constructor
    public PatientProfDB(String fileName) throws IOException {
        this.fileName = fileName;
        this.initializeDatabase(fileName);
    }

    // methods
    public void insertNewProfile(PatientProf newProf)
    {
        // insert newProf into the patientList.
        this.patientList[numPatient] = newProf ;
        numPatient++;
        //System.out.println("Profile created, now there are "+numPatient);
    }

    public boolean deleteProfile(String adminID, String lastName)
    {
        // returns true if it was successfully deleted.
        // iterates through patientList for lastName AND adminID AND makes sure something's in index of patient list
        for(int j = 0; j < numPatient; j++)
        {
            if ((patientList[j] != null) && (patientList[j].getLastName().equalsIgnoreCase(lastName)) && (patientList[j].getAdminID().equalsIgnoreCase(adminID)))
            {
                patientList[j] = null; // remove data from profile to be deleted
                // swap last element and removed element. Now the last element is null and there isn't a null in the middle of the array.
                // NOTE this does not preserve the order of the patients in the array, however the order is arbitrary so it is not a concern.
                PatientProf temp = patientList[numPatient - 1];
                patientList[numPatient - 1] = patientList[j];
                patientList[j] = temp;

                //System.arraycopy(patientList, j + 1,patientList,j, patientList.length - j - 1);//deletes jth index from patientList **Seems to not work? replaced.
                numPatient --;
                //System.out.println("Deleted a profile, now there are "+numPatient);
                return true;
            }
        }
        //System.out.printf("Delete failed: no profile with adminID = %s and lastName = %s\n", adminID, lastName);
        return false;
    }

    public PatientProf findProfile(String adminID, String lastName) throws NullPointerException
    {
        for( int i = 0; i < numPatient; i++) // iterate through patientList for lastName AND adminID
        {
            PatientProf current = patientList[i];
            if ((current != null) && (current.getLastName().equalsIgnoreCase(lastName)) && (current.getAdminID().equalsIgnoreCase(adminID)))
            {
                //System.out.printf("Found: %s %s's profile. AdminID: %s", current.getFirstName(), current.getLastName(), current.getAdminID());
                return current;
            }
        }
        //if couldn't find PatPro
        //System.out.print("Couldn't Find a Patient Profile");
        return null;
    }

    public PatientProf findFirstProfile()
    {
        // set currentPatientIndex to 0 and returns that profile
        currentPatientIndex = 0;// sets
        return patientList[currentPatientIndex];
    }

    public PatientProf findNextProfile() {
        // increment currentPatientIndex then return patientList[currentPatientIndex].
        currentPatientIndex++;
        if(currentPatientIndex < numPatient) {
            return patientList[currentPatientIndex];
        }
        else {
            return null;
        }
    }

    public void writeAllPatientProf(String fileName) throws IOException {
        // write the patientList data to a file.
        FileWriter f = new FileWriter(fileName);//opens file for writing
        for(PatientProf i: patientList){
            if (i != null) {
                //outputs Patient Profile data with newline in between each piece, to make initializing database easier
                f.write(i.getAdminID().concat("\n"));
                f.write(i.getFirstName().concat("\n"));
                f.write(i.getLastName().concat("\n"));
                f.write(i.getAddress().concat("\n"));
                f.write(i.getPhone().concat("\n"));
                f.write(String.valueOf(i.getCoPay()).concat("\n"));
                // swapped order of reading the PT and IT to match provided database.txt
                f.write(i.getPatientType().concat("\n"));
                f.write(i.getInsuType().concat("\n"));
                f.write(i.getMedCondInfo().getMdContact().concat("\n"));
                f.write(i.getMedCondInfo().getMdPhone().concat("\n"));
                f.write(i.getMedCondInfo().getAlgType().concat("\n"));
                f.write(i.getMedCondInfo().getIllType().concat("\n"));
            }
        }
        f.close();
        //System.out.println("Write complete");
    }

    public void initializeDatabase(String fileName) throws IOException {
        // read the patientList from a file to.
        FileReader f = new FileReader(fileName);
        Scanner fScan = new Scanner(f);
        int initedNum = 0;

        // reset memory and set numPatient to 0 before reading the data.
        Arrays.fill(patientList, null);
        numPatient = 0;
        //System.out.println("Starting initialization: all existing profiles deleted");

        while(fScan.hasNextLine()){
            // read each line for data
            String newID = fScan.nextLine();
            String newFN = fScan.nextLine();
            String newLN = fScan.nextLine();
            String newAD = fScan.nextLine();
            String newPH = fScan.nextLine();
            float newCP = Float.parseFloat(fScan.nextLine());
            // swapped order of reading the PT and IT to match provided database.txt
            String newPT = fScan.nextLine();
            String newIT = fScan.nextLine();
            String newMDC = fScan.nextLine();
            String newMDP = fScan.nextLine();
            String newMAT = fScan.nextLine();
            String newMIT = fScan.nextLine();
            MedCond newMedCond = new MedCond(newMDC,newMDP,newMAT,newMIT);//initializes MedCond class
            PatientProf b = new PatientProf(newID,newFN,newLN,newAD,newPH,newCP,newIT,newPT,newMedCond);//initializes Patient Prof class
            insertNewProfile(b);
            initedNum++;
            //System.out.println(newFN+" "+newLN+"'s profile initialized");
        }
        //System.out.println("Patient Profiles Initiated: "+initedNum);
    }

    //TESTING
    public static void main(String args[]) throws IOException {
        PatientProfDB dataBase = new PatientProfDB("src\\test.txt"); // create new database object (initialized automatically on creation)
        dataBase.insertNewProfile(new PatientProf("PA1","John","Smith","30 Elm St.","8608748965",10,"Government","Adult",new MedCond("Dr Jekyll", "7865413248","None","None")));
        dataBase.findFirstProfile();
        dataBase.findProfile("PA1","Smith");
        dataBase.writeAllPatientProf("src\\test.txt");
        System.out.println(dataBase.deleteProfile("PA1","Smith"));
        dataBase.writeAllPatientProf("src\\test.txt");

    }
}