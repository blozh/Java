package SoftEngineer;

public class Score {
    private String id;

    private String number;

    private Float sports;

    private Float english;

    private Float software;

    private Float comprehensive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Float getSports() {
        return sports;
    }

    public void setSports(Float sports) {
        this.sports = sports;
    }

    public Float getEnglish() {
        return english;
    }

    public void setEnglish(Float english) {
        this.english = english;
    }

    public Float getSoftware() {
        return software;
    }

    public void setSoftware(Float software) {
        this.software = software;
    }

    public Float getComprehensive() {
        return comprehensive;
    }

    public void setComprehensive(Float comprehensive) {
        this.comprehensive = comprehensive;
    }
}