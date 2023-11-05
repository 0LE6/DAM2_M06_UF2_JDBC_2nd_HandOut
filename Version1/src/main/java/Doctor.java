public class Doctor {
	
    private int doctorCode;
    private String doctorName;
    private int hospitalCode;
    private String doctorSpecialization;
    

    public Doctor(int doctorCode, String name, int hospitalCode, String specialization) {
        this.doctorCode = doctorCode;
        this.doctorName = name;
        this.hospitalCode = hospitalCode;
        this.doctorSpecialization = specialization;
    }

    public int getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(int doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getName() {
        return doctorName;
    }

    public void setName(String name) {
        this.doctorName = name;
    }

    public String getSpecialization() {
        return doctorSpecialization;
    }

    public void setSpecialization(String specialization) {
        this.doctorSpecialization = specialization;
    }

    public int getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(int hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorCode=" + doctorCode +
                ", name='" + doctorName + '\'' +
                ", specialization='" + doctorSpecialization + '\'' +
                ", hospitalCode=" + hospitalCode +
                '}';
    }
}
