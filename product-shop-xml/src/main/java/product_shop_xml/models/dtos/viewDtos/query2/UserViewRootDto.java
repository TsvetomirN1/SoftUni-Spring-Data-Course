package product_shop_xml.models.dtos.viewDtos.query2;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserViewRootDto {


    @XmlElement(name = "user")
    private List<UserViewDto> users;


    public UserViewRootDto(List<UserViewDto> users) {
        this.users = users;
    }

    public UserViewRootDto() {
    }

    public List<UserViewDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserViewDto> users) {
        this.users = users;
    }
}
