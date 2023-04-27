package models;

public class LendModel
{
    private int stuff_id;

    private int member_id;

    private String date;

    public LendModel(int stuff_id, int member_id, String date) {
        this.stuff_id = stuff_id;
        this.member_id = member_id;
        this.date = date;
    }

    public int getStuff_id() {
        return stuff_id;
    }

    public void setStuff_id(int stuff_id) {
        this.stuff_id = stuff_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
