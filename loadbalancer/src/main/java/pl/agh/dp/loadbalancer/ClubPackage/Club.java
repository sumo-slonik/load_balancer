package pl.agh.dp.loadbalancer.ClubPackage;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity(name = "CLUB")
@Getter
@Setter
@Table(name = "CLUBS_TABLE")
public class Club {


    public Club(Object[] object) {
        this.clubName = (String) object[0];
        this.city = (String) object[1];
        this.foundationDate = (Date) object[2];
        this.club_id = Long.valueOf((int) object[3]);
        this.province = (String) object[4];
    }

    public Club() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    Long club_id;

    @Column(name = "club_name")
    String clubName;

    @Column(name = "city")
    String city;

    @Column(name = "foundation_date")
    Date foundationDate;

    @Column(name = "province")
    String province;

    @Override
    public String toString() {
        return "Club{" +
                "clubName='" + clubName + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public Club(Club club) {
        this.club_id = club.club_id;
        this.clubName =  club.clubName;
        this.city =  club.city;
        this.foundationDate =  club.foundationDate;
        this.province =  club.province;
    }
}