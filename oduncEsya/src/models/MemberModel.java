package models;

//Model sınıfları SQL tablolarının yansıma sınıfları gibi bir yer tutuyor. 
//Bu sınıfları eleman tipi  olarak gören listelere veritabanındaki verileri çekiyoruz.

public class MemberModel
{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    //Örneğin burada da id, name, phone ve mail field ları var. Bu fieldlar ile işlem yapabiliriz.
    private int id;

    private String name;

    private String phone;

    private String mail;

    public MemberModel(int id, String name, String phone, String mail) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
