package pl.agh.dp.loadbalancer.data.acces;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "clubs")
public class Club {

    @Id
    Long club_id;

    @Column(name = "club_name")
    String clubName;

    @Column(name = "city")
    String city;

    @Column(name = "foundation_date")
    Date foundationDate;

    @Column(name = "province")
    String province;

}