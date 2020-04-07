package softuni.exam.domain.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    private String Url;

    public Picture() {
    }

    @Column(name = "url",nullable = false)
    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
