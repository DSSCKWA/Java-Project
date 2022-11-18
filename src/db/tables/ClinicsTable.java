package src.db.tables;

public class ClinicsTable {
    private int clinicId;
    private String address;
    private String city;

    public ClinicsTable(int clinicId, String address, String city) {
        this.clinicId = clinicId;
        this.address = address;
        this.city = city;
    }

    public ClinicsTable(String address, String city) {
        this.address = address;
        this.city = city;
    }

    public ClinicsTable() {}

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ClinicsTable{" +
                "clinicId=" + clinicId +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
